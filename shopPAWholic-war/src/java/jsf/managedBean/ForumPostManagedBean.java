/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.ForumPostSessionBeanLocal;
import entity.ForumPost;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Shi Zhan
 */
@Named(value = "forumPostManagedBean")
@ViewScoped
public class ForumPostManagedBean {

    @EJB(name = "ForumPostSessionBeanLocal")
    private ForumPostSessionBeanLocal forumPostSessionBeanLocal;

    /**
     * 
     */
    List<ForumPost> forumPosts;
    
    public ForumPostManagedBean() {
    }
    
}
