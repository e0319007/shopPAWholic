/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

import entity.Listing;
import java.util.List;
/**
 *
 * @author shizhan
 */
public class ListingsRetrieveAllRsp {
    private List<Listing> listings;

    
    
    public ListingsRetrieveAllRsp() {
    }
    
    
    
    public ListingsRetrieveAllRsp(List<Listing> listings) {
        this.listings = listings;
    }

    
    
    public List<Listing> getListings() {
        return listings;
    }

    public void setListings(List<Listing> listings) {
        this.listings = listings;
    }
}
