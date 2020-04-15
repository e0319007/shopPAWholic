package jsf.managedBean;

import ejb.session.stateless.CategorySessionBeanLocal;
import ejb.session.stateless.ListingSessionBeanLocal;
import entity.Category;
import java.io.Serializable;
import java.util.List;
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

@Named(value = "categoryManagedBean")
@ViewScoped
public class CategoryManagedBean implements Serializable {

    @EJB(name = "ListingSessionBeanLocal")
    private ListingSessionBeanLocal listingSessionBeanLocal;

    @EJB(name = "CategorySessionBeanLocal")
    private CategorySessionBeanLocal categorySessionBeanLocal;

    private List<Category> categories;
    private Category newCategory;

    private Category parentCategory;
    private List<Category> parentCategories;

    private String name;
    private String description;

    private Long parentCategoryId;

    private Category categoryToUpdate;

    public CategoryManagedBean() {
        newCategory = new Category();
    }

    @PostConstruct
    public void PostConstruct() {
        setCategories(categorySessionBeanLocal.retrieveAllCategories());
        setParentCategories(categorySessionBeanLocal.retrieveAllRootCategories());
    }

    /*public void createNewCategory(ActionEvent event) {
        
        Category category = new Category(name, description);
        try {
            
            categorySessionBeanLocal.createNewCategory(category, getParentCategoryId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New category created successfully! (Id: " + category.getCategoryId()+ ")", null));
        } catch (CreateNewCategoryException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Category with name " + category.getName()+ "exists: " + ex.getMessage(), null));
        } catch (InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating category: " + ex.getMessage(), null));
        } 
    }*/
    public void createNewCategory(ActionEvent event) {
        try {
            System.out.println("################ i am in category;");
            Category category = categorySessionBeanLocal.createNewCategory(newCategory, parentCategoryId);
            categories.add(newCategory);

            newCategory = new Category();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New category " + category.getName() + " created successfully", null));
        } catch (InputDataValidationException | CreateNewCategoryException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new category: " + ex.getMessage(), null));
        }
    }

    public void updateCategory(ActionEvent event) throws UpdateCategoryException {
        try {
            categorySessionBeanLocal.updateCategory(getCategoryToUpdate(), getParentCategoryId());
        } catch (InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while editing category: " + ex.getMessage(), null));
        } catch (CategoryNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Category with title " + getCategoryToUpdate().getName() + "exists: " + ex.getMessage(), null));
        }
    }

    public void deleteCategory(ActionEvent event) throws DeleteCategoryException {
        try {
            Category categoryToDelete = (Category) event.getComponent().getAttributes().get("categoryToDelete");
            categorySessionBeanLocal.deleteCategory(categoryToDelete.getCategoryId());
            categories.remove(categoryToDelete);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Category deleted successfully.", null));
        } catch (CategoryNotFoundException | DeleteCategoryException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occured while deleting product: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occured: " + ex.getMessage(), null));
        }
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public Category getNewCategory() {
        return newCategory;
    }

    public void setNewCategory(Category newCategory) {
        this.newCategory = newCategory;
    }

    public Category getCategoryToUpdate() {
        return categoryToUpdate;
    }

    public List<Category> getParentCategories() {
        return parentCategories;
    }

    public void setCategoryToUpdate(Category categoryToUpdate) {
        this.categoryToUpdate = categoryToUpdate;
    }

    public void setParentCategories(List<Category> parentCategories) {
        this.parentCategories = parentCategories;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }
}
