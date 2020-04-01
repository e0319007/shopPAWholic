package ejb.session.singleton;

import ejb.session.stateless.AdminSessionBeanLocal;
import ejb.session.stateless.AdvertisementSessionBeanLocal;
import ejb.session.stateless.CategorySessionBeanLocal;
import ejb.session.stateless.DeliveryDetailSessionBeanLocal;
import ejb.session.stateless.ListingSessionBeanLocal;
import ejb.session.stateless.OrderSessionBeanLocal;
import ejb.session.stateless.TagSessionBeanLocal;
import ejb.session.stateless.UserSessionBeanLocal;
import entity.Admin;
import entity.Advertisement;
import entity.Category;
import entity.Customer;
import entity.DeliveryDetail;
import entity.Listing;
import entity.OrderEntity;
import entity.Seller;
import entity.Tag;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.enumeration.DeliveryMethod;
import util.exception.AdminNotFoundException;
import util.exception.AdminUsernameExistException;
import util.exception.CreateNewAdvertisementException;
import util.exception.CreateNewCategoryException;
import util.exception.CreateNewDeliveryDetailException;
import util.exception.CreateNewListingException;
import util.exception.CreateNewOrderException;
import util.exception.CreateNewTagException;
import util.exception.InputDataValidationException;
import util.exception.ListingSkuCodeExistException;
import util.exception.UnknownPersistenceException;
import util.exception.UserUsernameExistException;

@Singleton
@LocalBean
@Startup
public class DataInitSessionBean {

    @EJB(name = "DeliveryDetailSessionBeanLocal")
    private DeliveryDetailSessionBeanLocal deliveryDetailSessionBeanLocal;

    @EJB(name = "OrderSessionBeanLocal")
    private OrderSessionBeanLocal orderSessionBeanLocal;

    @EJB
    private AdvertisementSessionBeanLocal advertisementSessionBean;

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
            
            Seller seller = new Seller("Seller One", "sellerOne@email.com", "98765432", "password", true, 0);
            Customer customer = new Customer("Customer One", "customerOne@email.com", "91234567", "password");
            userSessionBeanLocal.createNewUser(customer);
            userSessionBeanLocal.createNewUser(seller);
            em.flush();
            
            listingSessionBeanLocal.createNewListing(new Listing("LIST001", "Listing A1", "Listing A1", new BigDecimal("10.00"), 10), categoryA.getCategoryId(), tagIdsPopular, seller.getUserId());
            listingSessionBeanLocal.createNewListing(new Listing("LIST002", "Listing A2", "Listing A2", new BigDecimal("20.00"),  20), categoryA.getCategoryId(), tagIdsDiscount, seller.getUserId());
            listingSessionBeanLocal.createNewListing(new Listing("LIST003", "Listing A3", "Listing A3", new BigDecimal("30.00"),  30), categoryA.getCategoryId(), tagIdsPopularDiscount, seller.getUserId());
            
            listingSessionBeanLocal.createNewListing(new Listing("LIST004", "Listing B1", "Listing B1", new BigDecimal("10.00"),  10), categoryB.getCategoryId(), tagIdsEmpty, seller.getUserId());
            listingSessionBeanLocal.createNewListing(new Listing("LIST005", "Listing B2", "Listing B2", new BigDecimal("20.00"),  20), categoryB.getCategoryId(), tagIdsEmpty, seller.getUserId());
            listingSessionBeanLocal.createNewListing(new Listing("LIST006", "Listing B3", "Listing B3", new BigDecimal("30.00"),  30), categoryB.getCategoryId(), tagIdsEmpty, seller.getUserId());
            
            listingSessionBeanLocal.createNewListing(new Listing("LIST007", "Listing C1", "Listing C1", new BigDecimal("10.00"),  10), categoryC.getCategoryId(), tagIdsEmpty, seller.getUserId());
            listingSessionBeanLocal.createNewListing(new Listing("LIST008", "Listing C2", "Listing C2", new BigDecimal("20.00"),  20), categoryC.getCategoryId(), tagIdsEmpty, seller.getUserId());
            listingSessionBeanLocal.createNewListing(new Listing("LIST009", "Listing C3", "Listing C3", new BigDecimal("30.00"),  30), categoryC.getCategoryId(), tagIdsEmpty, seller.getUserId());
            
            listingSessionBeanLocal.createNewListing(new Listing("LIST010", "Listing X1", "Listing X1", new BigDecimal("10.00"),  10), categoryX.getCategoryId(), tagIdsPopular, seller.getUserId());
            listingSessionBeanLocal.createNewListing(new Listing("LIST011", "Listing X2", "Listing X2", new BigDecimal("20.00"),  20), categoryX.getCategoryId(), tagIdsDiscount, seller.getUserId());
            listingSessionBeanLocal.createNewListing(new Listing("LIST012", "Listing X3", "Listing X3", new BigDecimal("30.00"),  30), categoryX.getCategoryId(), tagIdsPopularDiscount, seller.getUserId());
            
            listingSessionBeanLocal.createNewListing(new Listing("LIST013", "Listing Y1", "Listing Y1", new BigDecimal("10.00"),  10), categoryY.getCategoryId(), tagIdsEmpty, seller.getUserId());
            listingSessionBeanLocal.createNewListing(new Listing("LIST014", "Listing Y2", "Listing Y2", new BigDecimal("20.00"),  20), categoryY.getCategoryId(), tagIdsEmpty, seller.getUserId());
            listingSessionBeanLocal.createNewListing(new Listing("LIST015", "Listing Y3", "Listing Y3", new BigDecimal("30.00"),  30), categoryY.getCategoryId(), tagIdsEmpty, seller.getUserId());
            
            listingSessionBeanLocal.createNewListing(new Listing("LIST016", "Listing Z1", "Listing Z1", new BigDecimal("10.00"), 10), categoryZ.getCategoryId(), tagIdsEmpty, seller.getUserId());
            listingSessionBeanLocal.createNewListing(new Listing("LIST017", "Listing Z2", "Listing Z2", new BigDecimal("20.00"),  20), categoryZ.getCategoryId(), tagIdsEmpty, seller.getUserId());
            listingSessionBeanLocal.createNewListing(new Listing("LIST019", "Listing Z3", "Listing Z3", new BigDecimal("30.00"),  30), categoryZ.getCategoryId(), tagIdsEmpty, seller.getUserId());
            
            DeliveryDetail delivery = new DeliveryDetail("BLK 1", "98765432", new Date(), DeliveryMethod.QXPRESS);
            OrderEntity order = new OrderEntity(new BigDecimal(100), new Date());
            List<Listing> listings = new ArrayList<>();
            listings.add(em.find(Listing.class, 1l));
            deliveryDetailSessionBeanLocal.createNewDeliveryDetail(delivery);
            orderSessionBeanLocal.createNewOrder(order, delivery.getDeliveryDetailId(), "1111 2222 3333 4444", customer.getUserId(), listings, listings.get(0).getSeller().getUserId());
           
            
            Advertisement advertisement1;
            List<String> pictures = new ArrayList<>();
            pictures.add("https://images.pexels.com/photos/1108099/pexels-photo-1108099.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");
            advertisement1 = new Advertisement("Advertisement One", new Date(2020, 3, 1), new Date(2020, 4, 1), BigDecimal.TEN, pictures, "https://images.pexels.com/photos/1108099/pexels-photo-1108099.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");
            advertisementSessionBean.createNewAdvertisement(advertisement1, seller.getUserId(), "4444 5555 6666 7777");
   
           
            
        } catch (AdminUsernameExistException | ListingSkuCodeExistException | CreateNewAdvertisementException | UnknownPersistenceException | InputDataValidationException | CreateNewCategoryException | CreateNewTagException | CreateNewListingException | UserUsernameExistException ex) {

            ex.printStackTrace();
        } catch (CreateNewDeliveryDetailException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CreateNewOrderException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    public void persist(Object object) {
        em.persist(object);
    }

    public void persist1(Object object) {
        em.persist(object);
    }

}
