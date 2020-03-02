/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Comment;
import entity.Customer;
import entity.ForumPost;
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
    
    public ForumPost createNewForumPost(ForumPost post) throws InputDataValidationException, CreateNewForumPostException, ForumTitleExistsException {
        Set<ConstraintViolation<ForumPost>> constraintViolations;
        constraintViolations = validator.validate(post);
        
        if(constraintViolations.isEmpty()) {
            if (retrieveForumPostByTitle(post.getTitle()) == null) {
                try {
                    em.persist(post);
                    em.flush();
                    return post;
                } catch (Exception ex) {
                    throw new CreateNewForumPostException("An unexpected error has occurred: " + ex.getMessage());
                }
            } else {
                throw new ForumTitleExistsException("Forum post title of name \"" + post.getTitle() + "\" exists. Please try another title.");
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }
    
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
    
//    public List<ForumPost> retrieveForumPostsByUser(Customer customer) {
//        Query query = em.createQuery("SELECT fp FROM ForumPost fp WHERE fp.customer.customerId = :inCustomerId");
//        query.setParameter("inCustomerId", customer.getCustomerId);
//        List<ForumPost> posts = query.getResultList();
//        if (posts.isEmpty() || posts == null) {
//            return null;
//        } else {
//            for (ForumPost fp: posts)
//                for (Comment c: fp.getComments())
//                    loopComment(c);
//            return posts;
//        }
//    }
    
    public List<ForumPost> retrieveAllForumPost() {
        Query query = em.createQuery("SELECT fp FROM ForumPost fp ORDER BY fp.title");
        if (query.getResultList() == null) {
            return null;
        } else {
            List<ForumPost> posts = query.getResultList();
            for (ForumPost fp: posts)
                for (Comment c: fp.getComments())
                    loopComment(c);
            return query.getResultList();
        }
    }
    
    public void loopComment(Comment comment) {
        for (Comment c:comment.getComments()) 
            loopComment(c);
    }
    
    private ForumPost retrieveForumPostByTitle(String title) {
        Query query = em.createQuery("SELECT fp FROM ForumPost fp WHERE fp.title = :inTitle");
        query.setParameter("inTitle", title);
        if (query.getSingleResult() != null) {
            return (ForumPost) query.getSingleResult();
        } else {
            return null;
        }
    }
    
    public void deleteForumPost(Long id) throws ForumPostNotFoundException {
        ForumPost post = retrieveForumPostById(id);
        post.setDeleted(true);
    }
    
    public ForumPost retrieveForumPostById(Long id) throws ForumPostNotFoundException {
        ForumPost post = em.find(ForumPost.class, id);
        if (post != null) return post;
        else throw new ForumPostNotFoundException(("Forum Post ID " + id + " does not exist!"));
    }
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<ForumPost>>constraintViolations) {
        String msg = "Input data validation error!:";    
        for(ConstraintViolation constraintViolation:constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        return msg;
    }

}
