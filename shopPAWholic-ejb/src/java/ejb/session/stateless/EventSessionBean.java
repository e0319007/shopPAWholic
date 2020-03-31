/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Event;
import entity.Seller;
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
import util.exception.CreateNewEventException;
import util.exception.EventNameExistsException;
import util.exception.EventNotFoundException;
import util.exception.InputDataValidationException;

/**
 *
 * @author Shi Zhan
 */
@Stateless
public class EventSessionBean implements EventSessionBeanLocal {

    @PersistenceContext(unitName = "shopPAWholic-ejbPU")
    private EntityManager em;
    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public EventSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = (Validator) validatorFactory.getValidator();
    }

    @Override
    public Event createNewEvent(Event event, Long sellerId) throws CreateNewEventException, InputDataValidationException, EventNameExistsException {
        Set<ConstraintViolation<Event>> constraintViolations;
        constraintViolations = validator.validate(event);
        if (retrieveEventByName(event.getEventName()) == null) {
            if (constraintViolations.isEmpty()) {
                try {
                    Seller seller = em.find(Seller.class, sellerId);
                    event.setSeller(seller);
                    seller.getEvents().add(event);
                    
                    em.persist(event);
                    em.flush();

                   return event;
                } catch (Exception ex) {
                    throw new CreateNewEventException("An unexpected error has occurred: " + ex.getMessage());
                }
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new EventNameExistsException("Event title of name \"" + event.getEventName()+ "\" exists already. Please try another title.");
        }
    }
    
    @Override
    public void updateEvent(Event event) throws InputDataValidationException, EventNameExistsException {
        Set<ConstraintViolation<Event>>constraintViolations = validator.validate(event);
        if(constraintViolations.isEmpty()) {
            if (retrieveEventByName(event.getEventName()) == null) {
                if(event.getEventId()!= null) {
                    em.merge(event);
                }
            } else {
                throw new EventNameExistsException("Event title of name \"" + event.getEventName()+ "\" exists already. Please try another title.");
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }
    
    @Override
    public Event retrieveEventByName(String name) {
        Query query = em.createQuery("SELECT e FROM Event e WHERE e.eventName = :inEventName");
        query.setParameter("inEventName",name);
        return (Event) query.getSingleResult();
    }
    
    @Override
    public void deleteEvent(Long id) throws EventNotFoundException {
        Event event = retrieveEventById(id);
        em.remove(event);
    }
    
    @Override
    public Event retrieveEventById(Long id) throws EventNotFoundException {
        Event event = em.find(Event.class, id);
        if (event != null) return event;
        else throw new EventNotFoundException("Event ID " + id + " does not exist!");
    }
    
    @Override
    public List<Event> retrieveAllEvent() {
        Query query = em.createQuery("SELECT e FROM Event e");
        return query.getResultList();
    }
    
    @Override
    public List<Event> retrieveEventBySellerId(Long sellerId) {
        Query query = em.createQuery("SELECT e FROM Event e WHERE e.seller.userId = :sellerId");
        query.setParameter("sellerId", sellerId);
        return query.getResultList();
    }
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Event>>constraintViolations) {
        String msg = "Input data validation error!:";    
        for(ConstraintViolation constraintViolation:constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        return msg;
    }
}
