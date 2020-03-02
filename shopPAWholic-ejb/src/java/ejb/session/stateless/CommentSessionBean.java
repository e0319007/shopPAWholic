/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Comment;
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
    public Comment createNewComment(Comment comment) throws CreateNewCommentException, InputDataValidationException {
        Set<ConstraintViolation<Comment>> constraintViolations;
        constraintViolations = validator.validate(comment);
        
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
        for (Comment c:comment.getComments()) 
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
