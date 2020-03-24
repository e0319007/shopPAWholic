/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;
import ejb.session.stateless.AdvertisementSessionBeanLocal;
import ejb.session.stateless.BilingDetailSessionBeanLocal;
import ejb.session.stateless.CartSessionBeanLocal;
import ejb.session.stateless.CategorySessionBeanLocal;
import ejb.session.stateless.CommentSessionBeanLocal;
import ejb.session.stateless.EventSessionBeanLocal;
import ejb.session.stateless.ForumPostSessionBeanLocal;
import ejb.session.stateless.ListingSessionBeanLocal;
import ejb.session.stateless.OrderSessionBeanLocal;
import ejb.session.stateless.ReviewSessionBeanLocal;
import ejb.session.stateless.TagSessionBeanLocal;
import ejb.session.stateless.UserSessionBeanLocal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Shi Zhan
 */
public class SessionBeanLookup {
    
    private final String ejbModuleJndiPath;
    
    
    public SessionBeanLookup() {
        ejbModuleJndiPath = "java:global/ShopPAWholic/ShopPAWholic-ejb";
    }
    
    public Object initialContextLookup(String path) throws NamingException {
        javax.naming.Context c = new InitialContext();
        return (Object) c.lookup(ejbModuleJndiPath + path);
    }
    
    public AdvertisementSessionBeanLocal lookupAdvertisementSessionBeanLocal() {
        try {
            String path = "AdvertisementSessionBean!ejb.session.stateless.AdvertisementSessionBeanLocal";
            return (AdvertisementSessionBeanLocal) initialContextLookup(path);
        } catch (NamingException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ex);
            throw new RuntimeException(ex);
        }
    }
    
    public BilingDetailSessionBeanLocal lookupBilingDetailSessionBeanLocal() {
        try {
            String path = "BilingDetailSessionBean!ejb.session.stateless.BilingDetailSessionBeanLocal";
            return (BilingDetailSessionBeanLocal) initialContextLookup(path);
        } catch (NamingException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ex);
            throw new RuntimeException(ex);
        }
    }
    
    public CartSessionBeanLocal lookupCartSessionBeanLocal() {
        try {
            String path = "CartSessionBean!ejb.session.stateless.CartSessionBeanLocal";
            return (CartSessionBeanLocal) initialContextLookup(path);
        } catch (NamingException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ex);
            throw new RuntimeException(ex);
        }
    }
    
    public CategorySessionBeanLocal lookupCategorySessionBeanLocal() {
        try {
            String path = "CategorySessionBean!ejb.session.stateless.CategorySessionBeanLocal";
            return (CategorySessionBeanLocal) initialContextLookup(path);
        } catch (NamingException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ex);
            throw new RuntimeException(ex);
        }
    }
    
    public CommentSessionBeanLocal lookupCommentSessionBeanLocal() {
        try {
            String path = "CommentSessionBean!ejb.session.stateless.CommentSessionBeanLocal";
            return (CommentSessionBeanLocal) initialContextLookup(path);
        } catch (NamingException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ex);
            throw new RuntimeException(ex);
        }
    }
    
    public EventSessionBeanLocal lookupEventSessionBeanLocal() {
        try {
            String path = "EventSessionBean!ejb.session.stateless.EventSessionBeanLocal";
            return (EventSessionBeanLocal) initialContextLookup(path);
        } catch (NamingException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ex);
            throw new RuntimeException(ex);
        }
    }
    
    public ForumPostSessionBeanLocal ForumPostSessionBeanLocal() {
        try {
            String path = "ForumPostSessionBean!ejb.session.stateless.ForumPostSessionBeanLocal";
            return (ForumPostSessionBeanLocal) initialContextLookup(path);
        } catch (NamingException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ex);
            throw new RuntimeException(ex);
        }
    }
    
    public ListingSessionBeanLocal lookupListingSessionBeanLocal() {
        try {
            String path = "ListingSessionBean!ejb.session.stateless.ListingSessionBeanLocal";
            return (ListingSessionBeanLocal) initialContextLookup(path);
        } catch (NamingException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ex);
            throw new RuntimeException(ex);
        }
    }
    
    public OrderSessionBeanLocal lookupOrderSessionBeanLocal() {
        try {
            String path = "OrderSessionBean!ejb.session.stateless.OrderSessionBeanLocal";
            return (OrderSessionBeanLocal) initialContextLookup(path);
        } catch (NamingException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ex);
            throw new RuntimeException(ex);
        }
    }
    
    public ReviewSessionBeanLocal lookupReviewSessionBeanLocal() {
        try {
            String path = "ReviewSessionBean!ejb.session.stateless.ReviewSessionBeanLocal";
            return (ReviewSessionBeanLocal) initialContextLookup(path);
        } catch (NamingException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ex);
            throw new RuntimeException(ex);
        }
    }
    
    public TagSessionBeanLocal lookupTagSessionBeanLocal() {
        try {
            String path = "TagSessionBean!ejb.session.stateless.TagSessionBeanLocal";
            return (TagSessionBeanLocal) initialContextLookup(path);
        } catch (NamingException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ex);
            throw new RuntimeException(ex);
        }
    }
    
    public UserSessionBeanLocal lookupUserSessionBeanLocal() {
        try {
            String path = "UserSessionBean!ejb.session.stateless.UserSessionBeanLocal";
            return (UserSessionBeanLocal) initialContextLookup(path);
        } catch (NamingException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ex);
            throw new RuntimeException(ex);
        }
    }
}
