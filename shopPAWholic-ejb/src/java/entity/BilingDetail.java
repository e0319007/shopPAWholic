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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author Joanna Ng
 */
@Entity
public class BilingDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bilingDetailId;
    private String creditCardDetail;
    private Date date;

    @ManyToOne(optional = true)
    private Customer customer;
    
    @OneToOne (fetch = FetchType.LAZY)
    private Advertisement advertisement;

    public BilingDetail() {
    }

    public BilingDetail(Long bilingDetailId, String creditCardDetail, Date date, Customer customer, Advertisement advertisement) {
        this();
        this.bilingDetailId = bilingDetailId;
        this.creditCardDetail = creditCardDetail;
        this.date = date;
        this.customer = customer;
        this.advertisement = advertisement;
    }
    
    
    
    public Long getBilingDetailId() {
        return bilingDetailId;
    }

    public void setBilingDetailId(Long bilingDetailId) {
        this.bilingDetailId = bilingDetailId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bilingDetailId != null ? bilingDetailId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the bilingDetailId fields are not set
        if (!(object instanceof BilingDetail)) {
            return false;
        }
        BilingDetail other = (BilingDetail) object;
        if ((this.bilingDetailId == null && other.bilingDetailId != null) || (this.bilingDetailId != null && !this.bilingDetailId.equals(other.bilingDetailId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.BilingDetail[ id=" + bilingDetailId + " ]";
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
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
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
    
}
