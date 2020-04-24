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
public class AdvertisementCreateNewRsp {
    
    private Long advertisementId;

    public AdvertisementCreateNewRsp(Long advertisementId) {
        this.advertisementId = advertisementId;
    }

    /**
     * @return the advertisementId
     */
    public Long getAdvertisementId() {
        return advertisementId;
    }

    /**
     * @param advertisementId the advertisementId to set
     */
    public void setAdvertisementId(Long advertisementId) {
        this.advertisementId = advertisementId;
    }
    
    
    
}
