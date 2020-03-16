/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Comment;
import entity.Customer;
import entity.ForumPost;
import java.util.ArrayList;
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
import util.exception.CreateNewForumPostException;
import util.exception.ForumPostNotFoundException;
import util.exception.ForumTitleExistsException;
import util.exception.InputDataValidationException;


/**
 *
 * @author Shi Zhan
 */
@Stateless
public class ForumPostSessionBean implements ForumPostSessionBeanLocal {

    @PersistenceContext(unitName = "shopPAWholic-ejbPU")
    private EntityManager em;
    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public ForumPostSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = (Validator) validatorFactory.getValidator();
    }
    
    @Override
    public ForumPost createNewForumPost(ForumPost post, long customerId) throws InputDataValidationException, CreateNewForumPostException, ForumTitleExistsException {
        Set<ConstraintViolation<ForumPost>> constraintViolations;
        constraintViolations = validator.validate(post);
        
        if(constraintViolations.isEmpty()) {
            if (retrieveForumPostByTitle(post.getTitle()) == null) {
                try {
                    
                    Customer customer = em.find(Customer.class, customerId);
                    customer.getForumPosts().add(post);
                    post.setCustomer(customer);
                    
                    em.persist(post);
                    em.flush();
                    return post;
                } catch (Exception ex) {
                    throw new CreateNewForumPostException("An unexpected error has occurred: " + ex.getMessage());
                }
            } else {
                throw new ForumTitleExistsException("Forum post title \"" + post.getTitle() + "\" exists already. Please try another title.");
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }
    
    @Override
    public void updateForumPost(ForumPost post) throws InputDataValidationException, ForumTitleExistsException {
        Set<ConstraintViolation<ForumPost>>  constraintViolations = validator.validate(post);
        if (constraintViolations.isEmpty()) {
            if (retrieveForumPostByTitle(post.getTitle()) == null) {
                em.merge(post);
            } else {
                throw new ForumTitleExistsException("Forum post title of name \"" + post.getTitle() + "\" exists. Please try another title.");
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }
    
    @Override
    public List<ForumPost> retrieveForumPostsByUser(Long customerId) {
        Query query = em.createQuery("SELECT fp FROM ForumPost fp WHERE fp.customer.userId = :inCustomerId");
        query.setParameter("inCustomerId", customerId);
        List<ForumPost> posts = query.getResultList();
        if (posts.isEmpty() || posts == null) {
            return new ArrayList<>();
        } else {
            for (ForumPost fp: posts)
                for (Comment c: fp.getComments())
                    loopComment(c);
            return posts;
        }
    }
    
    @Override
    public List<ForumPost> retrieveAllForumPost() {
        Query query = em.createQuery("SELECT fp FROM ForumPost fp ORDER BY fp.title");
        if (query.getResultList() == null) {
            return new ArrayList<>();
        } else {
            List<ForumPost> posts = query.getResultList();
            for (ForumPost fp: posts)
                for (Comment c: fp.getComments())
                    loopComment(c);
            return query.getResultList();
        }
    }
    
    private void loopComment(Comment comment) {
        for (Comment c:comment.getChildComments()) 
            loopComment(c);
    }
    
    @Override
    public ForumPost retrieveForumPostByTitle(String title) {
        Query query = em.createQuery("SELECT fp FROM ForumPost fp WHERE fp.title = :inTitle");
        query.setParameter("inTitle", title);
        if (query.getSingleResult() != null) {
            ForumPost post = (ForumPost) query.getSingleResult();
            loopComment((Comment) post.getComments());
            return post;
        } else {
            return null;
        }
    }
    
    @Override
    public List<ForumPost> filterForumPostByTitle(String searchString) {
        Query query = em.createQuery("SELECT fp FROM ForumPost fp WHERE fp.title LIKE :inSearchString ORDER BY fp.title ");
        query.setParameter("inSearchString", "%" + searchString + "%");
        List<ForumPost> posts = query.getResultList();
        for (ForumPost fp: posts)
            loopComment((Comment) fp.getComments());
        return posts;
    }
    
    @Override
    public List<ForumPost> filterForumPostByContent(String searchString) {
        Query query = em.createQuery("SELECT fp FROM ForumPost fp WHERE fp.content LIKE :inSearchString ORDER BY fp.title ");
        query.setParameter("inSearchString", "%" + searchString + "%");
        List<ForumPost> posts = query.getResultList();
        for (ForumPost fp: posts)
            loopComment((Comment) fp.getComments());
        return posts;
    }
    
    @Override
    public void deleteForumPost(Long id) throws ForumPostNotFoundException {
        ForumPost post = retrieveForumPostById(id);
        post.setDeleted(true);
    }
    
    @Override
    public ForumPost retrieveForumPostById(Long id) throws ForumPostNotFoundException {
        ForumPost post = em.find(ForumPost.class, id);
        if (post != null) return post;
        else throw new ForumPostNotFoundException("Forum Post ID " + id + " does not exist!");
    }
    
    public void thumbsUp(Long forumPostId) throws ForumPostNotFoundException  {
        ForumPost post = retrieveForumPostById(forumPostId);
        post.setThumbsUpCount(post.getThumbsUpCount() + 1);
    }
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<ForumPost>>constraintViolations) {
        String msg = "Input data validation error!:";    
        for(ConstraintViolation constraintViolation:constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        return msg;
    }

}
