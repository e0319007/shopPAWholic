/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;
import java.util.List;

import entity.Advertisement;

/**
 *
 * @author shizhan
 */
public class AdvertisementRetrieveAllRsp {
    
    private List<Advertisement> advertisement;

    public AdvertisementRetrieveAllRsp() {
    }

    public AdvertisementRetrieveAllRsp(List<Advertisement> advertisement) {
        this.advertisement = advertisement;
    }

    public List<Advertisement> getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(List<Advertisement> advertisement) {
        this.advertisement = advertisement;
    }
    
}
