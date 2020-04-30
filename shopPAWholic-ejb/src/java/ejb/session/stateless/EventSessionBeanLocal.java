package ejb.session.stateless;

import entity.Event;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import util.exception.CreateNewEventException;
import util.exception.EventNameExistsException;
import util.exception.EventNotFoundException;
import util.exception.InputDataValidationException;

@Local
public interface EventSessionBeanLocal {

    public Event retrieveEventByName(String name);

    //public void deleteEvent(Long id) throws EventNotFoundException;
    public void deleteEvent(Long eventIdToDelete);

    public Event retrieveEventById(Long id) throws EventNotFoundException;

    public List<Event> retrieveAllEvent();

    public Map<String, Integer> retrieveTotalNumberOfEventsForTheYear();

    public Map<String, Integer> retrieveTotalNumberOfEventsForDay();

    public List<Event> retrieveEventsBySellerId(Long sellerId);

    //public void updateEvent(Event event) throws InputDataValidationException, EventNameExistsException;
    public void updateEvent(Event eventToUpdate);

    public Event createNewEvent(Event event, Long sellerId) throws CreateNewEventException, InputDataValidationException, EventNameExistsException;

    public void deleteEvents(List<Long> eventIdsToDelete);

}
