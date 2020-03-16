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
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.CommentNotFoundException;
import util.exception.CreateNewCommentException;
import util.exception.ForumPostNotFoundException;
import util.exception.InputDataValidationException;

/**
 *
 * @author Shi Zhan
 */
@Stateless
public class CommentSessionBean implements CommentSessionBeanLocal {

    @EJB
    private ForumPostSessionBeanLocal forumPostSessionBean;

    @PersistenceContext(unitName = "shopPAWholic-ejbPU")
    private EntityManager em;
    private final ValidatorFactory validatorFactory;
    private final Validator validator;
    
    
    public CommentSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = (Validator) validatorFactory.getValidator();
    }

    @Override
    public Comment createNewCommentForForumPost(Comment comment, Long customerId, Long forumPostId) throws CreateNewCommentException, InputDataValidationException {
        Set<ConstraintViolation<Comment>> constraintViolations;
        constraintViolations = validator.validate(comment);
        
        Customer customer = em.find(Customer.class, customerId);
        customer.getComments().add(comment);
        comment.setCustomer(customer);
        
        ForumPost forumPost = em.find(ForumPost.class, forumPostId);
        forumPost.getComments().add(comment);
        comment.setForumPost(forumPost);
        
        if (constraintViolations.isEmpty()) {
            try {
               em.persist(comment);
               em.flush();
               
               return comment;
            } catch (Exception ex) {
                throw new CreateNewCommentException("An unexpected error has occurred: " + ex.getMessage());
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }
    
    @Override
    public Comment createNewChildComment(Comment comment, Long customerId, Long parentComentId) throws CreateNewCommentException, InputDataValidationException {
        Set<ConstraintViolation<Comment>> constraintViolations;
        constraintViolations = validator.validate(comment);
        
        if (constraintViolations.isEmpty()) {
            try {
                        
                Customer customer = em.find(Customer.class, customerId);
                customer.getComments().add(comment);
                comment.setCustomer(customer);

                Comment parentComment = em.find(Comment.class, parentComentId);
                parentComment.getChildComments().add(comment);
                comment.setParentComment(comment);
                
                em.persist(comment);
                em.flush();

                return comment;
            } catch (Exception ex) {
                throw new CreateNewCommentException("An unexpected error has occurred: " + ex.getMessage());
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }
    
    @Override
    public void updateComment(Comment comment) throws InputDataValidationException {
        Set<ConstraintViolation<Comment>>constraintViolations = validator.validate(comment);
        if(constraintViolations.isEmpty()) {
            if(comment.getCommentId()!= null) {
                em.merge(comment);
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }
    
    public void thumbsUp(Long commentId) throws CommentNotFoundException {
        Comment c = retrieveCommentById(commentId);
        c.setThumbsUpCount(c.getThumbsUpCount()+1);
    }
    
    @Override
    public void deleteComment(Long commentId) throws CommentNotFoundException{
        Comment comment = retrieveCommentById(commentId);
        comment.setDeleted(true);
    }
    
    @Override
    public List<Comment> retrieveCommentsByForumPost(Long id) throws ForumPostNotFoundException {
        ForumPost forumPost = forumPostSessionBean.retrieveForumPostById(id);
        List<Comment> comments = forumPost.getComments();
        for (Comment c:comments) loopComment(c);
        return comments;
    }
    
    private void loopComment(Comment comment) {
        for (Comment c:comment.getChildComments()) 
            loopComment(c);
    }
    
    @Override
    public Comment retrieveCommentById(Long id) throws CommentNotFoundException{
        Comment comment = em.find(Comment.class, id);
        if (comment != null) return comment;
        else throw new CommentNotFoundException("Coment ID " + id + " does not exist!");
    }
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Comment>>constraintViolations) {
        String msg = "Input data validation error!:";    
        for(ConstraintViolation constraintViolation:constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        return msg;
    }
}
