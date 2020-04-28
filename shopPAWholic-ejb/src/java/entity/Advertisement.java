package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

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
    private String picture;

    @URL
    private String url;


    @OneToOne(optional = false)
    private Seller seller;

  
    @OneToOne(optional = false) 
    private BillingDetail billingDetail;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date listDate;


    public Advertisement() {
        price = new BigDecimal("0.00");
    }

    public Advertisement(String description, Date startDate, Date endDate, BigDecimal price, String picture, String url, Date listDate) {
        this();
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.picture = picture;
        this.url = url;
        this.listDate = listDate;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the billingDetail
     */
    public BillingDetail getBillingDetail() {
        return billingDetail;
    }

    /**
     * @param billingDetail the billingDetail to set
     */
    public void setBillingDetail(BillingDetail billingDetail) {
        this.billingDetail = billingDetail;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public Date getListDate() {
        return listDate;
    }

    public void setListDate(Date listDate) {
        this.listDate = listDate;
    }

}
