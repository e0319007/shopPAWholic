/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

import entity.Review;

/**
 *
 * @author Joanna Ng
 */
public class ReviewUpdateReq {
    
    private Review review;
    private Long listingId;
    private String email;
    private String password;

    public ReviewUpdateReq() {
    }

    public ReviewUpdateReq(Review review, Long listingId, String email, String password) {
        this.review = review;
        this.listingId = listingId;
        this.email = email;
        this.password = password;
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
    
    
    
}