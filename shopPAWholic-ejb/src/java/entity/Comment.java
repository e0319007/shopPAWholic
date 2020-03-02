/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Shi Zhan
 */
@Entity
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    
    private Date date;
    private String content;
    private int thumbsUpCount;
    private boolean deleted;
    
    @ManyToOne(optional = true)
    private Customer customer;
    @ManyToOne(optional = true)
    private ForumPost forumPost;
    @OneToMany
    private List<Comment> comments;

    public Comment() {
        deleted = false;
    }

    public Comment(Long commentId, Date date, String content, int thumbsUpCount, Customer customer, ForumPost forumPost, List<Comment> comments) {
        this();
        this.commentId = commentId;
        this.date = date;
        this.content = content;
        this.thumbsUpCount = thumbsUpCount;
        this.customer = customer;
        this.forumPost = forumPost;
        this.comments = comments;
    }
    
    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (commentId != null ? commentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the commentId fields are not set
        if (!(object instanceof Comment)) {
            return false;
        }
        Comment other = (Comment) object;
        if ((this.commentId == null && other.commentId != null) || (this.commentId != null && !this.commentId.equals(other.commentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Comment[ id=" + commentId + " ]";
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the thumbsUpCount
     */
    public int getThumbsUpCount() {
        return thumbsUpCount;
    }

    /**
     * @param thumbsUpCount the thumbsUpCount to set
     */
    public void setThumbsUpCount(int thumbsUpCount) {
        this.thumbsUpCount = thumbsUpCount;
    }

    /**
     * @return the deleted
     */
    public boolean isDeleted() {
        return deleted;
    }

    /**
     * @param deleted the deleted to set
     */
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * @return the forumPost
     */
    public ForumPost getForumPost() {
        return forumPost;
    }

    /**
     * @param forumPost the forumPost to set
     */
    public void setForumPost(ForumPost forumPost) {
        this.forumPost = forumPost;
    }

    /**
     * @return the comments
     */
    public List<Comment> getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
    
}
