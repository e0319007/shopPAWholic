/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.CategorySessionBeanLocal;
import entity.Category;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author EileenLeong
 */
@Named(value = "viewAllCategoriesManagedBean")
@ViewScoped
public class ViewAllCategoriesManagedBean implements Serializable {

    @EJB(name = "CategorySessionBeanLocal")
    private CategorySessionBeanLocal categorySessionBeanLocal;
    
     private List<Category> categories;

    /**
     * Creates a new instance of ViewAllCategoriesManagedBean
     */
    public ViewAllCategoriesManagedBean() {
    }
    
    @PostConstruct
    public void postConstruct()
    {
        setCategories(categorySessionBeanLocal.retrieveAllCategories());
    }

    /**
     * @return the categories
     */
    public List<Category> getCategories() {
        return categories;
    }

    /**
     * @param categories the categories to set
     */
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
    
    
}
