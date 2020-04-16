/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

import entity.Listing;

/**
 *
 * @author shizhan
 */
public class ListingRetrieveDetailRsp {
    private Listing listing;

    public ListingRetrieveDetailRsp() {
    }

    public ListingRetrieveDetailRsp(Listing listing) {
        this.listing = listing;
    }

    public Listing getListing() {
        return listing;
    }

    public void setListing(Listing listing) {
        this.listing = listing;
    }
    
}
