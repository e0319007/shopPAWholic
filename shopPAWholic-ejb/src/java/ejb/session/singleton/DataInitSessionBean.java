package ejb.session.singleton;

import ejb.session.stateless.AdminSessionBeanLocal;
import ejb.session.stateless.CategorySessionBeanLocal;
import ejb.session.stateless.ListingSessionBeanLocal;
import ejb.session.stateless.TagSessionBeanLocal;
import ejb.session.stateless.UserSessionBeanLocal;
import entity.Admin;
import entity.Category;
import entity.Listing;
import entity.Tag;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.AdminNotFoundException;
import util.exception.AdminUsernameExistException;
import util.exception.CreateNewCategoryException;
import util.exception.CreateNewListingException;
import util.exception.CreateNewTagException;
import util.exception.InputDataValidationException;
import util.exception.ListingSkuCodeExistException;
import util.exception.UnknownPersistenceException;

@Singleton
@LocalBean
@Startup
public class DataInitSessionBean {

    @EJB(name = "CategorySessionBeanLocal")
    private CategorySessionBeanLocal categorySessionBeanLocal;

    @EJB(name = "TagSessionBeanLocal")
    private TagSessionBeanLocal tagSessionBeanLocal;

    @EJB(name = "ListingSessionBeanLocal")
    private ListingSessionBeanLocal listingSessionBeanLocal;

    @EJB(name = "UserSessionBeanLocal")
    private UserSessionBeanLocal userSessionBeanLocal;

    @PersistenceContext(unitName = "shopPAWholic-ejbPU")
    private EntityManager em;

    @EJB(name = "AdminSessionBeanLocal")
    private AdminSessionBeanLocal adminSessionBeanLocal;
    
    
    

    @PostConstruct
    public void postConstruct() {
        if(em.find(Admin.class, 1l) == null){
            initializeData();
        }
    }

    private void initializeData() {
        try {
            adminSessionBeanLocal.createNewAdmin(new Admin("manager", "password"));
            Category categoryPetFood = categorySessionBeanLocal.createNewCategory(new Category("Pet Food", "Pet Food"), null);
            Category categoryPetToy = categorySessionBeanLocal.createNewCategory(new Category("Pet Toy", "Pet Toy"), null);
            Category categoryA = categorySessionBeanLocal.createNewCategory(new Category("Category A", "Category A"), categoryPetFood.getCategoryId());
            Category categoryB = categorySessionBeanLocal.createNewCategory(new Category("Category B", "Category B"), categoryPetFood.getCategoryId());
            Category categoryC = categorySessionBeanLocal.createNewCategory(new Category("Category C", "Category C"), categoryPetFood.getCategoryId());
            Category categoryX = categorySessionBeanLocal.createNewCategory(new Category("Category X", "Category X"), categoryPetToy.getCategoryId());
            Category categoryY = categorySessionBeanLocal.createNewCategory(new Category("Category Y", "Category Y"), categoryPetToy.getCategoryId());
            Category categoryZ = categorySessionBeanLocal.createNewCategory(new Category("Category Z", "Category Z"), categoryPetToy.getCategoryId());
            
            Tag tagPopular = tagSessionBeanLocal.createNewTag(new Tag("popular"));
            Tag tagDiscount = tagSessionBeanLocal.createNewTag(new Tag("discount"));
            
            List<Long> tagIdsPopular = new ArrayList<>();
            tagIdsPopular.add(tagPopular.getTagId());
            
            List<Long> tagIdsDiscount = new ArrayList<>();
            tagIdsDiscount.add(tagDiscount.getTagId());
            
            List<Long> tagIdsPopularDiscount = new ArrayList<>();
            tagIdsPopularDiscount.add(tagPopular.getTagId());
            tagIdsPopularDiscount.add(tagDiscount.getTagId());

            List<Long> tagIdsEmpty = new ArrayList<>();
            
           // List<String> pictures = new ArrayList<>();
            //pictures.add("https://i.ibb.co/Hp2htdG/shop-PAWholic.png");
            
            listingSessionBeanLocal.createNewListing(new Listing("LIST001", "Listing A1", "Listing A1", new BigDecimal("10.00"), 10), categoryA.getCategoryId(), tagIdsPopular);
            listingSessionBeanLocal.createNewListing(new Listing("LIST002", "Listing A2", "Listing A2", new BigDecimal("20.00"),  20), categoryA.getCategoryId(), tagIdsDiscount);
            listingSessionBeanLocal.createNewListing(new Listing("LIST003", "Listing A3", "Listing A3", new BigDecimal("30.00"),  30), categoryA.getCategoryId(), tagIdsPopularDiscount);
            
            listingSessionBeanLocal.createNewListing(new Listing("LIST004", "Listing B1", "Listing B1", new BigDecimal("10.00"),  10), categoryB.getCategoryId(), tagIdsEmpty);
            listingSessionBeanLocal.createNewListing(new Listing("LIST005", "Listing B2", "Listing B2", new BigDecimal("20.00"),  20), categoryB.getCategoryId(), tagIdsEmpty);
            listingSessionBeanLocal.createNewListing(new Listing("LIST006", "Listing B3", "Listing B3", new BigDecimal("30.00"),  30), categoryB.getCategoryId(), tagIdsEmpty);
            
            listingSessionBeanLocal.createNewListing(new Listing("LIST007", "Listing C1", "Listing C1", new BigDecimal("10.00"),  10), categoryC.getCategoryId(), tagIdsEmpty);
            listingSessionBeanLocal.createNewListing(new Listing("LIST008", "Listing C2", "Listing C2", new BigDecimal("20.00"),  20), categoryC.getCategoryId(), tagIdsEmpty);
            listingSessionBeanLocal.createNewListing(new Listing("LIST009", "Listing C3", "Listing C3", new BigDecimal("30.00"),  30), categoryC.getCategoryId(), tagIdsEmpty);
            
            listingSessionBeanLocal.createNewListing(new Listing("LIST010", "Listing X1", "Listing X1", new BigDecimal("10.00"),  10), categoryX.getCategoryId(), tagIdsPopular);
            listingSessionBeanLocal.createNewListing(new Listing("LIST011", "Listing X2", "Listing X2", new BigDecimal("20.00"),  20), categoryX.getCategoryId(), tagIdsDiscount);
            listingSessionBeanLocal.createNewListing(new Listing("LIST012", "Listing X3", "Listing X3", new BigDecimal("30.00"),  30), categoryX.getCategoryId(), tagIdsPopularDiscount);
            
            listingSessionBeanLocal.createNewListing(new Listing("LIST013", "Listing Y1", "Listing Y1", new BigDecimal("10.00"),  10), categoryY.getCategoryId(), tagIdsEmpty);
            listingSessionBeanLocal.createNewListing(new Listing("LIST014", "Listing Y2", "Listing Y2", new BigDecimal("20.00"),  20), categoryY.getCategoryId(), tagIdsEmpty);
            listingSessionBeanLocal.createNewListing(new Listing("LIST015", "Listing Y3", "Listing Y3", new BigDecimal("30.00"),  30), categoryY.getCategoryId(), tagIdsEmpty);
            
            listingSessionBeanLocal.createNewListing(new Listing("LIST016", "Listing Z1", "Listing Z1", new BigDecimal("10.00"), 10), categoryZ.getCategoryId(), tagIdsEmpty);
            listingSessionBeanLocal.createNewListing(new Listing("LIST017", "Listing Z2", "Listing Z2", new BigDecimal("20.00"),  20), categoryZ.getCategoryId(), tagIdsEmpty);
            listingSessionBeanLocal.createNewListing(new Listing("LIST019", "Listing Z3", "Listing Z3", new BigDecimal("30.00"),  30), categoryZ.getCategoryId(), tagIdsEmpty);
        } catch (AdminUsernameExistException | ListingSkuCodeExistException | UnknownPersistenceException | InputDataValidationException | CreateNewCategoryException | CreateNewTagException | CreateNewListingException ex) {
            ex.printStackTrace();
        } 
    }

    public void persist(Object object) {
        em.persist(object);
    }

}
