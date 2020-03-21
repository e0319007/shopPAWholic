/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.ListingSessionBeanLocal;
import ejb.session.stateless.ReviewSessionBeanLocal;
import entity.Customer;
import entity.Event;
import entity.Listing;
import entity.Review;
import entity.Seller;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import util.exception.CreateNewReviewException;
import util.exception.InputDataValidationException;
import util.exception.ReviewNotFoundException;

/**
 *
 * @author zhan
 */
@Named(value = "reviewManagedBean")
@RequestScoped
public class ReviewManagedBean {

    @EJB(name = "ReviewSessionBeanLocal")
    private ReviewSessionBeanLocal reviewSessionBeanLocal;

    @EJB(name = "ListingSessionBeanLocal")
    private ListingSessionBeanLocal listingSessionBeanLocal;
    
    //for creating a new review
    private String description;
    private int rating;
    private Date reviewDate;
    private List<String> reviewPictures;
    private Listing listing;
    private Customer customer; 
    
    //for viewing reviews with given listing
    private List<Review> reviewsToView;
    private Listing selectedListingToViewReview;
    
    //for viewing all reviews of a given shop
    private List<Review> reviewsGivenShop;
    private Seller seller;
    
    /**
     * Creates a new instance of ReviewManagedBean
     */
    
    
    public ReviewManagedBean() {
    }
    
    @PostConstruct
    public void PostConstruct() {
        
    }
    
    public void createNewReview(ActionEvent event) {
        try {
            Review reviewToCreate = new Review(description, rating, reviewDate, reviewPictures);
            reviewSessionBeanLocal.createNewReview(reviewToCreate,customer.getUserId(), listing.getListingId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Thanks for reviewing!", null));
        } catch (CreateNewReviewException | InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new review: " + ex.getMessage(), null));
        }
    }
    
    public void deleteReview(ActionEvent event) {
        Review reviewToDelete = (Review) event.getComponent().getAttributes().get("reviewToDelete");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Review is deleted.", null));
        try {
            reviewSessionBeanLocal.deleteReview(reviewToDelete.getReviewId());
        } catch (ReviewNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Review with ID " + reviewToDelete.getReviewId() + " not found: " + ex.getMessage(), null));
        }
        
    }
    
    public void editReview(ActionEvent event) {
        Review reviewToEdit = (Review) event.getComponent().getAttributes().get("reviewToEdit");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Review is successfully edited.", null));
        try {
            reviewSessionBeanLocal.updateReview(reviewToEdit);
        } catch (InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Review with ID " + reviewToEdit.getReviewId() + " not found: " + ex.getMessage(), null));
        }
    }
    
    public void viewReviewOfListing() {
        reviewsToView = reviewSessionBeanLocal.filterReviewsByListing(selectedListingToViewReview.getListingId());
    }
    
    public void viewReviewOfSeller() {
        reviewsGivenShop = reviewSessionBeanLocal.getReviewsRelatedGivenSellerId(seller.getUserId());
    }

    public ReviewSessionBeanLocal getReviewSessionBeanLocal() {
        return reviewSessionBeanLocal;
    }

    public void setReviewSessionBeanLocal(ReviewSessionBeanLocal reviewSessionBeanLocal) {
        this.reviewSessionBeanLocal = reviewSessionBeanLocal;
    }

    public ListingSessionBeanLocal getListingSessionBeanLocal() {
        return listingSessionBeanLocal;
    }

    public void setListingSessionBeanLocal(ListingSessionBeanLocal listingSessionBeanLocal) {
        this.listingSessionBeanLocal = listingSessionBeanLocal;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Review> getReviewsToView() {
        return reviewsToView;
    }

    public void setReviewsToView(List<Review> reviewsToView) {
        this.reviewsToView = reviewsToView;
    }

    public Listing getSelectedListingToViewReview() {
        return selectedListingToViewReview;
    }

    public void setSelectedListingToViewReview(Listing selectedListingToViewReview) {
        this.selectedListingToViewReview = selectedListingToViewReview;
    }

    public List<Review> getReviewsGivenShop() {
        return reviewsGivenShop;
    }

    public void setReviewsGivenShop(List<Review> reviewsGivenShop) {
        this.reviewsGivenShop = reviewsGivenShop;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }
}
