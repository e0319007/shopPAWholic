package ejb.session.singleton;

import ejb.session.stateless.AdminSessionBeanLocal;
import ejb.session.stateless.AdvertisementSessionBeanLocal;
import ejb.session.stateless.CategorySessionBeanLocal;
import ejb.session.stateless.DeliveryDetailSessionBeanLocal;
import ejb.session.stateless.EventSessionBeanLocal;
import ejb.session.stateless.ListingSessionBeanLocal;
import ejb.session.stateless.OrderSessionBeanLocal;
import ejb.session.stateless.ReviewSessionBeanLocal;
import ejb.session.stateless.TagSessionBeanLocal;
import ejb.session.stateless.UserSessionBeanLocal;
import entity.Admin;
import entity.Advertisement;
import entity.Cart;
import entity.Category;
import entity.Customer;
import entity.DeliveryDetail;
import entity.Event;
import entity.Listing;
import entity.OrderEntity;
import entity.Review;
import entity.Seller;
import entity.Tag;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.enumeration.DeliveryMethod;
import util.exception.AdminUsernameExistException;
import util.exception.CreateNewAdvertisementException;
import util.exception.CreateNewCategoryException;
import util.exception.CreateNewDeliveryDetailException;
import util.exception.CreateNewEventException;
import util.exception.CreateNewListingException;
import util.exception.CreateNewOrderException;
import util.exception.CreateNewReviewException;
import util.exception.CreateNewTagException;
import util.exception.EventNameExistsException;
import util.exception.InputDataValidationException;
import util.exception.ListingSkuCodeExistException;
import util.exception.UnknownPersistenceException;
import util.exception.UserUsernameExistException;

@Singleton
@LocalBean
@Startup
public class DataInitSessionBean {

    @EJB(name = "EventSessionBeanLocal")
    private EventSessionBeanLocal eventSessionBeanLocal;

    @EJB(name = "ReviewSessionBeanLocal")
    private ReviewSessionBeanLocal reviewSessionBeanLocal;

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
        if (em.find(Admin.class, 1l) == null) {
            initializeData();
        }

    }

    private void initializeData() {
        try {
            adminSessionBeanLocal.createNewAdmin(new Admin("admin", "password"));
            Category categoryCatFood = categorySessionBeanLocal.createNewCategory(new Category("Cat Food", "Cat Food"));
            Category categoryPetToy = categorySessionBeanLocal.createNewCategory(new Category("Pet Toy", "Pet Toy"));
            Category categoryDogFood = categorySessionBeanLocal.createNewCategory(new Category("Dog Food", "Dog Food"));
            Category categoryLitters = categorySessionBeanLocal.createNewCategory(new Category("Litters", "Litters"));
            Category categoryPetGrooming = categorySessionBeanLocal.createNewCategory(new Category("Cat Grooming", "Cat Grooming"));
            Category categoryPetAccessories = categorySessionBeanLocal.createNewCategory(new Category("Dog Accessories", "Dog Accessories"));
            Category categoryDogCatBeds = categorySessionBeanLocal.createNewCategory(new Category("Dog/Cat Beds", "Dog/Cat Beds"));
            Category categoryOthers = categorySessionBeanLocal.createNewCategory(new Category("Others", "Others"));

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
            Seller seller = new Seller("Seller One", "sellerOne@email.com", "97652379", "password", new Date(), false, 0);
            Seller seller1 = new Seller("Sam Loves Dog", "samlovedog@email.com", "96541149", "samlovedog", new Date(120, 2, 20), false, 0);
            Seller seller2 = new Seller("Pet Lover Center", "petlovercenter@email.com", "61264625", "petlover", new Date(120, 0, 2), false, 0);
            Seller seller3 = new Seller("pawPAWpaw", "p3@email.com", "94158293", "pawpawpaw", new Date(120, 1, 22), false, 0);
            Seller seller4 = new Seller("Pets Unlimited", "petsunlimited@email.com", "67906027", "petsunlimited", new Date(120, 0, 6), false, 0);
            Seller seller5 = new Seller("Happy Tails Pets", "happytailspets@email.com", "92115371", "happytails", new Date(120, 2, 3), false, 0);
            Seller seller6 = new Seller("We Love Pets", "welovepets@email.com", "64622592", "welovepets", new Date(120, 2, 5), false, 0);
            Seller seller7 = new Seller("The Pet Shop", "thepetshop@email.com", "83532282", "thepetshop", new Date(120, 1, 24), false, 0);
            Seller seller8 = new Seller("Bow Meow", "bowmeow@email.com", "97818413", "bowmeow", new Date(120, 2, 12), false, 0);
            Seller seller9 = new Seller("Puppy Love", "puppylove@email.com", "63788800", "puppylove", new Date(120, 1, 13), false, 0);
            Seller seller10 = new Seller("Furry Cute", "furrycute@email.com", "64188899", "furrycute", new Date(120, 0, 22), false, 0);
            Seller seller11 = new Seller("Furry Wheel", "furrywheel@email.com", "61713887", "furrywheel", new Date(119, 11, 22), false, 0);
            Seller seller12 = new Seller("Happy Paws", "haoppyPawse@email.com", "85291432", "happypaw", new Date(119, 10, 22), false, 0);
            Seller seller13 = new Seller("Pets Love", "petslove@email.com", "91506502", "welove2016pets", new Date(119, 7, 22), false, 0);
            Seller seller14 = new Seller("Pets' Station", "petsstation@email.com", "61480093", "pets2019station", new Date(119, 6, 12), false, 0);
            Seller seller15 = new Seller("Superpets", "superp3ts@email.com", "94601631", "superP3TS", new Date(119, 0, 14), false, 0);
            Seller seller16 = new Seller("All 4 Pets", "all4pets@email.com", "90760582", "All4Pets", new Date(119, 1, 14), false, 0);
            Seller seller17 = new Seller("Dunk N' Dogs", "dnd@email.com", "67163148", "DnD2020", new Date(119, 7, 3), false, 0);
            Seller seller18 = new Seller("Furbaby", "furbaby@email.com", "61862980", "furbabyyy", new Date(119, 5, 5), false, 0);
            Seller seller19 = new Seller("ZooKeeper", "zookeeper@email.com", "91482340", "z00keeper_pwd", new Date(119, 8, 14), false, 0);
            Seller seller20 = new Seller("City Dog", "cityDogs@email.com", "99330096", "cityDOG", new Date(119, 0, 28), false, 0);

            Customer customer = new Customer("Customer One", "customerOne@email.com", "90657688", "password", new Date());
            Customer customer1 = new Customer("Serene Tan", "serenetan@email.com", "99696055", "p123@serene", new Date(120, 2, 22));
            Customer customer2 = new Customer("Kenny Singh", "kennysingh@email.com", "91234567", "password", new Date(120, 1, 19));
            Customer customer3 = new Customer("Alysha Choo", "alyshachoo90@email.com", "91234567", "password", new Date(120, 0, 12));
            Customer customer4 = new Customer("Demond Aw", "demondaw@email.com", "91234567", "password", new Date(120, 0, 16));
            Customer customer5 = new Customer("Beatrice Eng", "beaeng97@email.com", "91234567", "password", new Date(120, 3, 2));
            Customer customer6 = new Customer("Claudia Zhang", "claudiaz@email.com", "91234567", "password", new Date(120, 0, 27));
            Customer customer7 = new Customer("Timothy Leong", "timleong@email.com", "91234567", "password", new Date(120, 2, 23));
            Customer customer8 = new Customer("Fatin Tan", "fatintan@email.com", "91234567", "password", new Date(120, 1, 17));
            Customer customer9 = new Customer("Dave Kang", "dave_kang@email.com", "91234567", "password", new Date(120, 1, 19));
            Customer customer10 = new Customer("Kimberly Wee", "kimwee99@email.com", "91234567", "password", new Date(120, 2, 18));
            Customer customer11 = new Customer("Hew Yee Qing", "yeeqinghew@gmail.com", "98517588", "password", new Date(120, 3, 10));
            Customer customer12 = new Customer("Ed Fu", "edfued34@gmail.com", "93332597", "password", new Date(120, 3, 11));
            Customer customer13 = new Customer("Jonas Cheah Kah Hwee", "jonasche54@gmail.com", "97024878", "password", new Date(120, 3, 14));
            Customer customer14 = new Customer("Casey Chye", "caseychy71@gmail.com", "85609137", "password", new Date(120, 3, 13));
            Customer customer15 = new Customer("Anabelle Ho", "anabelle10@gmail.com", "94532085", "password", new Date(120, 3, 12));
            Customer customer16 = new Customer("Nathaniel Shum", "nathanie76@gmail.com", "95801494", "password", new Date(120, 3, 17));
            Customer customer17 = new Customer("Tan Wee Tat", "tanweeta43@gmail.com", "95511885", "password", new Date(120, 3, 16));
            Customer customer18 = new Customer("Graham Yap Guowei", "grahamya83@yahoo.com.sg", "80395710", "password", new Date(120, 3, 15));
            Customer customer19 = new Customer("Gene Lin Guohui", "geneling14@gmail.com", "92560381", "password", new Date(120, 3, 14));
            Customer customer20 = new Customer("Elaina Fu", "elainafu30@yahoo.com.sg", "98381476", "password", new Date(120, 3, 13));
            Customer customer21 = new Customer("Teo Mui Ling Chloe", "teomuili54@gmail.com", "98195908", "password", new Date(120, 3, 12));
            Customer customer22 = new Customer("Soh Min Li Daphne", "sohminli3@gmail.com", "87417338", "password", new Date(120, 3, 11));
            Customer customer23 = new Customer("Yusuf Mohamad", "yusufmoh31@gmail.com", "93786859", "password", new Date(120, 3, 10));
            userSessionBeanLocal.createNewUser(seller);
            userSessionBeanLocal.createNewUser(seller1);
            userSessionBeanLocal.createNewUser(seller2);
            userSessionBeanLocal.createNewUser(seller3);
            userSessionBeanLocal.createNewUser(seller4);
            userSessionBeanLocal.createNewUser(seller5);
            userSessionBeanLocal.createNewUser(seller6);
            userSessionBeanLocal.createNewUser(seller7);
            userSessionBeanLocal.createNewUser(seller8);
            userSessionBeanLocal.createNewUser(seller9);
            userSessionBeanLocal.createNewUser(seller10);
            userSessionBeanLocal.createNewUser(seller11);
            userSessionBeanLocal.createNewUser(seller12);
            userSessionBeanLocal.createNewUser(seller13);
            userSessionBeanLocal.createNewUser(seller14);
            userSessionBeanLocal.createNewUser(seller15);
            userSessionBeanLocal.createNewUser(seller16);
            userSessionBeanLocal.createNewUser(seller17);
            userSessionBeanLocal.createNewUser(seller18);
            userSessionBeanLocal.createNewUser(seller19);
            userSessionBeanLocal.createNewUser(seller20);

            userSessionBeanLocal.createNewUser(customer);
            userSessionBeanLocal.createNewUser(customer1);
            userSessionBeanLocal.createNewUser(customer2);
            userSessionBeanLocal.createNewUser(customer3);
            userSessionBeanLocal.createNewUser(customer4);
            userSessionBeanLocal.createNewUser(customer5);
            userSessionBeanLocal.createNewUser(customer6);
            userSessionBeanLocal.createNewUser(customer7);
            userSessionBeanLocal.createNewUser(customer8);
            userSessionBeanLocal.createNewUser(customer9);
            userSessionBeanLocal.createNewUser(customer10);
            userSessionBeanLocal.createNewUser(customer11);
            userSessionBeanLocal.createNewUser(customer12);
            userSessionBeanLocal.createNewUser(customer13);
            userSessionBeanLocal.createNewUser(customer14);
            userSessionBeanLocal.createNewUser(customer15);
            userSessionBeanLocal.createNewUser(customer16);
            userSessionBeanLocal.createNewUser(customer17);
            userSessionBeanLocal.createNewUser(customer18);
            userSessionBeanLocal.createNewUser(customer19);
            userSessionBeanLocal.createNewUser(customer20);
            userSessionBeanLocal.createNewUser(customer21);
            userSessionBeanLocal.createNewUser(customer22);
            userSessionBeanLocal.createNewUser(customer23);

            em.flush();

            Date date = new Date(System.currentTimeMillis());

            String picture = "https://www.k9natural.com/wp-content/uploads/2018/11/K9-Natural-Can-170g-Hoki-Beef-600x462.png";
            listingSessionBeanLocal.createNewListing(new Listing("LIST001", "Burp Cat Treat", "Cat Food, Burp Cat Treat 60g", new BigDecimal("10.00"), "https://sg-test-11.slatic.net/p/f64e8a88ed16ce54a37d4089f08cb339.jpg", 10, date), categoryCatFood.getCategoryId(), tagIdsPopular, seller1.getUserId());
            listingSessionBeanLocal.createNewListing(new Listing("LIST002", "Royal Canin Feline Health Nutrition Kitten Food", "Royal Canin Feline Health Nutrition Kitten Wet Food 85g", new BigDecimal("20.00"), "https://cdn.shopify.com/s/files/1/1149/5008/products/Royal-Canin-Feline-Health-Nutrition-Kitten-Wet-Food-85g.jpg?v=1574670735", 20, date), categoryCatFood.getCategoryId(), tagIdsDiscount, seller2.getUserId());
            listingSessionBeanLocal.createNewListing(new Listing("LIST003", "Prama Smokey Baken for Dog", "Prama Smokey Baken for Dog 70g", new BigDecimal("30.00"), "https://cdn.shopify.com/s/files/1/0719/7239/products/prama-delicacy-snack-smoky-bacon-dog-treats-70g-bag-pawpy-kisses_583_grande.jpg?v=1579516561", 30, date), categoryDogFood.getCategoryId(), tagIdsPopularDiscount, seller3.getUserId());

            listingSessionBeanLocal.createNewListing(new Listing("LIST004", "Royal Canin Mini Indoor Dry Dog Food", "Royal Canin Mini Indoor Dry Dog Food 500g", new BigDecimal("10.00"), "https://cdn.shopify.com/s/files/1/1149/5008/products/Royal-Canin-Mini-Indoor-Adult_480x480.jpg?v=1566451667", 10, date), categoryDogFood.getCategoryId(), tagIdsEmpty, seller1.getUserId());
            listingSessionBeanLocal.createNewListing(new Listing("LIST005", "Cat litter box for kitten", "Cat litter box for kitten 50CM X 40CM", new BigDecimal("20.00"), "https://s3-ap-southeast-1.amazonaws.com/kohepets-old/cms_paper-cat-litter-singapore.jpg", 20, date), categoryLitters.getCategoryId(), tagIdsEmpty, seller5.getUserId());
            listingSessionBeanLocal.createNewListing(new Listing("LIST006", "Dog Litter Tray", "Pet Toilet for Dog, Pee Training Tray Litter Box for Dog Puppy Pet with Protection Wall", new BigDecimal("30.00"), "https://ae01.alicdn.com/kf/HTB1zOt1avjsK1Rjy1Xaq6zispXaP.jpg", 30, date), categoryLitters.getCategoryId(), tagIdsEmpty, seller4.getUserId());

            listingSessionBeanLocal.createNewListing(new Listing("LIST007", "Professional Grooming Rake Comb", "2 Sided Pet Detangler Tool - Matted Fur Remover - for Painless Removal of Knots, Tangles & Mats", new BigDecimal("10.00"), "https://images-na.ssl-images-amazon.com/images/I/61oXlk823AL._AC_UL320_.jpg", 10, date), categoryPetGrooming.getCategoryId(), tagIdsEmpty, seller8.getUserId());
            listingSessionBeanLocal.createNewListing(new Listing("LIST008", "Gmjay Pet Dematting Rake", "Comb Ergonomic de Matting Comb for Dogs and Cats Remove Mats and Tangles Coats Safely", new BigDecimal("20.00"), "https://images-na.ssl-images-amazon.com/images/I/518F7KGyYmL._AC_UL320_.jpg", 20, date), categoryPetGrooming.getCategoryId(), tagIdsEmpty, seller6.getUserId());
            listingSessionBeanLocal.createNewListing(new Listing("LIST009", "Reflective Dog Leash", "Nylon Rope Pet Running Tracking Leashes Long Lead Dog", new BigDecimal("15.00"), "https://ae01.alicdn.com/kf/HTB1YJrcJuGSBuNjSspbq6AiipXaJ/Reflective-Large-Dog-Leash-Nylon-Rope-Pet-Running-Tracking-Leashes-Long-Lead-Dog-Mountain-Climbing-Rope.jpg", 30, date), categoryPetAccessories.getCategoryId(), tagIdsEmpty, seller8.getUserId());

            listingSessionBeanLocal.createNewListing(new Listing("LIST010", "Pet Collar", "Adjustable Portable Nylon Dog Accessories", new BigDecimal("10.00"), "https://cf.shopee.sg/file/b458a74c878e1d07b1e6f67f2cd5a912", 10, date), categoryPetAccessories.getCategoryId(), tagIdsPopular, seller5.getUserId());
            listingSessionBeanLocal.createNewListing(new Listing("LIST011", "Listing X2", "Listing X2", new BigDecimal("20.00"), picture, 20, date), categoryPetToy.getCategoryId(), tagIdsDiscount, seller1.getUserId());
            listingSessionBeanLocal.createNewListing(new Listing("LIST012", "Cat Scratcher", "Marukan Brisky Tunnel Cat Scratcher", new BigDecimal("30.00"), "https://cdn.shopify.com/s/files/1/2977/2416/products/marukan-brisky-tunnel-cat-scratcher-3_1.jpg?v=1571711561", 30, date), categoryPetToy.getCategoryId(), tagIdsPopularDiscount, seller6.getUserId());

            listingSessionBeanLocal.createNewListing(new Listing("LIST013", "Goodie Dog Bone", "Dog Bone Kong Company Red", new BigDecimal("10.00"), "https://9ed48207422fa7fc5013-a6297eb5ec0f30e883355c8680f3b2d6.ssl.cf2.rackcdn.com/10014_1_1000x1000.jpg", 10, date), categoryPetToy.getCategoryId(), tagIdsEmpty, seller2.getUserId());
            listingSessionBeanLocal.createNewListing(new Listing("LIST014", "Pet Fusion Dog Bed", "Pet Fusion Dog Bed 70CM X 55CM", new BigDecimal("20.00"), "https://static.businessinsider.sg/2020/01/01/5e307b8724306a6f881a9dd7.png", 20, date), categoryDogCatBeds.getCategoryId(), tagIdsEmpty, seller3.getUserId());
            listingSessionBeanLocal.createNewListing(new Listing("LIST015", "Tuft + Paw Cat Cave Bed", "Tuft + Paw Cat Cave Bed 55CM", new BigDecimal("30.00"), "https://cdn.shopify.com/s/files/1/1511/7434/products/tuft-paw-cat-cave2_800x.jpg?v=1555446167", 30, date), categoryDogCatBeds.getCategoryId(), tagIdsEmpty, seller7.getUserId());

            listingSessionBeanLocal.createNewListing(new Listing("LIST016", "ZuPreem Nut Blend Diet for Medium/Large Birds", "ZuPreem Nut Blend Diet for Medium/Large Birds, 1kg", new BigDecimal("10.00"), "https://images-na.ssl-images-amazon.com/images/I/71Jvb0ViGKL._AC_SX425_.jpg", 20, date), categoryOthers.getCategoryId(), tagIdsEmpty, seller7.getUserId());
            listingSessionBeanLocal.createNewListing(new Listing("LIST017", "Hamster Wheel", "Love Hamster Wheel, Spinner Exercise Wheel", new BigDecimal("20.00"), "https://contestimg.wish.com/api/webimage/5a00103e6531681cb28c7025-large.jpg?cache_buster=f9ae0741b22a2cf38ec28a5e4149fb34", 20, date), categoryOthers.getCategoryId(), tagIdsEmpty, seller6.getUserId());
            listingSessionBeanLocal.createNewListing(new Listing("LIST018", "Ecological Fish Tank", "Fish tank small ecological fish tank office table fish tank", new BigDecimal("30.00"), "https://gd.image-gmkt.com/li/512/047/1138047512.g_0-w_g.jpg", 30, date), categoryOthers.getCategoryId(), tagIdsEmpty, seller8.getUserId());

            DeliveryDetail delivery = new DeliveryDetail("BLK 1 Street 1", "98765432", date, DeliveryMethod.QXPRESS);

            OrderEntity order = new OrderEntity(new BigDecimal(100), date);
            System.out.println("New Order Created with ID: " + order.getOrderId());
            System.out.println("New Order has price of: " + order.getTotalPrice());
            System.out.println("New Order has date of: " + order.getOrderDate());
            List<Listing> listings = new ArrayList<>();
            listings.add(em.find(Listing.class, 1l));
            listings.add(em.find(Listing.class, 1l));
            listings.add(em.find(Listing.class, 12l));
            deliveryDetailSessionBeanLocal.createNewDeliveryDetail(delivery);

            //orderSessionBeanLocal.createNewOrder(order, delivery.getDeliveryDetailId(), "1111 2222 3333 4444", customer.getUserId(), listings, listings.get(0).getSeller().getUserId());
            //String advPicture = "https://images.pexels.com/photos/1108099/pexels-photo-1108099.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500";
            String advPicture1 = "https://i.postimg.cc/ZRrn2HNm/pet-Advertisement1.jpg";
            String advPicture2 = "https://i.postimg.cc/bwSyVmPV/pet-Advertisement2.png";
            String advPicture3 = "https://i.postimg.cc/rmPZHNMg/pet-Advertisement3.jpg";
            String advPicture4 = "https://i.postimg.cc/KvzBjh7K/pet-Advertisement4.jpg";

            Advertisement advertisement1 = new Advertisement("Advertisement One", new Date(120, 0, 1), new Date(120, 1, 1), BigDecimal.TEN, advPicture1, "https://sgpets.com.sg/", new Date(120, 3, 20));
            Advertisement advertisement2 = new Advertisement("Advertisement Two", new Date(120, 0, 1), new Date(120, 1, 1), BigDecimal.TEN, advPicture2, "https://apetmart.com/", new Date(120, 3, 23));
            Advertisement advertisement3 = new Advertisement("Advertisement Three", new Date(120, 0, 1), new Date(120, 1, 1), BigDecimal.TEN, advPicture3, "https://thehoneycombers.com/singapore/event/pet-expo-singapore-2017/", new Date(120, 3, 23));
            Advertisement advertisement4 = new Advertisement("Advertisement Four", new Date(120, 0, 1), new Date(120, 1, 1), BigDecimal.TEN, advPicture4, "https://redmart.lazada.sg/shop-pet-supplies/", new Date(120, 3, 23));
            
            advertisementSessionBean.createNewAdvertisement(advertisement1, seller7.getUserId(), "4444 5555 6666 7777");
            advertisementSessionBean.createNewAdvertisement(advertisement2, seller8.getUserId(), "4444 5555 6666 8888");
            advertisementSessionBean.createNewAdvertisement(advertisement3, seller7.getUserId(), "4444 5555 6666 7777");
            advertisementSessionBean.createNewAdvertisement(advertisement4, seller8.getUserId(), "4444 5555 6666 8888");

            OrderEntity o = orderSessionBeanLocal.createNewOrder(order, delivery.getDeliveryDetailId(), "1111 2222 3333 4444", customer.getUserId(), listings, listings.get(0).getSeller().getUserId());
            System.out.println("OrderEntity id: " + o.getOrderId() + " created");
            System.out.println("OrderEntity has " + o.getListings().size() + " listings");

            System.out.println("initialise review");
            long listingIDtoPassIn = 1;
            Review review = new Review("Good Product", 5, date, new ArrayList<>());
            System.out.println("calling review");
            reviewSessionBeanLocal.createNewReview(review, listingIDtoPassIn, customer.getUserId());

            String eventPicture1 = "https://i.postimg.cc/mD8Fwm3p/pet-Event1.jpg";
            String eventPicture2 = "https://i.postimg.cc/Hx2Pt1hG/pet-Event2.jpg";
            String eventPicture3 = "https://i.postimg.cc/qRhr7LB6/pet-Event3.jpg";
            String eventPicture4 = "https://i.postimg.cc/0QbtRXkF/pet-Event4.jpg";

            Event event1 = new Event("Doggy Walk Run", "Bring your doggos for a run", "Bedok Reservoir", eventPicture1, new Date(120, 0, 1), new Date(120, 1, 1), "http://www.spca.org.sg/whatson_details.asp?id=119", new Date(120, 3, 19));
            Event event2 = new Event("Cat Walk Run", "Bring your cats for a run", "Tampines Park", eventPicture2, new Date(120, 0, 1), new Date(120, 1, 1), "https://weekender.com.sg/w/do/indulge-in-your-cat-obsession-at-singapores-first-ever-cat-festival/", new Date(120, 3, 18));
            Event event3 = new Event("Woof-A-Thon", "2.5km Buddy Walk", "Oasis Terraces, Punggol", eventPicture3, new Date(120, 0, 1), new Date(120, 1, 1), "https://www.myheart.org.sg/woof-a-thon/woof-a-thon-2019/", new Date(120, 3, 19));
            Event event4 = new Event("Dog Obedience Competition 2020", "Come and watch Singapore's top dogs in action", "Far East Plaza", eventPicture4, new Date(120, 0, 1), new Date(120, 1, 1), "https://waggie.com.sg/news/", new Date(120, 3, 18));

            eventSessionBeanLocal.createNewEvent(event1, seller7.getUserId());
            eventSessionBeanLocal.createNewEvent(event2, seller8.getUserId());
            eventSessionBeanLocal.createNewEvent(event3, seller7.getUserId());
            eventSessionBeanLocal.createNewEvent(event4, seller8.getUserId());

            Cart c = em.find(Cart.class, 1l);
            List<Listing> listingsToAddToCart = new ArrayList<>();
            listingsToAddToCart.add(em.find(Listing.class, 1l));
            listingsToAddToCart.add(em.find(Listing.class, 1l));
            listingsToAddToCart.add(em.find(Listing.class, 2l));
            System.out.println(listingsToAddToCart);
            c.setListings(listingsToAddToCart);
            c.setTotalPrice(new BigDecimal(40));
            c.setTotalQuantity(3);

        } catch (AdminUsernameExistException | CreateNewOrderException | ListingSkuCodeExistException | CreateNewDeliveryDetailException | CreateNewReviewException | CreateNewAdvertisementException | UnknownPersistenceException | InputDataValidationException | CreateNewCategoryException | CreateNewTagException | CreateNewListingException | UserUsernameExistException
                | CreateNewEventException | EventNameExistsException ex) {

//CreateNewOrderException
            ex.printStackTrace();
        } //catch (CreateNewOrderException ex) {
        //Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        //}
    }

    public void persist(Object object) {
        em.persist(object);
    }

}
