/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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
    
    @ManyToOne(optional = true)
    private Customer customer;

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
    
}
