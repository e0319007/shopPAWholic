/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

import entity.Tag;

/**
 *
 * @author Joanna Ng
 */
public class TagCreateNewReq {
    
    private Tag tag;

    public TagCreateNewReq() {
    }

    public TagCreateNewReq(Tag tag) {
        this.tag = tag;
    }

    /**
     * @return the tag
     */
    public Tag getTag() {
        return tag;
    }

    /**
     * @param tag the tag to set
     */
    public void setTag(Tag tag) {
        this.tag = tag;
    }
    
    
    
}
