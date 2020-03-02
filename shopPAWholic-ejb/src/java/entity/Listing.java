/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 *
 * @author EileenLeong
 */
@Entity
public class Listing implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long listingId;
    private String skuCode;
    private String name;
    private String description;
    private Integer quantityOnHand;
    private BigDecimal unitPrice; 
    
    @ManyToOne(optional = false) 
    @JoinColumn(nullable = false)
    private Category category; 
    
    @ManyToMany(mappedBy="listings")
    private List<Tag> tags; 

    public Listing() {
        quantityOnHand = 0;
        unitPrice = new BigDecimal("0.00");
        tags = new ArrayList<>();
    }

    public Listing(String skuCode, String name, String description, Integer quantityOnHand, BigDecimal unitPrice) {
        this();
        this.skuCode = skuCode;
        this.name = name;
        this.description = description;
        this.quantityOnHand = quantityOnHand;
        this.unitPrice = unitPrice;
    }
    
    public void addTag(Tag tag) {
        if (tag != null) {
            if (!this.tags.contains(tag)) {
                this.tags.add(tag);
                if (!tag.getListings().contains(this)) {
                    tag.getListings().add(this);
                }
            }
        }
    }
    
    public void removeTag(Tag tag) {
        if (tag != null) {
            if (this.tags.contains(tag)) {
                this.tags.remove(tag);
                if (tag.getListings().contains(this)) {
                    tag.getListings().remove(this);
                }
            }
        }
    }
    
    public Long getListingId() {
        return listingId;
    }

    public void setListingId(Long listingId) {
        this.listingId = listingId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (listingId != null ? listingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the listingId fields are not set
        if (!(object instanceof Listing)) {
            return false;
        }
        Listing other = (Listing) object;
        if ((this.listingId == null && other.listingId != null) || (this.listingId != null && !this.listingId.equals(other.listingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Listing[ id=" + listingId + " ]";
    }

    /**
     * @return the skuCode
     */
    public String getSkuCode() {
        return skuCode;
    }

    /**
     * @param skuCode the skuCode to set
     */
    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the quantityOnHand
     */
    public Integer getQuantityOnHand() {
        return quantityOnHand;
    }

    /**
     * @param quantityOnHand the quantityOnHand to set
     */
    public void setQuantityOnHand(Integer quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }

    /**
     * @return the unitPrice
     */
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    /**
     * @param unitPrice the unitPrice to set
     */
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * @return the category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(Category category) {
        if (this.category != null) {
            if (this.category.getListings().contains(this)) {
                this.category.getListings().remove(this);
            }
        }
        this.category = category; 
        if (this.category != null) {
            if (!this.category.getListings().contains(this)) {
                this.category.getListings().add(this);
            }
        }
    }

    /**
     * @return the tags
     */
    public List<Tag> getTags() {
        return tags;
    }

    /**
     * @param tags the tags to set
     */
    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
    
}
