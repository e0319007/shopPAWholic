/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

/**
 *
 * @author Shi Zhan
 */
@Entity
public class Advertisement implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long advertisementId;
    @Size(min = 10, max = 250, message = "Description must be between 10 and 200 characters")
    private String description;
    @NotNull
    private Date startDate;
    @NotNull
    private Date endDate;
    @DecimalMin(value = "0.0", inclusive = false, message = "Price most be more than $0")
    private BigDecimal price;
    @NotNull
    private List<String> pictures;
    @URL
    private String url;
    
   // @OneToOne(optional = false)
   // private ServiceProvider serviceProvider;

    public Advertisement() {
    }

    public Advertisement(String description, Date startDate, Date endDate, BigDecimal price, List<String> pictures, String url) {
        this();
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.pictures = pictures;
        this.url = url;
    }
    
    
    
    public Long getAdvertisementId() {
        return advertisementId;
    }

    public void setAdvertisementId(Long advertisementId) {
        this.advertisementId = advertisementId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (advertisementId != null ? advertisementId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the advertisementId fields are not set
        if (!(object instanceof Advertisement)) {
            return false;
        }
        Advertisement other = (Advertisement) object;
        if ((this.advertisementId == null && other.advertisementId != null) || (this.advertisementId != null && !this.advertisementId.equals(other.advertisementId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Advertisement[ id=" + advertisementId + " ]";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
}
