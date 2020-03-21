/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.ForumPostSessionBeanLocal;
import entity.Comment;
import entity.Customer;
import entity.ForumPost;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import util.exception.CreateNewForumPostException;
import util.exception.ForumPostNotFoundException;
import util.exception.ForumTitleExistsException;
import util.exception.InputDataValidationException;

/**
 *
 * @author Shi Zhan
 */
@Named(value = "forumPostManagedBean")
@ViewScoped
public class ForumPostManagedBean implements Serializable{

    @EJB(name = "ForumPostSessionBeanLocal")
    private ForumPostSessionBeanLocal forumPostSessionBeanLocal;

    //Add new forum post
    private String content;
    private Date date;
    private int thumbsUpCount;
    private String title;
    private boolean deleted;
    private Customer customer;
    
    //Retrieve all forum post
    private List<ForumPost> forumPosts;
    
    //update forum post
    private ForumPost forumPostToUpdate;
    
    public ForumPostManagedBean() {
    }
    
    @PostConstruct
    public void PostConstruct() {
       forumPosts = forumPostSessionBeanLocal.retrieveAllForumPost();
    }
    
    public void createNewForumPost(ActionEvent event) {
        ForumPost forumPost = new ForumPost(content, date, title);
        date =  new Date();
        try {
            forumPostSessionBeanLocal.createNewForumPost(forumPost, customer.getUserId());
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New forum post created successfully! (Id: " + forumPost.getForumId()+ ")", null));
        } catch (ForumTitleExistsException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Forum post with title " + forumPost.getTitle() + "exists: " + ex.getMessage(), null));
        } catch(InputDataValidationException | CreateNewForumPostException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating forum post: " + ex.getMessage(), null));
        }
    }
    
    public void updateForumPost(ActionEvent event) {
        try {
            forumPostSessionBeanLocal.updateForumPost(forumPostToUpdate);
        } catch (InputDataValidationException ex) {
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while editing forum post: " + ex.getMessage(), null));
        } catch (ForumTitleExistsException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Forum post with title " + forumPostToUpdate.getTitle() + "exists: " + ex.getMessage(), null));
        }
    }
    
    public void deleteForumPost(ActionEvent event) {
        ForumPost forumPostToDelete = (ForumPost) event.getComponent().getAttributes().get("forumPostToDelete");
        try {
            forumPostSessionBeanLocal.deleteForumPost(forumPostToDelete.getForumId());
        } catch (ForumPostNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Forum post with given ID is not found", null));
        }
    }
    
    public void thumbsUp(ActionEvent event) {
        ForumPost forumPostToLike = (ForumPost) event.getComponent().getAttributes().get("forumPostToLike");
        try {
            forumPostSessionBeanLocal.thumbsUp(forumPostToLike.getForumId());
        } catch (ForumPostNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Forum post with given ID is not found", null));
        }
    }

    public ForumPostSessionBeanLocal getForumPostSessionBeanLocal() {
        return forumPostSessionBeanLocal;
    }

    public void setForumPostSessionBeanLocal(ForumPostSessionBeanLocal forumPostSessionBeanLocal) {
        this.forumPostSessionBeanLocal = forumPostSessionBeanLocal;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getThumbsUpCount() {
        return thumbsUpCount;
    }

    public void setThumbsUpCount(int thumbsUpCount) {
        this.thumbsUpCount = thumbsUpCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<ForumPost> getForumPosts() {
        return forumPosts;
    }

    public void setForumPosts(List<ForumPost> forumPosts) {
        this.forumPosts = forumPosts;
    }

    public ForumPost getForumPostToUpdate() {
        return forumPostToUpdate;
    }

    public void setForumPostToUpdate(ForumPost forumPostToUpdate) {
        this.forumPostToUpdate = forumPostToUpdate;
    }
}
