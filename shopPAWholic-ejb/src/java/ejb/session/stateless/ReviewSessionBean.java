/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Customer;
import entity.Listing;
import entity.Review;
import entity.Seller;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.CreateNewReviewException;
import util.exception.InputDataValidationException;
import util.exception.ReviewNotFoundException;

/**
 *
 * @author Shi Zhan
 */
@Stateless
public class ReviewSessionBean implements ReviewSessionBeanLocal {

    @EJB(name = "ListingSessionBeanLocal")
    private ListingSessionBeanLocal listingSessionBeanLocal;

    @PersistenceContext(unitName = "shopPAWholic-ejbPU")
    
    private EntityManager em;
    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public ReviewSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = (Validator) validatorFactory.getValidator();
    }
    
    @Override
    public Review createNewReview(Review review, Long listingId, Long customerId ) throws CreateNewReviewException, InputDataValidationException {
        Set<ConstraintViolation<Review>> constraintViolations;
        constraintViolations = validator.validate(review);
        
        if (constraintViolations.isEmpty()) {
            try {
                Listing listing = em.find(Listing.class, listingId);
                listing.getReviews().add(review);
                review.setListing(listing);
                
                Customer customer = em.find(Customer.class, customerId);
                customer.getReviews().add(review);
                review.setCustomer(customer);
                
                em.persist(review);
                em.flush();

                return review;
            } catch (Exception ex) {
                throw new CreateNewReviewException("An unexpected error has occurred: " + ex.getMessage());
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }

    @Override
    public void updateReview(Review review) throws InputDataValidationException {
        Set<ConstraintViolation<Review>>constraintViolations = validator.validate(review);
        if(constraintViolations.isEmpty()) {
            if(review.getReviewId()!= null) {
                em.merge(review);
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }
    
    @Override
    public void deleteReview(Long reviewId) throws ReviewNotFoundException {
        Review review = getReviewById(reviewId);
        em.remove(review);
    }
    
    @Override
    public List<Review> filterReviewByImage() {
        Query query = em.createQuery("SELECT r FROM Review r WHERE r.reviewPictures IS NOT EMPTY ORDER BY r.date DESC");
        List<Review> reviews = query.getResultList();
        return reviews;
    }
    
    @Override
    public List<Review> filterReviewByImageAndListingId(Long listingId) {
        List<Review> reviews = filterReviewByImage();
        List<Review> reviewstoreturn = new ArrayList<>();
        
        for(Review r: reviews) {
            if(r.getListing().getListingId() == listingId)
                reviewstoreturn.add(r);
        }
        return reviewstoreturn;
    }
    
    @Override
    public Review getReviewById(Long id) throws ReviewNotFoundException {
        Review review = em.find(Review.class, id);
        if (review != null) return review;
        else throw new ReviewNotFoundException("Review ID " + id + " does not exist");
    }
    
    public List<Review> filterReviewsByListing(Long listingId) {
        Query query = em.createQuery("SELECT r FROM Review r WHERE r.listing.listingId = :inListingId");
        query.setParameter("inListingId", listingId);
        return query.getResultList();
    }
    
    @Override
    public List<Review> getReviewsRelatedGivenSellerId(Long sellerId) {
        Query q = em.createQuery("SELECT r FROM Review r WHERE r.listing.seller.userId = :inSellerId");
        q.setParameter("inSellerId", sellerId);
        return q.getResultList();
    }
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Review>>constraintViolations) {
        String msg = "Input data validation error!:";    
        for(ConstraintViolation constraintViolation:constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        return msg;
    }
}
