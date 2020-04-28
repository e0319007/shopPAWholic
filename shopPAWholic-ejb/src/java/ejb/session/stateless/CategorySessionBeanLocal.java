package ejb.session.stateless;

import entity.Category;
import java.util.List;
import javax.ejb.Local;
import util.exception.CategoryNotFoundException;
import util.exception.CreateNewCategoryException;
import util.exception.DeleteCategoryException;
import util.exception.InputDataValidationException;
import util.exception.UpdateCategoryException;

@Local
public interface CategorySessionBeanLocal {

    public Category retrieveCategoryByCategoryId(Long categoryId) throws CategoryNotFoundException;

    public Category createNewCategory(Category newCategory) throws InputDataValidationException, CreateNewCategoryException;

    public List<Category> retrieveAllCategories();

    public List<Category> retrieveAllCategoriesWithoutProduct();

    public void updateCategory(Category category) throws InputDataValidationException, CategoryNotFoundException, UpdateCategoryException;
    
    public void deleteCategory(Long categoryId) throws CategoryNotFoundException, DeleteCategoryException;
}
