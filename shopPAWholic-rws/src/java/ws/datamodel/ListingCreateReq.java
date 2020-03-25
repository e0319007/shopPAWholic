/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

import java.util.List;
import entity.Listing;

/**
 *
 * @author shizhan
 */
public class ListingCreateReq {
    
    private String email;
    private String password;
    private Listing listing;
    private Long categoryId;
    private List<Long> tagIds;

    public ListingCreateReq() {
    }

    public ListingCreateReq(String email, String password, Listing listing, Long categoryId, List<Long> tagIds) {
        this.email = email;
        this.password = password;
        this.listing = listing;
        this.categoryId = categoryId;
        this.tagIds = tagIds;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Listing getListing() {
        return listing;
    }

    public void setListing(Listing listing) {
        this.listing = listing;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public List<Long> getTagIds() {
        return tagIds;
    }

    public void setTagIds(List<Long> tagIds) {
        this.tagIds = tagIds;
    }
    
    
    
}
