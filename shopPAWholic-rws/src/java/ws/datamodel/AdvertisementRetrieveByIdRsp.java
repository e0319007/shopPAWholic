/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

import entity.Advertisement;

/**
 *
 * @author shizhan
 */
public class AdvertisementRetrieveByIdRsp {
    private Advertisement ad;

    public AdvertisementRetrieveByIdRsp() {
    }

    public AdvertisementRetrieveByIdRsp(Advertisement ad) {
        this.ad = ad;
    }

    public Advertisement getAd() {
        return ad;
    }

    public void setAd(Advertisement ad) {
        this.ad = ad;
    }
    
}
