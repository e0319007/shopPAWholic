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
import java.util.Calendar;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

/**
 *
 * @author Shi Zhan
 */
@Named(value = "eventManagedBean")
@ViewScoped
public class EventManagedBean implements Serializable{

    @EJB(name = "EventSessionBeanLocal")
    private EventSessionBeanLocal eventSessionBeanLocal;

    private ScheduleModel scheduleModel;
    private ScheduleEvent scheduleEvent;

    private List<Event> events;
    
    //for creating new events
    private String eventName;
    private String description;
    private String location;
    private List<String> pictures;
    private Date startDateTime;
    private Date endDateTime;
    private String url;
    private Long sellerId;
    private Event selectedEvent;
    private Event newEvent;
    //for filtering events
    private List<Event> filterBySeller;
    
    
    public EventManagedBean() {
        scheduleModel = new DefaultScheduleModel();
        scheduleEvent = new DefaultScheduleEvent();
        newEvent = new Event();
     
    }
    
    @PostConstruct
    public void PostConstruct() {
        events = eventSessionBeanLocal.retrieveAllEvent();
        //hardcoded event as example
        scheduleModel.addEvent(new DefaultScheduleEvent("Doggy Party", today1Pm(), today6Pm()));
    }
    
    public void createNewEvent(ActionEvent event) {
        try {
           newEvent = new Event(eventName, description, location, pictures, startDateTime, endDateTime, url);
            eventSessionBeanLocal.createNewEvent(newEvent,sellerId);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New event created successfully! (Id: " + newEvent.getEventId() + ")", null));
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
    
    public void addEvent(ActionEvent actionEvent) 
    {
        if(scheduleEvent.getId() == null)
            scheduleModel.addEvent(scheduleEvent);
        else
            scheduleModel.updateEvent(scheduleEvent);
         
        scheduleEvent = new DefaultScheduleEvent();
    }
    
    
    
    public void onEventSelect(SelectEvent selectEvent) 
    {
        scheduleEvent = (ScheduleEvent) selectEvent.getObject();
    }
    
    
    
    public void onDateSelect(SelectEvent selectEvent) 
    {
        scheduleEvent = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
    }
    
    
    
    public void onEventMove(ScheduleEntryMoveEvent scheduleEvent) 
    {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Day delta:" + scheduleEvent.getDayDelta() + ", Minute delta:" + scheduleEvent.getMinuteDelta());
         
        addMessage(message);
    }
    
    
    
    public void onEventResize(ScheduleEntryResizeEvent scheduleEvent) 
    {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Day delta:" + scheduleEvent.getDayDelta() + ", Minute delta:" + scheduleEvent.getMinuteDelta());
         
        addMessage(message);
    }
     
    
    
    private void addMessage(FacesMessage message) 
    {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    
    
    private Calendar today() 
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
 
        return calendar;
    }
    private Date today1Pm() 
    {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.HOUR, 1);

        return t.getTime();
    }
    private Date today6Pm() 
    {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.HOUR, 6);

        return t.getTime();
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

    public List<Event> getFilterBySeller() {
        return filterBySeller;
    }

    public void setFilterBySeller(List<Event> filterBySeller) {
        this.filterBySeller = filterBySeller;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    /**
     * @return the scheduleModel
     */
    public ScheduleModel getScheduleModel() {
        return scheduleModel;
    }

    /**
     * @param scheduleModel the scheduleModel to set
     */
    public void setScheduleModel(ScheduleModel scheduleModel) {
        this.scheduleModel = scheduleModel;
    }

    /**
     * @return the scheduleEvent
     */
    public ScheduleEvent getScheduleEvent() {
        return scheduleEvent;
    }

    /**
     * @param scheduleEvent the scheduleEvent to set
     */
    public void setScheduleEvent(ScheduleEvent scheduleEvent) {
        this.scheduleEvent = scheduleEvent;
    }

    /**
     * @return the selectedEvent
     */
    public Event getSelectedEvent() {
        return selectedEvent;
    }

    /**
     * @param selectedEvent the selectedEvent to set
     */
    public void setSelectedEvent(Event selectedEvent) {
        this.selectedEvent = selectedEvent;
    }

    /**
     * @return the newEvent
     */
    public Event getNewEvent() {
        return newEvent;
    }

    /**
     * @param newEvent the newEvent to set
     */
    public void setNewEvent(Event newEvent) {
        this.newEvent = newEvent;
    }

   
   
    
    
}
