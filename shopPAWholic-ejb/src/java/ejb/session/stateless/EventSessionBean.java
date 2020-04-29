package ejb.session.stateless;

import entity.Event;
import entity.Seller;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        Set<ConstraintViolation<Event>> constraintViolations = validator.validate(event);
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
    }

    @Override
    public void updateEvent(Event event) throws InputDataValidationException, EventNameExistsException {
        Set<ConstraintViolation<Event>> constraintViolations = validator.validate(event);
        if (constraintViolations.isEmpty()) {
            if (retrieveEventByName(event.getEventName()) == null) {
                if (event.getEventId() != null) {
                    em.merge(event);
                }
            } else {
                throw new EventNameExistsException("Event title of name \"" + event.getEventName() + "\" exists already. Please try another title.");
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }

    @Override
    public Event retrieveEventByName(String name) {
        Query query = em.createQuery("SELECT e FROM Event e WHERE e.eventName = :inEventName");
        query.setParameter("inEventName", name);
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
        if (event != null) {
            return event;
        } else {
            throw new EventNotFoundException("Event ID " + id + " does not exist!");
        }
    }

    @Override
    public List<Event> retrieveAllEvent() {
        Query query = em.createQuery("SELECT e FROM Event e");
        return query.getResultList();
    }
    
    @Override
    public Map<String, Integer> retrieveTotalNumberOfEventsForTheYear() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        Query query;
        Map<String, Integer> listingPerYear = new LinkedHashMap<>();
        List<String> months = Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        for (int i = 0; i < months.size(); i++) {
            query = em.createQuery("SELECT a FROM Advertisement a WHERE EXTRACT(YEAR(a.listDate)) = :inYear and EXTRACT(MONTH(a.listDate)) = :inMonth");
            query.setParameter("inYear", year);
            query.setParameter("inMonth", i + 1);
            listingPerYear.put(months.get(i), (query.getResultList()).size());
        }
        return listingPerYear;
    }
    
    @Override
    public Map<String, Integer> retrieveTotalNumberOfEventsForDay() {
        Query query;
        long days_in_ms = 1000 * 60 * 60 * 24;
        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() - (7 * days_in_ms));
        List<Date> sevenDays = new ArrayList<>();
        DateFormat dformat = new SimpleDateFormat("yyyy-MM-dd");
        sevenDays.add(startDate);

        while (endDate.before(startDate)) {
            try {
                sevenDays.add(dformat.parse(dformat.format(endDate)));
                endDate = new Date(endDate.getTime() + (days_in_ms));
            } catch (ParseException ex) {
                Logger.getLogger(EventSessionBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        Map<String, Integer> sevenDaysCast = new LinkedHashMap<>();

        for (int i = 0; i < sevenDays.size(); i++) {
            query = em.createQuery("SELECT e FROM Event e WHERE e.listDate between :inStartDate AND :inEndDate");
            query.setParameter("inStartDate", sevenDays.get(i));

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sevenDays.get(i));
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 0);

            query.setParameter("inEndDate", calendar.getTime());

            List<Event> totalNumber = query.getResultList();
            sevenDaysCast.put(dformat.format(sevenDays.get(i)), totalNumber.size());
        }
        return sevenDaysCast;
    }

    @Override
    public List<Event> retrieveEventsBySellerId(Long sellerId) {
        Query query = em.createQuery("SELECT e FROM Event e WHERE e.seller.userId = :sellerId");
        query.setParameter("sellerId", sellerId);
        return query.getResultList();
    }
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Event>> constraintViolations) {
        String msg = "Input data validation error!:";
        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        return msg;
    }
}
