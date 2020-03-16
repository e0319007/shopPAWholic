/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Joanna Ng
 */
@Entity
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be more than $0")
    private BigDecimal totalPrice;
    @NotNull
    private int totalQuantity;
    
    @OneToOne (fetch = FetchType.LAZY)
    private Customer customer;
    
    @OneToMany
    private List<Listing> listings;

    public Cart() {
    }

    public Cart(Long cartId, BigDecimal totalPrice, int totalQuantity, Customer customer, List<Listing> listings) {
        this();
        this.cartId = cartId;
        this.totalPrice = totalPrice;
        this.totalQuantity = totalQuantity;
        this.customer = customer;
        this.listings = listings;
    }
    
    
    

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cartId != null ? cartId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the cartId fields are not set
        if (!(object instanceof Cart)) {
            return false;
        }
        Cart other = (Cart) object;
        if ((this.cartId == null && other.cartId != null) || (this.cartId != null && !this.cartId.equals(other.cartId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Cart[ id=" + cartId + " ]";
    }

    /**
     * @return the totalPrice
     */
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    /**
     * @param totalPrice the totalPrice to set
     */
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
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
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * @return the Listings
     */
    public List<Listing> getListings() {
        return listings;
    }

    /**
     * @param Listings the Listings to set
     */
    public void setListings(List<Listing> listings) {
        this.listings = listings;
    }
    
}
