/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.CommentSessionBeanLocal;
import entity.Comment;
import entity.Customer;
import entity.ForumPost;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.event.ActionEvent;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import util.exception.CommentNotFoundException;
import util.exception.CreateNewCommentException;
import util.exception.InputDataValidationException;

/**
 *
 * @author zhan
 */
@Named(value = "commentManagedBean")
@RequestScoped
public class CommentManagedBean {

    @EJB(name = "CommentSessionBeanLocal")
    private CommentSessionBeanLocal commentSessionBeanLocal;

    //create new comment
    private Date commentDate;
    private String content;
    private boolean deleted;
    private Customer customer;
    private ForumPost forumPost;
    private List<Comment> childComments;
    private Comment parentComment;
    
    public CommentManagedBean() {
    }
    
    @PostConstruct
    public void PostConstruct() {
        //customer = ApplicationScope.getExternalContext.
    }
    
    public void createNewCommentForForumPost(ActionEvent event) {
        commentDate =  new Date();
        Comment comment = new Comment(commentDate, content);
        try {
            commentSessionBeanLocal.createNewCommentForForumPost(comment, customer.getUserId(), forumPost.getForumId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Comment created successfully! (Id: " + comment.getCommentId()+ ")", null));
        } catch (CreateNewCommentException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unknown Error occured while creating the comment!", null));
        } catch (InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Input validation error: " + ex.getMessage(), null));
        }
    }
    
    public void createNewCommentForComment(ActionEvent event) {
        try {
            commentDate =  new Date();
            Comment comment = new Comment(commentDate, content);
            commentSessionBeanLocal.createNewCommentForForumPost(comment, customer.getUserId(), parentComment.getParentComment().getCommentId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Comment created successfully! (Id: " + comment.getCommentId()+ ")", null));
        } catch (CreateNewCommentException | InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unknown Error occured while creating the comment!", null));
        }
    }
    
    public void updateComment(ActionEvent event) {
        Comment comment = (Comment) event.getComponent().getAttributes().get("commentToUpdate");
        try {
            commentSessionBeanLocal.updateComment(comment);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Comment updated successfully! (Id: " + comment.getCommentId()+ ")", null));
        } catch (InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Input validation error: " + ex.getMessage(), null));
        }
    }
    
    public void deleteComment(ActionEvent event) {
        Comment comment = (Comment) event.getComponent().getAttributes().get("eventToDelete");
        try {
            commentSessionBeanLocal.deleteComment(comment.getCommentId());
        } catch (CommentNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unknown error while deleting the comment!", null));
        }
    }

    public CommentSessionBeanLocal getCommentSessionBeanLocal() {
        return commentSessionBeanLocal;
    }

    public void setCommentSessionBeanLocal(CommentSessionBeanLocal commentSessionBeanLocal) {
        this.commentSessionBeanLocal = commentSessionBeanLocal;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public ForumPost getForumPost() {
        return forumPost;
    }

    public void setForumPost(ForumPost forumPost) {
        this.forumPost = forumPost;
    }

    public List<Comment> getChildComments() {
        return childComments;
    }

    public void setChildComments(List<Comment> childComments) {
        this.childComments = childComments;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }
    
}
