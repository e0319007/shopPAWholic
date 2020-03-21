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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
    
     private Tag tagToUpdate;
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
        Tag tag = new Tag(name);
        try {
            tagSessionBeanLocal.createNewTag(tag);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New tag created successfully! (Id: " + tag.getTagId()+ ")", null));
        } catch (CreateNewTagException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Tag with name " + tag.getName()+ "exists: " + ex.getMessage(), null));
        } catch (InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating tag: " + ex.getMessage(), null));
        } 
    }
    
    public void updateTag(ActionEvent event) throws UpdateTagException {
        try {
            
            tagSessionBeanLocal.updateTag(tagToUpdate);
        } catch (InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while editing tag: " + ex.getMessage(), null));
        } catch (TagNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Tag with name " + getTagToUpdate().getName() + "exists: " + ex.getMessage(), null));
        }
    }
    
    public void deleteTag(ActionEvent event ) throws DeleteTagException{
        Tag tagToDelete = (Tag) event.getComponent().getAttributes().get("TagToDelete");
        try {
            tagSessionBeanLocal.deleteTag(tagToDelete.getTagId());
        } catch (TagNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Tag with given ID is not found", null));
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

    /**
     * @return the tagToUpdate
     */
    public Tag getTagToUpdate() {
        return tagToUpdate;
    }

    /**
     * @param tagToUpdate the tagToUpdate to set
     */
    public void setTagToUpdate(Tag tagToUpdate) {
        this.tagToUpdate = tagToUpdate;
    }
    
}
