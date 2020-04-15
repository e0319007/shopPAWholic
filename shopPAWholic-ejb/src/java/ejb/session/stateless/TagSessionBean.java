/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Tag;
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
import util.exception.CreateNewTagException;
import util.exception.DeleteTagException;
import util.exception.InputDataValidationException;
import util.exception.TagNotFoundException;
import util.exception.UpdateTagException;

/**
 *
 * @author EileenLeong
 */
@Stateless
public class TagSessionBean implements TagSessionBeanLocal {

    @PersistenceContext(unitName = "shopPAWholic-ejbPU")
    private EntityManager em;
    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public TagSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = (Validator) validatorFactory.getValidator();
    }

    @Override
    public Tag createNewTag(Tag newTag) throws InputDataValidationException, CreateNewTagException {
        Set<ConstraintViolation<Tag>> constraintViolations = validator.validate(newTag);

        if (constraintViolations.isEmpty()) {
            try {
                em.persist(newTag);
                em.flush();
                return newTag;
            } catch (PersistenceException ex) {
                if (ex.getCause() != null
                        && ex.getCause().getCause() != null
                        && ex.getCause().getClass().getSimpleName().equals("SQLIntegrityConstraintViolationException")) {
                    throw new CreateNewTagException("Tag with same name exist.");
                } else {
                    throw new CreateNewTagException("An unexpected error has occured: " + ex.getMessage());
                }
            } catch (Exception ex) {
                throw new CreateNewTagException("An unexpected error has occurred: " + ex.getMessage());
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }

    @Override
    public Tag retrieveTagByTagId(Long tagId) throws TagNotFoundException {
        Tag tag = em.find(Tag.class, tagId);

        if (tag != null) {
            tag.getListings().size();
            return tag;
        } else {
            throw new TagNotFoundException("Tag Id " + tagId + " does not exist!");
        }
    }

    @Override
    public List<Tag> retrieveAllTags() {
        Query query = em.createQuery("SELECT t FROM Tag t ORDER BY t.name ASC");
        List<Tag> tags = query.getResultList();

        for (Tag tag : tags) {
            tag.getListings().size();
        }
        return tags;
    }

    @Override
    public void updateTag(Tag tag) throws InputDataValidationException, TagNotFoundException, UpdateTagException {
        Set<ConstraintViolation<Tag>> constraintViolations = validator.validate(tag);

        if (constraintViolations.isEmpty()) {
            if (tag.getTagId() != null) {
                Tag tagToUpdate = retrieveTagByTagId(tag.getTagId());

                Query query = em.createQuery("SELECT t FROM Tag t WHERE t.name = :inName AND t.tagId <> :inTagId");
                query.setParameter("inName", tag.getName());
                query.setParameter("inTagId", tag.getTagId());

                if (!query.getResultList().isEmpty()) {
                    throw new UpdateTagException("Name of tag to be updated is duplicated!");
                }
                tagToUpdate.setName(tag.getName());
            } else {
                throw new TagNotFoundException("Tag Id is not provided for tag to be updated!");
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }

    @Override
    public void deleteTag(Long tagId) throws TagNotFoundException, DeleteTagException {
        Tag tagToDelete = retrieveTagByTagId(tagId);

        if (!tagToDelete.getListings().isEmpty()) {
            throw new DeleteTagException("Tag Id " + tagId + " is associated with existing listings and cannot be deleted!");
        } else {
            em.remove(tagToDelete);
        }
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Tag>> constraintViolations) {
        String msg = "Input data validation error!:";
        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        return msg;
    }
}
