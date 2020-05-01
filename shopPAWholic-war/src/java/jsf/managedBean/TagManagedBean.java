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
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
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

    private Tag newTag;
    private Long tagId;
    private String name;
    


    private Tag tagToUpdate;
    
    @Inject
    private ViewAllTagsManagedBean viewAllTagsManagedBean;

    public TagManagedBean() {
        newTag = new Tag();
    }

    @PostConstruct
    public void PostConstruct() {
        setTags(tagSessionBeanLocal.retrieveAllTags());
    }

    public void createNewTag(ActionEvent event) {
        try {
            Tag tag = tagSessionBeanLocal.createNewTag(newTag);
            tags.add(tag);

            newTag = new Tag();
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New tag " + tag.getName() + " created successfully", null));
        } catch (InputDataValidationException | CreateNewTagException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new tag: " + ex.getMessage(), null));
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

    public void deleteTag(ActionEvent event) throws DeleteTagException {
        try {
            Tag tagToDelete = (Tag) event.getComponent().getAttributes().get("tagToDelete");
            tagSessionBeanLocal.deleteTag(tagToDelete.getTagId());
            tags.remove(tagToDelete);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Tag deleted successfully.", null));
        } catch (TagNotFoundException | DeleteTagException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occured while deleting tag: " + ex.getMessage(), null));
        } catch (Exception ex){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occured: " + ex.getMessage(),null));
        }
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Tag getTagToUpdate() {
        return tagToUpdate;
    }

    public void setTagToUpdate(Tag tagToUpdate) {
        this.tagToUpdate = tagToUpdate;
    }

    
    public Tag getNewTag() {
        return newTag;
    }

    public void setNewTag(Tag newTag) {
        this.newTag = newTag;
    }

    /**
     * @return the viewAllTagsManagedBean
     */
    public ViewAllTagsManagedBean getViewAllTagsManagedBean() {
        return viewAllTagsManagedBean;
    }

    /**
     * @param viewAllTagsManagedBean the viewAllTagsManagedBean to set
     */
    public void setViewAllTagsManagedBean(ViewAllTagsManagedBean viewAllTagsManagedBean) {
        this.viewAllTagsManagedBean = viewAllTagsManagedBean;
    }

}
