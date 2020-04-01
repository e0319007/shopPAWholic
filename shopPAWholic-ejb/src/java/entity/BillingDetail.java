/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Joanna Ng
 */
@Entity
public class BillingDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billingDetailId;
    @NotNull
    private String creditCardDetail;
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date billingDate;

    @ManyToOne(optional = true)
    @JoinColumn(nullable = true)
    private Customer customer;
    
    @ManyToOne(optional = true)
    @JoinColumn(nullable = true)
    private Seller seller;
    
    @OneToOne (optional = true)
    private Advertisement advertisement;
    
    @OneToOne (optional = true)
    private OrderEntity order;
    
    

    public BillingDetail() {
    }

    public BillingDetail(String creditCardDetail, Date date) {
        this();
        
        this.creditCardDetail = creditCardDetail;
        this.billingDate = date;
        
        
    }
    
    
    
    public Long getBillingDetailId() {
        return billingDetailId;
    }

    public void setBillingDetailId(Long billingDetailId) {
        this.billingDetailId = billingDetailId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (billingDetailId != null ? billingDetailId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the billingDetailId fields are not set
        if (!(object instanceof BillingDetail)) {
            return false;
        }
        BillingDetail other = (BillingDetail) object;
        if ((this.billingDetailId == null && other.billingDetailId != null) || (this.billingDetailId != null && !this.billingDetailId.equals(other.billingDetailId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.BillingDetail[ id=" + billingDetailId + " ]";
    }

    /**
     * @return the creditCardDetail
     */
    public String getCreditCardDetail() {
        return creditCardDetail;
    }

    /**
     * @param creditCardDetail the creditCardDetail to set
     */
    public void setCreditCardDetail(String creditCardDetail) {
        this.creditCardDetail = creditCardDetail;
    }

    /**
     * @return the billingDate
     */
    public Date getBillingDate() {
        return billingDate;
    }

    /**
     * @param billingDate the billingDate to set
     */
    public void setBillingDate(Date billingDate) {
        this.billingDate = billingDate;
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
     * @return the advertisement
     */
    public Advertisement getAdvertisement() {
        return advertisement;
    }

    /**
     * @param advertisement the advertisement to set
     */
    public void setAdvertisement(Advertisement advertisement) {
        this.advertisement = advertisement;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }
    
}
