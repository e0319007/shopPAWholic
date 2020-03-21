/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

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
import util.exception.CreateNewEventException;
import util.exception.EventNameExistsException;
import util.exception.EventNotFoundException;
import util.exception.InputDataValidationException;
import javax.annotation.PostConstruct;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Shi Zhan
 */
@Named(value = "eventManagedBean")
@ViewScoped
public class EventManagedBean implements Serializable{

    @EJB(name = "EventSessionBeanLocal")
    private EventSessionBeanLocal eventSessionBeanLocal;

    

    private List<Event> events;
    
    //for creating new events
    private String eventName;
    private String description;
    private String location;
    private List<String> pictures;
    private Date startDateTime;
    private Date endDateTime;
    private String url;
    private Long serviceProviderId;
    
    //for filtering events
    private List<Event> filterByServiceProvider;
    
    
    public EventManagedBean() {
    }
    
    @PostConstruct
    public void PostConstruct() {
        events = eventSessionBeanLocal.retrieveAllEvent();
    }
    
    public void createNewEvent(ActionEvent event) {
        try {
            Event eventToCreate = new Event(eventName, description, location, pictures, startDateTime, endDateTime, url);
            eventSessionBeanLocal.createNewEvent(eventToCreate,serviceProviderId);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New event created successfully! (Id: " + eventToCreate.getEventId() + ")", null));
        } catch (CreateNewEventException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unknown Error occured while creating the event!", null));
        } catch (InputDataValidationException ex) {
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error occured: " + ex.getMessage(), null));
        } catch (EventNameExistsException ex) {
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Event name exist already! Please choose a new event name.", null));
        }
    }
    
    public void updateEvent(ActionEvent event) {
        try {
            Event eventToUpdate = (Event) event.getComponent().getAttributes().get("EventToUpdate");
            eventSessionBeanLocal.updateEvent(eventToUpdate);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Event updated successfully! (Id: " + eventToUpdate.getEventId() + ")", null));
        } catch (InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unknown Error occured while creating the event!", null));
        } catch (EventNameExistsException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Event name exist already! Please choose a new event name.", null));
        }
    }
    
    public void deleteEvent(ActionEvent event ){
        try {
            Event eventToDelete = (Event) event.getComponent().getAttributes().get("EventToDelete");
            eventSessionBeanLocal.deleteEvent(eventToDelete.getEventId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Event deleted successfully! (Id: " + eventToDelete.getEventId() + ")", null));
        } catch (EventNotFoundException ex) {
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Event not found. Please select another event.", null));
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
