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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.annotation.PostConstruct;
import java.io.Serializable;

/**
 *
 * @author Shi Zhan
 */
@Named(value = "forumPostManagedBean")
@ViewScoped
public class ForumPostManagedBean implements Serializable{

    @EJB(name = "ForumPostSessionBeanLocal")
    private ForumPostSessionBeanLocal forumPostSessionBeanLocal;

    /**
     * Add new forum post
     */
    @Size(min = 10, message = "Forum post must be more than 10 characters")
    private String content;
    @NotNull
    private Date date;
    @NotNull
    private int thumbsUpCount;
    @NotNull
    @Size(min = 5, message = "Title must be more than 5 characters")
    private String title;
    @NotNull
    private boolean deleted;
    @ManyToOne(optional = true)
    private Customer customer;
    @OneToMany
    private List<Comment> comments;
    
    List<ForumPost> forumPosts;
    
    public ForumPostManagedBean() {
    }
    
    @PostConstruct
    public void PostConstruct() {
        
    }
    
}
