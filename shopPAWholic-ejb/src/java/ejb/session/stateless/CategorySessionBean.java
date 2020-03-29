/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Category;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.CategoryNotFoundException;
import util.exception.CreateNewCategoryException;
import util.exception.DeleteCategoryException;
import util.exception.InputDataValidationException;
import util.exception.UpdateCategoryException;

/**
 *
 * @author EileenLeong
 */
@Stateless
public class CategorySessionBean implements CategorySessionBeanLocal {

    @PersistenceContext(unitName = "shopPAWholic-ejbPU")
    private EntityManager em;
    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public CategorySessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = (Validator) validatorFactory.getValidator();
    }
    
    @Override
    public Category retrieveCategoryByCategoryId(Long categoryId) throws CategoryNotFoundException {
        Category category = em.find(Category.class, categoryId);
        if (category != null) {
            category.getListings().size();
            category.getSubCategories().size();
            return category;
        } else {
            throw new CategoryNotFoundException("Category Id " + categoryId + " does not exist!");
        }
    }
    
    @Override
    public Category createNewCategory(Category newCategory, Long parentCategoryId) throws InputDataValidationException, CreateNewCategoryException {
        Set<ConstraintViolation<Category>>constraintViolations = validator.validate(newCategory);
        
        if (constraintViolations.isEmpty()) {
            try {
                if (parentCategoryId != null) {
                    Category parentCategory = retrieveCategoryByCategoryId(parentCategoryId);
                    if (!parentCategory.getListings().isEmpty()) {
                        throw new CreateNewCategoryException("Parent category cannot be associated with any product!");
                    }
                    newCategory.setParentCategory(parentCategory);
                }
                em.persist(newCategory);
                em.flush();
                System.out.println("I am in the database now");
                
                return newCategory;
            } catch (Exception ex) {
                    throw new CreateNewCategoryException("An  error has occurred: " + ex.getMessage());
                }
            } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }
    
    @Override
    public List<Category> retrieveAllCategories() {
        Query query = em.createQuery("SELECT c FROM Category c ORDER BY c.name ASC");
        List<Category> categories = query.getResultList();
        
        for (Category category : categories) {
            category.getParentCategory();
            category.getSubCategories().size();
            category.getListings().size();
        }
        return categories;
    }
    
    @Override
    public List<Category> retrieveAllRootCategories() {
        Query query = em.createQuery("SELECT c FROM Category c WHERE c.parentCategory IS NULL ORDER BY c.name ASC");
        List<Category> rootCategories = query.getResultList();
        
        for (Category rootCategory : rootCategories) {
            lazilyLoadSubCategories(rootCategory);
            rootCategory.getListings().size();
        }
        return rootCategories; 
    }
    
    private void lazilyLoadSubCategories(Category category){
        for(Category c : category.getSubCategories())
        {
            lazilyLoadSubCategories(c);
        }
    }
    
    @Override
    public List<Category> retrieveAllLeafCategories() {
        Query query = em.createQuery("SELECT c FROM Category c WHERE c.subCategories IS EMPTY ORDER BY c.name ASC");
        List<Category> leafCategories = query.getResultList();
        
        for (Category leafCategory : leafCategories) {
            leafCategory.getListings().size();
        }
        return leafCategories;
    }
    
    @Override
    public List<Category> retrieveAllCategoriesWithoutProduct() {
        Query query = em.createQuery("SELECT c FROM Category c WHERE c.listings IS EMPTY ORDER BY c.name ASC");
        List<Category> rootCategories = query.getResultList();
        
        for (Category rootCategory : rootCategories) {
            rootCategory.getParentCategory();
        }
        return rootCategories;
    }
    
    @Override
    public void updateCategory(Category category, Long parentCategoryId) throws InputDataValidationException, CategoryNotFoundException, UpdateCategoryException {
        Set<ConstraintViolation<Category>>constraintViolations = validator.validate(category);
        
        if(constraintViolations.isEmpty()) {
            if (category.getCategoryId() != null) {
                Category categoryToUpdate = retrieveCategoryByCategoryId(category.getCategoryId()); 
                
                Query query = em.createQuery("SELECT c FROM Category c WHERE c.name = :inName AND c.categoryId <> :inCategoryId");
                query.setParameter("inName", category.getName());
                query.setParameter("inCategoryId", category.getCategoryId());
                
                if (!query.getResultList().isEmpty()) {
                    throw new UpdateCategoryException("Name of category to be updated is duplicated!"); 
                }
                categoryToUpdate.setName(category.getName());
                categoryToUpdate.setDescription(category.getDescription());
                
                if (parentCategoryId != null) {
                    if (categoryToUpdate.getCategoryId().equals(parentCategoryId)) {
                        throw new UpdateCategoryException("Category cannot be its own parent");
                    } else if (categoryToUpdate.getParentCategory() == null || (!categoryToUpdate.getParentCategory().getCategoryId().equals(parentCategoryId))) {
                        Category parentCategoryToUpdate = retrieveCategoryByCategoryId(parentCategoryId);
                        
                        if (!parentCategoryToUpdate.getListings().isEmpty()) {
                            throw new UpdateCategoryException("Parent category cannot have any listing associated with it!");
                            
                        }
                        categoryToUpdate.setParentCategory(parentCategoryToUpdate);
                                
                    }
                } else {
                    throw new CategoryNotFoundException("Category Id not provided for category to be updated!");
                }
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        }
    }
    
    @Override
    public void deleteCategory(Long categoryId) throws CategoryNotFoundException, DeleteCategoryException {
        Category categoryToDelete = retrieveCategoryByCategoryId(categoryId);
        
        if (!categoryToDelete.getSubCategories().isEmpty()) {
            throw new DeleteCategoryException("Category Id " + categoryId + " is associated with existing sub-categories and cannot be deleted!");
            
        } else if (!categoryToDelete.getListings().isEmpty()) {
            throw new DeleteCategoryException("Category Id " + categoryId + " is associated with existing listings and cannot be deleted!");
        } else {
            categoryToDelete.setParentCategory(null);
            em.remove(categoryToDelete);
        }
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Category>>constraintViolations) {
        String msg = "Input data validation error!:";    
        for(ConstraintViolation constraintViolation:constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        return msg;
    }
}
