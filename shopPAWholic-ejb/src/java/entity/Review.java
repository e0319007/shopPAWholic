/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Shi Zhan
 */
@Entity
public class Review implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private Long reviewId;
    @Size(min = 10, max = 250, message = "Description must be between 10 and 200 characters")
    private String description;
    @NotNull
    @Size(max = 5, min = 1)
    private int rating;
    @NotNull
    private Date reviewDate;
    @NotNull
    private List<String> reviewPictures;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Listing listing;
    
    @ManyToOne(optional = true) 
    @JoinColumn(nullable = true)
    private Customer customer; 
    
    public Review() {
        reviewPictures = new ArrayList<>();
    }

    public Review(String description, int rating, Date date, List<String> reviewPictures) {
        this();
        this.description = description;
        this.rating = rating;
        this.reviewDate = date;
        this.reviewPictures = reviewPictures;
    }
    
    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reviewId != null ? reviewId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the reviewId fields are not set
        if (!(object instanceof Review)) {
            return false;
        }
        Review other = (Review) object;
        if ((this.reviewId == null && other.reviewId != null) || (this.reviewId != null && !this.reviewId.equals(other.reviewId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Review[ id=" + reviewId + " ]";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }
    
//    public Listing getListing() {
//        return listing;
//    }
//
//    public void setListing(Listing listing) {
//        this.listing = listing;
//    }

    public List<String> getReviewPictures() {
        return reviewPictures;
    }

    public void setReviewPictures(List<String> reviewPictures) {
        this.reviewPictures = reviewPictures;
    }

    public Listing getListing() {
        return listing;
    }

    public void setListing(Listing listing) {
        this.listing = listing;
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
    
}
