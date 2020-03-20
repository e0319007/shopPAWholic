/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.TagSessionBeanLocal;
import entity.Tag;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author EileenLeong
 */
@Named(value = "viewAllTagsManagedBean")
@RequestScoped
public class ViewAllTagsManagedBean {

    @EJB(name = "TagSessionBeanLocal")
    private TagSessionBeanLocal tagSessionBeanLocal;
    
    private List<Tag> tags;

    /**
     * Creates a new instance of ViewAllTagsManagedBean
     */
    public ViewAllTagsManagedBean() {
    }
    
    @PostConstruct
    public void postConstruct()
    {
        tags = tagSessionBeanLocal.retrieveAllTags();
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
    
}
