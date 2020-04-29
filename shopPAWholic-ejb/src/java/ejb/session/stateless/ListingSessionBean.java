package ejb.session.stateless;

import entity.Category;
import entity.Listing;
import entity.Seller;
import entity.Tag;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.CategoryNotFoundException;
import util.exception.CreateNewListingException;
import util.exception.InputDataValidationException;
import util.exception.ListingNotFoundException;
import util.exception.ListingSkuCodeExistException;
import util.exception.TagNotFoundException;
import util.exception.UpdateListingException;

@Stateless
public class ListingSessionBean implements ListingSessionBeanLocal {

    @EJB(name = "TagSessionBeanLocal")
    private TagSessionBeanLocal tagSessionBeanLocal;

    @EJB(name = "CategorySessionBeanLocal")
    private CategorySessionBeanLocal categorySessionBeanLocal;

    @PersistenceContext(unitName = "shopPAWholic-ejbPU")
    private EntityManager em;
    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public ListingSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = (Validator) validatorFactory.getValidator();
    }

    @Override
    public Listing retrieveListingByListingId(Long listingId) throws ListingNotFoundException {
        Listing listing = em.find(Listing.class, listingId);
        if (listing != null) {
            listing.getCategory();
            listing.getTags().size();
            return listing;
        } else {
            throw new ListingNotFoundException("Listing Id " + listingId + " does not exist! ");
        }
    }

    @Override
    public Listing retrieveListingBySkuCode(String skuCode) throws ListingNotFoundException {
        Query query = em.createQuery("SELECT l FROM Listing l WHERE l.skuCode = :inSkuCode");
        query.setParameter("inSkuCode", skuCode);

        try {
            Listing listing = (Listing) query.getSingleResult();
            listing.getCategory();
            listing.getTags().size();

            return listing;
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new ListingNotFoundException("Sku Code " + skuCode + " does not exist!");
        }
    }

    @Override
    public Listing createNewListing(Listing newListing, Long categoryId, List<Long> tagIds, Long sellerId) throws InputDataValidationException, ListingSkuCodeExistException, CreateNewListingException {
        Set<ConstraintViolation<Listing>> constraintViolations;
        constraintViolations = validator.validate(newListing);

        if (constraintViolations.isEmpty()) {
            try {
                Category category = categorySessionBeanLocal.retrieveCategoryByCategoryId(categoryId);

                em.persist(newListing);
                newListing.setCategory(category);
                Seller seller = em.find(Seller.class, sellerId);
                seller.getListings().add(newListing);
                newListing.setSeller(seller);
                if (tagIds != null && (!tagIds.isEmpty())) {
                    for (Long tagId : tagIds) {
                        Tag tag = tagSessionBeanLocal.retrieveTagByTagId(tagId);
                        newListing.addTag(tag);
                    }
                }
                em.flush();
                return newListing;
            } catch (CategoryNotFoundException | TagNotFoundException ex) {
                throw new CreateNewListingException("An error has occurred while creating the new listing: " + ex.getMessage());
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }

    @Override
    public List<Listing> retrieveAllListings() {
        Query query = em.createQuery("SELECT l FROM Listing l ORDER BY l.skuCode ASC");
        List<Listing> listings = query.getResultList();

        for (Listing listing : listings) {
            listing.getCategory();
            listing.getTags().size();
            listing.getOrders().size();
        }
        return listings;
    }
    
    @Override
    public List<Listing> retrieveListingsBySellerId(Long sellerId) {
        Query query = em.createQuery("SELECT l FROM Listing l WHERE l.seller.userId = :sellerId");
        query.setParameter("sellerId", sellerId);
        List<Listing> listings = query.getResultList();

        for (Listing listing : listings) {
            listing.getCategory();
            listing.getTags().size();
            listing.getOrders().size();
        }
        return listings;
    }
    
    @Override
    public Map<String, Integer> retrieveTotalNumberOfListingsPerCategory() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        Query query;
        Map<String, Integer> listingPerMonth = new LinkedHashMap<>();
        List<Category> categories = categorySessionBeanLocal.retrieveAllCategories();
        for (Category c: categories) {
            query = em.createQuery("SELECT l FROM Listing l WHERE l.category = :inCategory");
            query.setParameter("inCategory", c);
            listingPerMonth.put(c.getName(), (query.getResultList()).size());
        }
        return listingPerMonth;
    }

    
    @Override
    public Map<String, Integer> retrieveTotalNumberOfListingsForTheYear() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        Query query;
        Map<String, Integer> listingPerYear = new LinkedHashMap<>();
        List<String> months = Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        for (int i = 0; i < months.size(); i++) {
            query = em.createQuery("SELECT l FROM Listing l WHERE EXTRACT(YEAR(l.listDate)) = :inYear and EXTRACT(MONTH(l.listDate)) = :inMonth");
            query.setParameter("inYear", year);
            query.setParameter("inMonth", i + 1);
            listingPerYear.put(months.get(i), (query.getResultList()).size());
        }
        return listingPerYear;
    }
    
    @Override
    public Map<String, Integer> retrieveTotalNumberOfListingsForDay() {
        Query query;
        long days_in_ms = 1000 * 60 * 60 * 24;
        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() - (7 * days_in_ms));
        List<Date> sevenDays = new ArrayList<>();
        DateFormat dformat = new SimpleDateFormat("yyyy-MM-dd");
        sevenDays.add(startDate);

        while (endDate.before(startDate)) {
            try {
                sevenDays.add(dformat.parse(dformat.format(endDate)));
                endDate = new Date(endDate.getTime() + (days_in_ms));
            } catch (ParseException ex) {
                Logger.getLogger(ListingSessionBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        Map<String, Integer> sevenDaysCast = new LinkedHashMap<>();

        for (int i = 0; i < sevenDays.size(); i++) {
            query = em.createQuery("SELECT l FROM Listing l WHERE l.listDate between :inStartDate AND :inEndDate");
            query.setParameter("inStartDate", sevenDays.get(i));

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sevenDays.get(i));
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 0);

            query.setParameter("inEndDate", calendar.getTime());

            List<Listing> totalNumber = query.getResultList();
            sevenDaysCast.put(dformat.format(sevenDays.get(i)), totalNumber.size());
        }
        return sevenDaysCast;
    }

    @Override
    public List<Listing> searchListingsByName(String searchName) {
        Query query = em.createQuery("SELECT l FROM Listing l WHERE l.name LIKE :inSearchName ORDER BY l.skuCode ASC");
        query.setParameter("inSearchName", "%" + searchName + "%");
        List<Listing> listings = query.getResultList();

        for (Listing listing : listings) {
            listing.getCategory();
            listing.getTags().size();
            listing.getOrders().size();
        }
        return listings;
    }

    @Override
    public List<Listing> filterListingsByCategory(Long categoryId) throws CategoryNotFoundException {
        List<Listing> listings = new ArrayList<>();
        Category category = categorySessionBeanLocal.retrieveCategoryByCategoryId(categoryId);

        listings = category.getListings();

        for (Listing listing : listings) {
            listing.getCategory();
            listing.getTags().size();
            listing.getOrders().size();
        }

        Collections.sort(listings, new Comparator<Listing>() {
            public int compare(Listing l1, Listing l2) {
                return l1.getSkuCode().compareTo(l2.getSkuCode());
            }
        });
        return listings;
    }

    @Override
    public List<Listing> filterListingsByTags(List<Long> tagIds, String condition) {
        List<Listing> listings = new ArrayList<>();

        if (tagIds == null || tagIds.isEmpty() || (!condition.equals("AND") && !condition.equals("OR"))) {
            return listings;
        } else {
            if (condition.equals("OR")) {
                Query query = em.createQuery("SELECT DISTINCT l FROM Listing l, IN (l.tags) t WHERE t.tagId IN :inTagIds ORDER BY l.skuCode ASC");
                query.setParameter("inTagIds", tagIds);
                listings = query.getResultList();
            } else { //AND condition
                String selectClause = "SELECT l FROM Listing l";
                String whereClause = "";
                Boolean firstTag = true;
                Integer tagCount = 1;

                for (Long tagId : tagIds) {
                    selectClause += ", IN (l.tags) t" + tagCount;

                    if (firstTag) {
                        whereClause = "WHERE t1.tagId = " + tagId;
                        firstTag = false;
                    } else {
                        whereClause += " AND t" + tagCount + ".tagId = " + tagId;
                    }
                    tagCount++;
                }
                String jpql = selectClause + " " + whereClause + " ORDER BY l.skuCode ASC";
                Query query = em.createQuery(jpql);
                listings = query.getResultList();
            }

            for (Listing listing : listings) {
                listing.getCategory();
                listing.getTags().size();
                listing.getOrders().size();
            }
            Collections.sort(listings, new Comparator<Listing>() {
                public int compare(Listing l1, Listing l2) {
                    return l1.getSkuCode().compareTo(l2.getSkuCode());
                }
            });
            return listings;
        }
    }

    @Override
    public void updateListing(Listing listing, Long categoryId, List<Long> tagIds) throws ListingNotFoundException, CategoryNotFoundException, TagNotFoundException, UpdateListingException, InputDataValidationException {
        if (listing != null && listing.getListingId() != null) {
            Set<ConstraintViolation<Listing>> constraintViolations = validator.validate(listing);

            if (constraintViolations.isEmpty()) {
                Listing listingToUpdate = retrieveListingByListingId(listing.getListingId());

                if (listingToUpdate.getSkuCode().equals(listing.getSkuCode())) {
                    if (categoryId != null && (!listingToUpdate.getCategory().getCategoryId().equals(categoryId))) {
                        Category categoryToUpdate = categorySessionBeanLocal.retrieveCategoryByCategoryId(categoryId);

                        listingToUpdate.setCategory(categoryToUpdate);
                    }

                    if (tagIds != null) {
                        for (Tag tag : listingToUpdate.getTags()) {
                            tag.getListings().remove(listingToUpdate);
                        }
                        listingToUpdate.getTags().clear();

                        for (Long tagId : tagIds) {
                            Tag tag = tagSessionBeanLocal.retrieveTagByTagId(tagId);
                            listingToUpdate.addTag(tag);
                        }
                    }

                    listingToUpdate.setName(listing.getName());
                    listingToUpdate.setDescription(listing.getDescription());
                    listingToUpdate.setUnitPrice(listing.getUnitPrice());
                    listingToUpdate.setQuantityOnHand(listing.getQuantityOnHand());
                } else {
                    throw new UpdateListingException("Sku code of listing record to be updated does not match existing record!");

                }
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new ListingNotFoundException("Listing Id not provided for listing to be updated!");
        }
    }

    @Override
    public void deleteListing(Long listingId) throws ListingNotFoundException {
        Listing listingToDelete = retrieveListingByListingId(listingId);
        listingToDelete.getCategory().getListings().remove(listingToDelete);

        for (Tag tag : listingToDelete.getTags()) {
            tag.getListings().remove(listingToDelete);
        }
        listingToDelete.getTags().clear();
        em.remove(listingToDelete);
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Listing>> constraintViolations) {
        String msg = "Input data validation error!:";
        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        return msg;
    }
}
