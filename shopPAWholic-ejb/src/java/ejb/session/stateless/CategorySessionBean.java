package ejb.session.stateless;

import entity.Category;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
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
            return category;
        } else {
            throw new CategoryNotFoundException("Category Id " + categoryId + " does not exist!");
        }
    }

    @Override
    public Category createNewCategory(Category newCategory) throws InputDataValidationException, CreateNewCategoryException {
        Set<ConstraintViolation<Category>> constraintViolations = validator.validate(newCategory);

        if (constraintViolations.isEmpty()) {
            try {
                em.persist(newCategory);
                em.flush();

                return newCategory;
            } catch (PersistenceException ex) {
                if (ex.getCause() != null
                        && ex.getCause().getCause() != null
                        && ex.getCause().getCause().getClass().getSimpleName().equals("SQLIntegrityConstraintViolationException")) {
                    throw new CreateNewCategoryException("Category with same name already exist");
                } else {
                    throw new CreateNewCategoryException("An unexpected error has occurred: " + ex.getMessage());
                }
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
            category.getListings().size();
        }
        return categories;
    }


    @Override
    public List<Category> retrieveAllCategoriesWithoutProduct() {
        Query query = em.createQuery("SELECT c FROM Category c WHERE c.listings IS EMPTY ORDER BY c.name ASC");
        List<Category> rootCategories = query.getResultList();
        return rootCategories;
    }

    @Override
    public void updateCategory(Category category) throws InputDataValidationException, CategoryNotFoundException, UpdateCategoryException {
        Set<ConstraintViolation<Category>> constraintViolations = validator.validate(category);

        if (constraintViolations.isEmpty()) {
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

            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        }
    }

    @Override
    public void deleteCategory(Long categoryId) throws CategoryNotFoundException, DeleteCategoryException {
        Category categoryToDelete = retrieveCategoryByCategoryId(categoryId);
        System.out.println(categoryToDelete);
        if (!categoryToDelete.getListings().isEmpty()) {
            throw new DeleteCategoryException("Category Id " + categoryId + " is associated with existing listings and cannot be deleted!");
        } else {
            em.remove(categoryToDelete);
        }
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Category>> constraintViolations) {
        String msg = "Input data validation error!:";
        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        return msg;
    }
}
