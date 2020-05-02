package ejb.session.stateless;

import entity.Advertisement;
import entity.BillingDetail;
import entity.Seller;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.AdvertisementNotFoundException;
import util.exception.CreateNewAdvertisementException;
import util.exception.InputDataValidationException;

@Stateless
public class AdvertisementSessionBean implements AdvertisementSessionBeanLocal{

    @PersistenceContext(unitName = "shopPAWholic-ejbPU")
    private EntityManager em;
    private final ValidatorFactory validatorFactory;
    private final Validator validator;
    
    @EJB
    BillingDetailSessionBeanLocal billingDetailSessionBeanLocal; 

    public AdvertisementSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = (Validator) validatorFactory.getValidator();
    }

    @Override//billing detail initialised in the method,
    public Advertisement createNewAdvertisement(Advertisement advertisement, Long sellerId, String ccNum) throws CreateNewAdvertisementException, InputDataValidationException {
        Set<ConstraintViolation<Advertisement>> constraintViolations;
        constraintViolations = validator.validate(advertisement);
        if (constraintViolations.isEmpty()) {
            try {
               // SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
                Date date = new Date(System.currentTimeMillis());
                BillingDetail billingDetail = new BillingDetail(ccNum, date);
                billingDetailSessionBeanLocal.createNewBillingDetail(billingDetail);
                billingDetail.setAdvertisement(advertisement);
                advertisement.setBillingDetail(billingDetail);
                System.out.println("****AdvertisementSessionBean---createNewAdvertisement ccNum: " + ccNum);
                billingDetail.setCreditCardDetail(ccNum);
                
                Seller seller = em.find(Seller.class, sellerId);
                seller.getAdvertisements().add(advertisement);
                advertisement.setSeller(seller);
                
                seller.getBillingDetails().add(billingDetail);
                billingDetail.setSeller(seller);
                
                em.persist(advertisement);
                em.flush();

                return advertisement;
            } catch (Exception ex) {
                throw new CreateNewAdvertisementException("An unexpected error has occurred: " + ex.getMessage());
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }
    
    @Override
    public void updateAdvertisement(Advertisement advertisement) throws InputDataValidationException {
        Set<ConstraintViolation<Advertisement>>constraintViolations = validator.validate(advertisement);
        if(constraintViolations.isEmpty()) {
            if(advertisement.getAdvertisementId() != null) {
                em.merge(advertisement);
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }
    
    @Override
    public void deleteAdvertisement(Long advertisementId) throws AdvertisementNotFoundException {
        Advertisement advertisement = retrieveAdvertisementById(advertisementId);
        em.remove(advertisement);
    }
    
    @Override
    public Advertisement retrieveAdvertisementById(Long id) throws AdvertisementNotFoundException {
        Advertisement advertisement = em.find(Advertisement.class, id);
        if (advertisement != null) {
            return advertisement;
        } else {
            throw new AdvertisementNotFoundException("Advertisement ID of " + id + " does not exist!");
        }
    }
    
    @Override
    public List<Advertisement> retrieveAllAdvertisements() {
        Query query = em.createQuery("SELECT a FROM Advertisement a ORDER BY a.startDate");
        List<Advertisement> advertisements = query.getResultList();
        return advertisements;
    }
    
    @Override 
    public List<String> retrieveAdvertisementImages(){
        Query query = em.createQuery("SELECT a.picture FROM Advertisement a");
        List<String> pictureList = query.getResultList();
        return pictureList;
    }
    
    @Override
    public List<Advertisement> retrieveAdvertisementsBySellerId(Long sellerId) {
        Query query = em.createQuery("SELECT a FROM Advertisement a WHERE a.seller.userId = :sellerId");
        query.setParameter("sellerId", sellerId);
        List<Advertisement> advertisements = query.getResultList();
        return advertisements;
    }
    
    public List<Advertisement> retrieveAllAdvertisementOnCurrentDay() {
        List<Advertisement> advertisements = retrieveAllAdvertisements();
        List<Advertisement> advertisementsToBePassed = new ArrayList<>();
        Date dateC = new Date(System.currentTimeMillis());
        for(Advertisement a: advertisements) {
            if(dateC.compareTo(a.getStartDate()) >= 0 && dateC.compareTo(a.getEndDate()) <= 0) advertisementsToBePassed.add(a);
        }
        
        return advertisementsToBePassed;
    }
    
    @Override
    public Map<String, Integer> retrieveTotalNumberOfAdvertisementsForTheYear() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        Query query;
        Map<String, Integer> listingPerYear = new LinkedHashMap<>();
        List<String> months = Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        for (int i = 0; i < months.size(); i++) {
            query = em.createQuery("SELECT a FROM Advertisement a WHERE EXTRACT(YEAR(a.listDate)) = :inYear and EXTRACT(MONTH(a.listDate)) = :inMonth");
            query.setParameter("inYear", year);
            query.setParameter("inMonth", i + 1);
            listingPerYear.put(months.get(i), (query.getResultList()).size());
        }
        return listingPerYear;
    }
    
    @Override
    public Map<String, Integer> retrieveTotalNumberOfAdvertisementsForDay() {
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
                Logger.getLogger(AdvertisementSessionBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        Map<String, Integer> sevenDaysCast = new LinkedHashMap<>();

        for (int i = 0; i < sevenDays.size(); i++) {
            query = em.createQuery("SELECT a FROM Advertisement a WHERE a.listDate between :inStartDate AND :inEndDate");
            query.setParameter("inStartDate", sevenDays.get(i));

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sevenDays.get(i));
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 0);

            query.setParameter("inEndDate", calendar.getTime());

            List<Advertisement> totalNumber = query.getResultList();
            sevenDaysCast.put(dformat.format(sevenDays.get(i)), totalNumber.size());
        }
        return sevenDaysCast;
    }
            
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Advertisement>>constraintViolations) {
        String msg = "Input data validation error!:";    
        for(ConstraintViolation constraintViolation:constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        return msg;
    }
}
