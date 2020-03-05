/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Category;
import java.util.List;
import javax.ejb.Local;
import util.exception.CategoryNotFoundException;
import util.exception.CreateNewCategoryException;
import util.exception.InputDataValidationException;
import util.exception.UpdateCategoryException;

/**
 *
 * @author EileenLeong
 */
@Local
public interface CategorySessionBeanLocal {

    public Category retrieveCategoryByCategoryId(Long categoryId) throws CategoryNotFoundException;

    public Category createNewCategory(Category newCategory, Long parentCategoryId) throws InputDataValidationException, CreateNewCategoryException;

    public List<Category> retrieveAllCategories();

    public List<Category> retrieveAllLeafCategories();

    public List<Category> retrieveAllRootCategories();

    public List<Category> retrieveAllCategoriesWithoutProduct();

    public void updateCategory(Category category, Long parentCategoryId) throws InputDataValidationException, CategoryNotFoundException, UpdateCategoryException;
    
}
