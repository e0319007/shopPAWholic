/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Comment;
import java.util.List;
import javax.ejb.Local;
import util.exception.CommentNotFoundException;
import util.exception.CreateNewCommentException;
import util.exception.ForumPostNotFoundException;
import util.exception.InputDataValidationException;

/**
 *
 * @author Shi Zhan
 */
@Local
public interface CommentSessionBeanLocal {

    
    public void updateComment(Comment comment) throws InputDataValidationException;

    public void deleteComment(Long commentId) throws CommentNotFoundException;

    public List<Comment> retrieveCommentsByForumPost(Long id) throws ForumPostNotFoundException;

    public Comment retrieveCommentById(Long id) throws CommentNotFoundException;

    public Comment createNewCommentForForumPost(Comment comment, Long customerId, Long forumPostId) throws CreateNewCommentException, InputDataValidationException;

    public Comment createNewChildComment(Comment comment, Long customerId, Long parentComentId) throws CreateNewCommentException, InputDataValidationException;

    public void thumbsUp(Long commentId) throws CommentNotFoundException;
    
}
