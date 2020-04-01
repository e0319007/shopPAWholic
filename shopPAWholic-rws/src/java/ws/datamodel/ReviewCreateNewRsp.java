/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

/**
 *
 * @author Joanna Ng
 */
public class ReviewCreateNewRsp {
    private Long reviewId;

    public ReviewCreateNewRsp() {
    }

    public ReviewCreateNewRsp(Long reviewId) {
        this.reviewId = reviewId;
    }

    /**
     * @return the reviewId
     */
    public Long getReviewId() {
        return reviewId;
    }

    /**
     * @param reviewId the reviewId to set
     */
    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }
    
    
    
}
