/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

import entity.Review;
import java.util.List;

/**
 *
 * @author Joanna Ng
 */
public class ReviewCreateNewReq {
    
    private Review review;
    private int rating;
    private Long listingId;
    private String email;
    private String password;
    private String description;
    private List<String> reviewPictures;
    

    public ReviewCreateNewReq() {
    }

    public ReviewCreateNewReq(Review review, int rating, Long listingId, String email, String password, String description, List<String> reviewPictures) {
        this.review = review;
        this.rating = rating;
        this.listingId = listingId;
        this.email = email;
        this.password = password;
        this.description = description;
        this.reviewPictures = reviewPictures;
    }

    /**
     * @return the review
     */
    public Review getReview() {
        return review;
    }

    /**
     * @param review the review to set
     */
    public void setReview(Review review) {
        this.review = review;
    }

    /**
     * @return the listingId
     */
    public Long getListingId() {
        return listingId;
    }

    /**
     * @param listingId the listingId to set
     */
    public void setListingId(Long listingId) {
        this.listingId = listingId;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the rating
     */
    public int getRating() {
        return rating;
    }

    /**
     * @param rating the rating to set
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * @return the reviewPictures
     */
    public List<String> getReviewPictures() {
        return reviewPictures;
    }

    /**
     * @param reviewPictures the reviewPictures to set
     */
    public void setReviewPictures(List<String> reviewPictures) {
        this.reviewPictures = reviewPictures;
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
    
}
