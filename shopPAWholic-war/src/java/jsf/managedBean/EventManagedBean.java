/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.AdvertisementSessionBeanLocal;
import ejb.session.stateless.EventSessionBeanLocal;
import entity.Event;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;
import util.exception.CreateNewEventException;
import util.exception.EventNameExistsException;
import util.exception.EventNotFoundException;
import util.exception.InputDataValidationException;

/**
 *
 * @author Shi Zhan
 */
@Named(value = "eventManagedBean")
@ViewScoped
public class EventManagedBean {

    @EJB(name = "EventSessionBeanLocal")
    private EventSessionBeanLocal eventSessionBeanLocal;

    

    private List<Event> events;
    /**
     * For creating new events
     */
    @Size(min = 1, message = "Event name must not be empty")
    private String eventName;
    @Size(min = 10, message = "Event description must be more than 10 characters")
    private String description;
    @Size(min = 10, message = "Location must be more than 10 characters")
    private String location;
    @NotNull
    private List<String> pictures;
    @NotNull
    private Date startDateTime;
    @NotNull
    private Date endDateTime;
    @URL
    private String url;
    
    private Long serviceProviderId;
    
    /**
     * Filtering
     */
    private List<Event> filterByServiceProvider;
    
    
    public EventManagedBean() {
    }
    
    public void PostConstruct() {
        events = eventSessionBeanLocal.retrieveAllEvent();
    }
    
    public void createNewEvent(ActionEvent event) {
        try {
            Event eventToCreate = new Event(eventName, description, location, pictures, startDateTime, endDateTime, url);
            eventSessionBeanLocal.createNewEvent(eventToCreate,serviceProviderId);
        } catch (CreateNewEventException ex) {
            Logger.getLogger(EventManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InputDataValidationException ex) {
            Logger.getLogger(EventManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EventNameExistsException ex) {
            Logger.getLogger(EventManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateEvent(ActionEvent event) {
        try {
            Event eventToUpdate = (Event) event.getComponent().getAttributes().get("EventToUpdate");
            eventSessionBeanLocal.updateEvent(eventToUpdate);
        } catch (InputDataValidationException ex) {
            Logger.getLogger(EventManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EventNameExistsException ex) {
            Logger.getLogger(EventManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteEvent(ActionEvent event ){
        try {
            Event eventToDelete = (Event) event.getComponent().getAttributes().get("EventToDelete");
            eventSessionBeanLocal.deleteEvent(eventToDelete.getEventId());
        } catch (EventNotFoundException ex) {
            Logger.getLogger(EventManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public EventSessionBeanLocal getEventSessionBeanLocal() {
        return eventSessionBeanLocal;
    }

    public void setEventSessionBeanLocal(EventSessionBeanLocal eventSessionBeanLocal) {
        this.eventSessionBeanLocal = eventSessionBeanLocal;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Event> getFilterByServiceProvider() {
        return filterByServiceProvider;
    }

    public void setFilterByServiceProvider(List<Event> filterByServiceProvider) {
        this.filterByServiceProvider = filterByServiceProvider;
    }

    public Long getServiceProviderId() {
        return serviceProviderId;
    }

    public void setServiceProviderId(Long serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }
    
    
    
}
