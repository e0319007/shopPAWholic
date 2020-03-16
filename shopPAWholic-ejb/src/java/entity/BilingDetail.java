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
public class BilingDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bilingDetailId;
    @NotNull
    private String creditCardDetail;
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date bilingDate;

    @ManyToOne(optional = true)
    @JoinColumn(nullable = true)
    private Customer customer;
    
    @OneToOne (fetch = FetchType.LAZY)
    private Advertisement advertisement;
    
    

    public BilingDetail() {
    }

    public BilingDetail(String creditCardDetail, Date date) {
        this();
        
        this.creditCardDetail = creditCardDetail;
        this.bilingDate = date;
        
        
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
     * @return the bilingDate
     */
    public Date getBilingDate() {
        return bilingDate;
    }

    /**
     * @param bilingDate the bilingDate to set
     */
    public void setBilingDate(Date bilingDate) {
        this.bilingDate = bilingDate;
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
