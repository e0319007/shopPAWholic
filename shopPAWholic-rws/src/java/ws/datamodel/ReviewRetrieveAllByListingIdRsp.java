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
public class ReviewRetrieveAllByListingIdRsp {
    
    private List<Review> reviews;

    public ReviewRetrieveAllByListingIdRsp() {
    }

    public ReviewRetrieveAllByListingIdRsp(List<Review> reviews) {
        this.reviews = reviews;
    }

    /**
     * @return the reviews
     */
    public List<Review> getReviews() {
        return reviews;
    }

    /**
     * @param reviews the reviews to set
     */
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
    
    
    
}
