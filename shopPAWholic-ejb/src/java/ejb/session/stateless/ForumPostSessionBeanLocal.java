/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ForumPost;
import java.util.List;
import javax.ejb.Local;
import util.exception.CreateNewForumPostException;
import util.exception.ForumPostNotFoundException;
import util.exception.ForumTitleExistsException;
import util.exception.InputDataValidationException;

/**
 *
 * @author Shi Zhan
 */
@Local
public interface ForumPostSessionBeanLocal {

    public ForumPost createNewForumPost(ForumPost post, long customer) throws InputDataValidationException, CreateNewForumPostException, ForumTitleExistsException;

    public void updateForumPost(ForumPost post) throws InputDataValidationException, ForumTitleExistsException;

    public List<ForumPost> retrieveAllForumPost();

    public ForumPost retrieveForumPostByTitle(String title);

    public List<ForumPost> filterForumPostByTitle(String searchString);

    public void deleteForumPost(Long id) throws ForumPostNotFoundException;

    public ForumPost retrieveForumPostById(Long id) throws ForumPostNotFoundException;

    public List<ForumPost> filterForumPostByContent(String searchString);

    public List<ForumPost> retrieveForumPostsByUser(Long customerId);

    public void thumbsUp(Long forumPostId) throws ForumPostNotFoundException;

    
}
