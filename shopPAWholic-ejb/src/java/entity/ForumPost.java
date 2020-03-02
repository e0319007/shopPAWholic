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
public class ForumPost implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long forumId;
    
    private String content;
    private Date date;
    private int thumbsUpCount;
    private String title;
    private boolean deleted;
    
    @ManyToOne(optional = true)
    private Customer customer;
    @OneToMany
    private List<Comment> comments;

    public ForumPost() {
        deleted = false;
    }

    public ForumPost(String content, Date date, int thumbsUpCount, String title, Customer customer, List<Comment> comments) {
        this();
        this.content = content;
        this.date = date;
        this.thumbsUpCount = thumbsUpCount;
        this.title = title;
        this.customer = customer;
        this.comments = comments;
    }
    
    public Long getForumId() {
        return forumId;
    }

    public void setForumId(Long forumId) {
        this.forumId = forumId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (forumId != null ? forumId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the forumId fields are not set
        if (!(object instanceof ForumPost)) {
            return false;
        }
        ForumPost other = (ForumPost) object;
        if ((this.forumId == null && other.forumId != null) || (this.forumId != null && !this.forumId.equals(other.forumId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ForumPost[ id=" + forumId + " ]";
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    
}
