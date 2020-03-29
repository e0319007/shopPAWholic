/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author EileenLeong
 */
@Entity
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    
    @Column(nullable = false, length = 32)
    @NotNull
    @Size(max = 32, message = "Category name must not be more than 32 characters")
    private String name;
    
    @Column(length = 128)
    @Size(max = 128, message = "Description must not be more than 128 characters")
    private String description; 
    
    @OneToMany(mappedBy="parentCategory")
    private List<Category> subCategories; 
    
    @ManyToOne
    //@JoinColumn(nullable = false)
    private Category parentCategory; 
    
    @OneToMany(mappedBy="category")
    private List<Listing> listings; 

    public Category() {
        subCategories = new ArrayList<>();
        listings = new ArrayList<>();
    }

    public Category(String name, String description) {
        this();
        this.name = name;
        this.description = description;
    }
    
    

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (categoryId != null ? categoryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the categoryId fields are not set
        if (!(object instanceof Category)) {
            return false;
        }
        Category other = (Category) object;
        if ((this.categoryId == null && other.categoryId != null) || (this.categoryId != null && !this.categoryId.equals(other.categoryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Category[ id=" + categoryId + " ]";
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
     * @return the subCategories
     */
    public List<Category> getSubCategories() {
        return subCategories;
    }

    /**
     * @param subCategories the subCategories to set
     */
    public void setSubCategories(List<Category> subCategories) {
        this.subCategories = subCategories;
    }

    /**
     * @return the parentCategory
     */
    public Category getParentCategory() {
        return parentCategory;
    }

    /**
     * @param parentCategory the parentCategory to set
     */
    public void setParentCategory(Category parentCategory) {
        if (this.parentCategory != null) {
            if (this.parentCategory.getSubCategories().contains(this)) {
                this.parentCategory.getSubCategories().remove(this);
            }
        }
        this.parentCategory = parentCategory; 
        
        if (this.parentCategory != null) {
            if (!this.parentCategory.getSubCategories().contains(this)) {
                this.parentCategory.getSubCategories().add(this);
            }
        }
    }

    /**
     * @return the listings
     */
    public List<Listing> getListings() {
        return listings;
    }

    /**
     * @param listings the listings to set
     */
    public void setListings(List<Listing> listings) {
        this.listings = listings;
    }
    
}
