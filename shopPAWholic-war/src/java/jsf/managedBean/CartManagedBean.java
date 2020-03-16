/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import POJO.CheckoutListingPOJO;
import ejb.session.stateless.CartSessionBeanLocal;
import ejb.session.stateless.ListingSessionBeanLocal;
import entity.Cart;
import entity.Customer;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Joanna Ng
 */
@Named(value = "cartManagedBean")
@ApplicationScoped
public class CartManagedBean implements Serializable{

    @EJB(name = "CartSessionBeanLocal")
    private CartSessionBeanLocal cartSessionBeanLocal;

    @EJB(name ="ListingSessionBeanLocal")
    private ListingSessionBeanLocal listingSessionBeanLocal;
    
    private List<CheckoutListingPOJO> shoppingCart;
    private List<CheckoutListingPOJO> checkoutList;
    private boolean completeCheckoutOut = false;
    private BigDecimal selectedItemPrice;
    private int numOfListing;
    private int totalQuantity;
    private Long customerId;
    private Cart cart; 
    
    /**
     * Creates a new instance of CartManagedBean
     */
    public CartManagedBean() {
        shoppingCart = new ArrayList<>();
        checkoutList = new ArrayList<>();
        System.out.println("Called cart managed bean");
        Customer c = (Customer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentCustomerEntity");
        customerId = c.getUserId();
//        cart.setCustomer(em.find(Customer.class, customerId));
    }
    
    //salesTransactionEntity not actl needed? is basically the same as the checkoutProductPOJO
    
//    @PostConstruct
//    public void PostConstruct() {
//        listings = listingSessionBeanLocal.retrieveAllListings();
//    }
    
    
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
//        Date date = new Date();
        for (CheckoutListingPOJO clp:checkoutList) {
            BigDecimal subTotal;
            subTotal = clp.getListing().getUnitPrice();
            cart.getListings().add(clp.getListing());
        }
        
        //checkout what's in the cart
        //see shizhan's newTransactionWithoutStaff???
        
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
