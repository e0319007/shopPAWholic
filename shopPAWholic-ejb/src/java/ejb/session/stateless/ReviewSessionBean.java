/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Listing;
import entity.Review;
import java.util.List;
import java.util.Set;
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

    @PersistenceContext(unitName = "shopPAWholic-ejbPU")
    private EntityManager em;
    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public ReviewSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = (Validator) validatorFactory.getValidator();
    }
    
    @Override
    public Review createNewReview(Review review) throws CreateNewReviewException, InputDataValidationException {
        Set<ConstraintViolation<Review>> constraintViolations;
        constraintViolations = validator.validate(review);
        
        if (constraintViolations.isEmpty()) {
            try {
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
    public Review getReviewById(Long id) throws ReviewNotFoundException {
        Review review = em.find(Review.class, id);
        if (review != null) return review;
        else throw new ReviewNotFoundException("Review ID " + id + " does not exist");
    }
    
    public List<Review> filterReviewsByListing(Listing listingId) {
        Query query = em.createQuery("SELECT r FROM Review r WHERE r.listing.listingId = :inListingId");
        query.setParameter("inListingId", listingId);
        return query.getResultList();
    }
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Review>>constraintViolations) {
        String msg = "Input data validation error!:";    
        for(ConstraintViolation constraintViolation:constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        return msg;
    }
}
