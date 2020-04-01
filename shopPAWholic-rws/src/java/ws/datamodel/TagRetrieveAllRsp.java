/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

import entity.Tag;
import java.util.List;

/**
 *
 * @author Joanna Ng
 */
public class TagRetrieveAllRsp {
    
    private List<Tag> tags;

    public TagRetrieveAllRsp() {
    }

    public TagRetrieveAllRsp(List<Tag> tags) {
        this.tags = tags;
    }

    /**
     * @return the tags
     */
    public List<Tag> getTags() {
        return tags;
    }

    /**
     * @param tags the tags to set
     */
    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
    
    
    
}
