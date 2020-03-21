/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.CategorySessionBeanLocal;
import ejb.session.stateless.ListingSessionBeanLocal;
import entity.Category;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import util.exception.CategoryNotFoundException;
import util.exception.CreateNewCategoryException;
import util.exception.DeleteCategoryException;
import util.exception.InputDataValidationException;
import util.exception.UpdateCategoryException;

/**
 *
 * @author EileenLeong
 */
@Named(value = "categoryManagedBean")
@ViewScoped
public class CategoryManagedBean implements Serializable {

    @EJB(name = "ListingSessionBeanLocal")
    private ListingSessionBeanLocal listingSessionBeanLocal;

    @EJB(name = "CategorySessionBeanLocal")
    private CategorySessionBeanLocal categorySessionBeanLocal;
    
    private List<Category> categories;
     
    private String name;
    private String description; 
    
    private Long parentCategoryId;
    
    private Category categoryToUpdate;

    /**
     * Creates a new instance of CategoryManagedBean
     */
    public CategoryManagedBean() {
    }
    
    @PostConstruct
    public void PostConstruct() {
        setCategories(categorySessionBeanLocal.retrieveAllCategories());
    }
    
    public void createNewCategory(ActionEvent event) {
        
        Category category = new Category(name, description);
        try {
            
            categorySessionBeanLocal.createNewCategory(category, getParentCategoryId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New category created successfully! (Id: " + category.getCategoryId()+ ")", null));
        } catch (CreateNewCategoryException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Category with name " + category.getName()+ "exists: " + ex.getMessage(), null));
        } catch (InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating category: " + ex.getMessage(), null));
        } 
    }
    
    public void updateCategory(ActionEvent event) throws UpdateCategoryException {
        try {
            categorySessionBeanLocal.updateCategory(categoryToUpdate, getParentCategoryId());
        } catch (InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while editing category: " + ex.getMessage(), null));
        } catch (CategoryNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Category with title " + categoryToUpdate.getName()+ "exists: " + ex.getMessage(), null));
        }
    }
    
     public void deleteCategory(ActionEvent event ) throws DeleteCategoryException{
        try {
            Category categoryToDelete = (Category) event.getComponent().getAttributes().get("CategoryToDelete");
            categorySessionBeanLocal.deleteCategory(categoryToDelete.getCategoryId());
        } catch (CategoryNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Category with given ID is not found", null));
        }
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

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the parentCategoryId
     */
    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    /**
     * @param parentCategoryId the parentCategoryId to set
     */
    public void setParentCategoryId(Long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }
}
