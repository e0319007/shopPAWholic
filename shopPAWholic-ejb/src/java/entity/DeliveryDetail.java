/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author Joanna Ng
 */
@Entity
public class DeliveryDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliveryDetailId;
    @Size(min=5, message = "Address must be longer than 5 characters")
    private String address;
    @Pattern(regexp = "65[6|8|9]\\d{7}|\\")
    private String contactNumber;
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @NotNull
    private String statusLists;
    
    //add relationship to enum DeliveryMethod
    /*@Enumerated(EnumType.STRING)
    private DeliveryMethod deliveryMethod;*/
    
    @ManyToOne(optional = true)
    @JoinColumn(nullable = true)
    private Customer customer;

    public DeliveryDetail() {
    }

    public DeliveryDetail(Long deliveryDetailId, String address, String contactNumber, Date date, String statusLists, Customer customer) {
        this();
        this.deliveryDetailId = deliveryDetailId;
        this.address = address;
        this.contactNumber = contactNumber;
        this.date = date;
        this.statusLists = statusLists;
        this.customer = customer;
    }
    
    

    public Long getDeliveryDetailId() {
        return deliveryDetailId;
    }

    public void setDeliveryDetailId(Long deliveryDetailId) {
        this.deliveryDetailId = deliveryDetailId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (deliveryDetailId != null ? deliveryDetailId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the deliveryDetailId fields are not set
        if (!(object instanceof DeliveryDetail)) {
            return false;
        }
        DeliveryDetail other = (DeliveryDetail) object;
        if ((this.deliveryDetailId == null && other.deliveryDetailId != null) || (this.deliveryDetailId != null && !this.deliveryDetailId.equals(other.deliveryDetailId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.DeliveryDetail[ id=" + deliveryDetailId + " ]";
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
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the contactNumber
     */
    public String getContactNumber() {
        return contactNumber;
    }

    /**
     * @param contactNumber the contactNumber to set
     */
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
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
     * @return the statusLists
     */
    public String getStatusLists() {
        return statusLists;
    }

    /**
     * @param statusLists the statusLists to set
     */
    public void setStatusLists(String statusLists) {
        this.statusLists = statusLists;
    }
    
}
