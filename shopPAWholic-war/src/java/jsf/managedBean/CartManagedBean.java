/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import POJO.CheckoutListingPOJO;
import ejb.session.stateless.CartSessionBeanLocal;
import ejb.session.stateless.ListingSessionBeanLocal;
import ejb.session.stateless.OrderSessionBeanLocal;
import entity.BillingDetail;
import entity.Cart;
import entity.Customer;
import entity.DeliveryDetail;
import entity.Listing;
import entity.OrderEntity;
import entity.Seller;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import util.enumeration.OrderStatus;
import util.exception.CartNotFoundException;
import java.util.HashMap;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.exception.CreateNewOrderException;
import util.exception.InputDataValidationException;


/**
 *
 * @author Joanna Ng
 */
@Named(value = "cartManagedBean")
@SessionScoped
public class CartManagedBean implements Serializable{

    @EJB(name = "orderSessionBeanLocal")
    private OrderSessionBeanLocal orderSessionBeanLocal;

    @EJB(name = "CartSessionBeanLocal")
    private CartSessionBeanLocal cartSessionBeanLocal;

    @EJB(name ="ListingSessionBeanLocal")
    private ListingSessionBeanLocal listingSessionBeanLocal;
    
    
    
    private List<CheckoutListingPOJO> shoppingCart;
    private List<CheckoutListingPOJO> checkoutList; 
    private List<Listing> savedShoppingCart; //stable
    private boolean completeCheckoutOut = false;
    private BigDecimal selectedItemPrice;
    private int numOfListing;
    private int totalQuantity;
    private Long customerId;
    private Cart cart; 
    
    
    //create new billing detail for order entity
    private String ccNum;
     
    
    /**
     * Creates a new instance of CartManagedBean
     */
    public CartManagedBean() {
        shoppingCart = new ArrayList<>();
        checkoutList = new ArrayList<>();
        System.out.println("Called cart managed bean");
        Customer c = (Customer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentCustomerEntity");
        customerId = c.getUserId();
        cart = cartSessionBeanLocal.getCartByCustomerId(customerId);
//        cart.setCustomer(em.find(Customer.class, customerId));
    }
    
    //technically cart will be created with customer soooooooooooooooooo idt need a create cart
    
    //salesTransactionEntity not actl needed? is basically the same as the checkoutProductPOJO
    
    @PostConstruct
    public void PostConstruct() {
        savedShoppingCart = cart.getListings();
        for(Listing l:savedShoppingCart) {
            boolean isRepeatedItem = false;
            for(CheckoutListingPOJO itemQtyPair: shoppingCart) {
                if(l.getListingId() == itemQtyPair.getListing().getListingId()) {
                    isRepeatedItem = true;
                    itemQtyPair.setQuantity(itemQtyPair.getQuantity() + 1);
                }
            }
            if(isRepeatedItem == false) {
                CheckoutListingPOJO listPojo = new CheckoutListingPOJO(l, 1);
            }
        }
//        listings = listingSessionBeanLocal.retrieveAllListings();
    }
    
    @PreDestroy
    public void PreDestroy() throws CartNotFoundException {
        cartSessionBeanLocal.clearCart(cart.getCartId());
        for(CheckoutListingPOJO cl: shoppingCart) {
            cartSessionBeanLocal.addListingToCart(cl.getListing().getListingId(), cart.getCartId(), cl.getQuantity());
        }
    }
    
//    public void addListingToCart(ActionEvent event) {
//        CheckoutListingPOJO checkoutListingEntityToAdd = (CheckoutListingPOJO) event.getComponent().getAttributes().get("listingToAdd");
//        System.out.println("Num of products: " + getNumOfListing());
//        System.out.println("Product to add: " + checkoutListingEntityToAdd.getListing().getName());
//    
//        shoppingCart.add(checkoutListingEntityToAdd);
//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Product added successfully", null));
//
//}
//    
    public void checkout(ActionEvent event) {
        System.out.println("***going through checkout");
        Date orderDate = new Date();
        
        
        //creating a hashmap that maps seller to a list of checkoutpojo
        HashMap sellerMapListQtyPair = new HashMap<Seller, List<CheckoutListingPOJO>>();
        for(CheckoutListingPOJO itemQtyPair: checkoutList) {
            Seller seller = itemQtyPair.getListing().getSeller();
            if(sellerMapListQtyPair.containsKey(seller)) {
                List<CheckoutListingPOJO> newList = (List<CheckoutListingPOJO>) sellerMapListQtyPair.get(seller);
                newList.add(itemQtyPair);
                sellerMapListQtyPair.replace(seller, newList);
            } else {
                List<CheckoutListingPOJO> newList = new ArrayList<>();
                newList.add(itemQtyPair);
                sellerMapListQtyPair.put(seller, newList);
            }
        }
        
        //creating an order for each of the seller
        Iterator hmIterator = sellerMapListQtyPair.entrySet().iterator(); 
        while (hmIterator.hasNext()) { 
            Map.Entry mapElement = (Map.Entry)hmIterator.next(); 
            Seller seller = (Seller) mapElement.getKey();
            List<CheckoutListingPOJO> checkoutListings = (List<CheckoutListingPOJO>) mapElement.getValue();
            OrderEntity newOrder = new OrderEntity(totalPrice(checkoutListings), orderDate);
            try {
                orderSessionBeanLocal.createNewOrder(newOrder, ccNum, customerId, convertCheckoutPojoIntoListings(checkoutListings), seller.getUserId());
            } catch (CreateNewOrderException ex) {
                Logger.getLogger(CartManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InputDataValidationException ex) {
                Logger.getLogger(CartManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        
        //clear items checkout alr
        for(CheckoutListingPOJO clp: checkoutList) {
            shoppingCart.remove(clp);
        }
        
    }
    
    private List<Listing> convertCheckoutPojoIntoListings(List<CheckoutListingPOJO> listingpojos) {
        List<Listing> listings = new ArrayList<>();
        for(CheckoutListingPOJO cl: listingpojos) {
            for(int i = 0; i < cl.getQuantity(); i++) {
                listings.add(cl.getListing());
            }
        }
        return listings;
    }
    
    private BigDecimal totalPrice(List<CheckoutListingPOJO> clp) {
        BigDecimal price = BigDecimal.ZERO;
        for(CheckoutListingPOJO item : clp) {
            price.add(item.getListing().getUnitPrice().multiply(new BigDecimal(item.getQuantity())));
        }
        return price;
    }
     
     public void message() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Test message", null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * @return the shoppingCart
     */
    public List<CheckoutListingPOJO> getShoppingCart() {
        return shoppingCart;
    }

    /**
     * @param shoppingCart the shoppingCart to set
     */
    public void setShoppingCart(List<CheckoutListingPOJO> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    /**
     * @return the checkoutList
     */
    public List<CheckoutListingPOJO> getCheckoutList() {
        return checkoutList;
    }

    /**
     * @param checkoutList the checkoutList to set
     */
    public void setCheckoutList(List<CheckoutListingPOJO> checkoutList) {
        this.checkoutList = checkoutList;
    }

    /**
     * @return the completeCheckoutOut
     */
    public boolean isCompleteCheckoutOut() {
        return completeCheckoutOut;
    }

    /**
     * @param completeCheckoutOut the completeCheckoutOut to set
     */
    public void setCompleteCheckoutOut(boolean completeCheckoutOut) {
        this.completeCheckoutOut = completeCheckoutOut;
    }

    /**
     * @return the selectedItemPrice
     */
    public BigDecimal getSelectedItemPrice() {
        return selectedItemPrice;
    }

    /**
     * @param selectedItemPrice the selectedItemPrice to set
     */
    public void setSelectedItemPrice(BigDecimal selectedItemPrice) {
        this.selectedItemPrice = selectedItemPrice;
    }

    /**
     * @return the numOfListing
     */
    public int getNumOfListing() {
        return numOfListing;
    }

    /**
     * @param numOfListing the numOfListing to set
     */
    public void setNumOfListing(int numOfListing) {
        this.numOfListing = numOfListing;
    }

    /**
     * @return the totalQuantity
     */
    public int getTotalQuantity() {
        return totalQuantity;
    }

    /**
     * @param totalQuantity the totalQuantity to set
     */
    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    /**
     * @return the customerId
     */
    public Long getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId the customerId to set
     */
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    /**
     * @return the cartSessionBeanLocal
     */
    public CartSessionBeanLocal getCartSessionBeanLocal() {
        return cartSessionBeanLocal;
    }

    /**
     * @param cartSessionBeanLocal the cartSessionBeanLocal to set
     */
    public void setCartSessionBeanLocal(CartSessionBeanLocal cartSessionBeanLocal) {
        this.cartSessionBeanLocal = cartSessionBeanLocal;
    }

    /**
     * @return the listingSessionBeanLocal
     */
    public ListingSessionBeanLocal getListingSessionBeanLocal() {
        return listingSessionBeanLocal;
    }

    /**
     * @param listingSessionBeanLocal the listingSessionBeanLocal to set
     */
    public void setListingSessionBeanLocal(ListingSessionBeanLocal listingSessionBeanLocal) {
        this.listingSessionBeanLocal = listingSessionBeanLocal;
    }

    
}
