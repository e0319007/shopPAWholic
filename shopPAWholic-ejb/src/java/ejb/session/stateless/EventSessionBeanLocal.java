/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Event;
import java.util.List;
import javax.ejb.Local;
import util.exception.CreateNewEventException;
import util.exception.EventNameExistsException;
import util.exception.EventNotFoundException;
import util.exception.InputDataValidationException;

/**
 *
 * @author Shi Zhan
 */
@Local
public interface EventSessionBeanLocal {
    public Event retrieveEventByName(String name);

    public void deleteEvent(Long id) throws EventNotFoundException;

    public Event retrieveEventById(Long id) throws EventNotFoundException;

    public List<Event> retrieveAllEvent();

    public List<Event> retrieveEventBySellerId(Long sellerId);

    public void updateEvent(Event event) throws InputDataValidationException, EventNameExistsException;

    public Event createNewEvent(Event event, Long sellerId) throws CreateNewEventException, InputDataValidationException, EventNameExistsException;

    
    
}
