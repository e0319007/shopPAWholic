/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.TagSessionBeanLocal;
import entity.Tag;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import util.exception.CreateNewTagException;
import util.exception.DeleteTagException;
import util.exception.InputDataValidationException;
import util.exception.TagNotFoundException;
import util.exception.UpdateTagException;

/**
 *
 * @author EileenLeong
 */
@Named(value = "tagManagedBean")
@ViewScoped
public class TagManagedBean implements Serializable {

    @EJB(name = "TagSessionBeanLocal")
    private TagSessionBeanLocal tagSessionBeanLocal;
    
    private List<Tag> tags;
    
    private Long tagId;
    private String name; 

    /**
     * Creates a new instance of TagManagedBean
     */
    public TagManagedBean() {
    }
    
    @PostConstruct
    public void PostConstruct() {
        setTags(tagSessionBeanLocal.retrieveAllTags());
    }
    
    public void createNewTag(ActionEvent event) {
        try {
            Tag tagToCreate = new Tag(getName());
            tagSessionBeanLocal.createNewTag(tagToCreate);
        } catch (CreateNewTagException ex) {
            Logger.getLogger(TagManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InputDataValidationException ex) {
            Logger.getLogger(TagManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public void updateTag(ActionEvent event) throws UpdateTagException {
        try {
            Tag tagToUpdate = (Tag) event.getComponent().getAttributes().get("TagToUpdate");
            tagSessionBeanLocal.updateTag(tagToUpdate);
        } catch (InputDataValidationException ex) {
            Logger.getLogger(TagManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TagNotFoundException ex) {
            Logger.getLogger(EventManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteTag(ActionEvent event ) throws DeleteTagException{
        try {
            Tag tagToDelete = (Tag) event.getComponent().getAttributes().get("TagToDelete");
            tagSessionBeanLocal.deleteTag(tagToDelete.getTagId());
        } catch (TagNotFoundException ex) {
            Logger.getLogger(TagManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    /**
     * @return the tagId
     */
    public Long getTagId() {
        return tagId;
    }

    /**
     * @param tagId the tagId to set
     */
    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
}
