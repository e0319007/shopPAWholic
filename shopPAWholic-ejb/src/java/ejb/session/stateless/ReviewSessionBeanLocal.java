/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Listing;
import entity.Review;
import java.util.List;
import javax.ejb.Local;
import util.exception.CreateNewReviewException;
import util.exception.InputDataValidationException;
import util.exception.ReviewNotFoundException;

/**
 *
 * @author Shi Zhan
 */
@Local
public interface ReviewSessionBeanLocal {
    
    public void updateReview(Review review) throws InputDataValidationException;

    public void deleteReview(Long reviewId) throws ReviewNotFoundException;

    public List<Review> filterReviewByImage();

    public Review getReviewById(Long id) throws ReviewNotFoundException;

    public Review createNewReview(Review review, Long listingId, Long customerId) throws CreateNewReviewException, InputDataValidationException;

    public List<Review> filterReviewByImageAndListingId(Long listingId);

    public List<Review> getReviewsRelatedGivenSellerId(Long sellerId);

    public List<Review> filterReviewsByListing(Long listingId);
    
}
