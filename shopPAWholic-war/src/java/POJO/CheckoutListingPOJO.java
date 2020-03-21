/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POJO;

import entity.Listing;
import java.util.UUID;

/**
 *
 * @author Joanna Ng
 */
public class CheckoutListingPOJO {
    
    private Long checkoutProductId;
    private Listing listing;
    private int quantity;
    private String id = UUID.randomUUID().toString().substring(0, 8);
    
    
    //line transaction shit
    
    public CheckoutListingPOJO(Listing listing, int quantity) {
        this.listing = listing;
        this.quantity = quantity;
    }
    
    public Long getCheckoutProductId() {
        return checkoutProductId;
    }

    public void setCheckoutProductId(Long checkoutProductId) {
        this.checkoutProductId = checkoutProductId;
    }
    
    @Override
    public String toString() {
        return "checkoutProductPOJO[ id=" + checkoutProductId + " ]";
    }
    
    public Listing getListing() {
        return listing;
    }
    
    public void setListing(Listing listing) {
        this.listing = listing;
    }
    
//    public int getQuantity() {
//        return quantity;
//    }
//    
//    public void setQuantity(int quantity) {
//        this.quantity = quantity;
//    }
//    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
}
