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
public class SellerCreateNewRsp {
    
    private Long sellerId;

    public SellerCreateNewRsp(Long sellerId) {
        this.sellerId = sellerId;
    }

    /**
     * @return the sellerId
     */
    public Long getSellerId() {
        return sellerId;
    }

    /**
     * @param sellerId the sellerId to set
     */
    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    
    
    
}
