/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import util.enumeration.DeliveryMethod;

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
    @Size(min = 5, message = "Address must be longer than 5 characters")
    private String address;
    @Size(min = 8)
    @Pattern(regexp = "[6|8|9]\\d{7}")
    private String contactNumber;
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date deliveryDate;
    @NotNull
    private List<String> statusLists;
    @NotNull
    private BigDecimal deliveryPrice;

    @Enumerated(EnumType.STRING)
    private DeliveryMethod deliveryMethod;

    public DeliveryDetail() {
        deliveryMethod = DeliveryMethod.SINGPOST_REGULAR;
        statusLists = new ArrayList<>();
    }

    public DeliveryDetail(String address, String contactNumber, Date date, DeliveryMethod deliveryMethod) {
        this();

        this.address = address;
        this.contactNumber = contactNumber;
        this.deliveryDate = date;
        this.deliveryMethod = deliveryMethod;
        double dp = 0;
        if (deliveryMethod.equals(DeliveryMethod.SINGPOST_REGULAR)) {
            dp = 1.50;
        } else if (deliveryMethod.equals(DeliveryMethod.SINGPOST_REGISTERED)) {
            dp = 3.50;
        } else if (deliveryMethod.equals(DeliveryMethod.QXPRESS)) {
            dp = 5;
        } else if (deliveryMethod.equals(DeliveryMethod.PARKNPARCEL)) {
            dp = 5.50;
        } else if (deliveryMethod.equals(DeliveryMethod.NINJAVAN)) {
            dp = 4;
        }
        deliveryPrice = new BigDecimal(dp);
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
     * @return the deliveryDate
     */
    public Date getDeliveryDate() {
        return deliveryDate;
    }

    /**
     * @param deliveryDate the deliveryDate to set
     */
    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    /**
     * @return the statusLists
     */
    public List<String> getStatusLists() {
        return statusLists;
    }

    /**
     * @param statusLists the statusLists to set
     */
    public void setStatusLists(List<String> statusLists) {
        this.statusLists = statusLists;
    }

    /**
     * @return the deliveryMethod
     */
    public DeliveryMethod getDeliveryMethod() {
        return deliveryMethod;
    }

    /**
     * @param deliveryMethod the deliveryMethod to set
     */
    public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public BigDecimal getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(BigDecimal deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

}
