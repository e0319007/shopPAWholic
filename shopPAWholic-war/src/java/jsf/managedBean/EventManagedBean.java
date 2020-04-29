package jsf.managedBean;

import ejb.session.stateless.EventSessionBeanLocal;
import entity.Event;
import entity.User;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import org.primefaces.model.UploadedFile;

@Named(value = "eventManagedBean")
@ViewScoped
public class EventManagedBean implements Serializable {

    @EJB(name = "EventSessionBeanLocal")
    private EventSessionBeanLocal eventSessionBeanLocal;

    private ScheduleModel scheduleModel;
    private ScheduleEvent scheduleEvent;

    private List<Event> allEvents;
    private List<Event> eventsBySellerId;

    //for creating new allEvents
    private String eventName;
    private String description;
    private String location;
    private String picture;
    private Date startDateTime;
    private Date endDateTime;
    private String url;
    private Date listDate;
    private Long sellerId;
    private Event selectedEvent;
    private Event newEvent;

    //for filtering allEvents
    private List<Event> filterBySeller;

    //fileUpload
    private UploadedFile file;

    public EventManagedBean() {
        scheduleModel = new DefaultScheduleModel();
        scheduleEvent = new DefaultScheduleEvent();
        newEvent = new Event();
    }

    @PostConstruct
    public void PostConstruct() {
        allEvents = eventSessionBeanLocal.retrieveAllEvent();
        
        //hardcoded event as example
        scheduleModel.addEvent(new DefaultScheduleEvent("Doggy Party", today1Pm(), today6Pm()));

        User currentUser = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
        sellerId = currentUser.getUserId();
        
        setEventsBySellerId(eventSessionBeanLocal.retrieveEventsBySellerId(sellerId));
    }

    public void handleFileUpload(FileUploadEvent event) {
        User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
        try {
            String destination = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("alternatedocroot_1") + System.getProperty("file.separator");
            String secDest = "Seller"
                    + System.getProperty("file.separator")
                    + user.getUserId()
                    + System.getProperty("file.separator")
                    + "Event"
                    + System.getProperty("file.separator");
            File newPath = new File(destination + secDest);
            newPath.mkdirs();
            System.err.println("********** FileUploadView.handleFileUpload(): File name: " + event.getFile().getFileName());
            System.err.println("********** FileUploadView.handleFileUpload(): newFilePath: " + newPath);

            File uploadEventImage = new File(newPath + "/" + event.getFile().getFileName());
            FileOutputStream fileOutputStream = new FileOutputStream(uploadEventImage);
            //Creates a FileOutputStream to write to the file represented by the specified file

            int a;
            int BUFFER_SIZE = 8192;
            byte[] buffer = new byte[BUFFER_SIZE];

            InputStream inputStream = event.getFile().getInputstream();
            //This getInputStream() method of the uploadedFile represents the file content
            
            picture = "http://localhost:8080/shopPAWholic-war/uploadedFiles/" + secDest + event.getFile().getFileName();

            while (true) {
                a = inputStream.read(buffer);
                if (a < 0) {
                    break;
                }
                fileOutputStream.write(buffer, 0, a);
                //write a bytes from the specified bytes array starting at offset 0 to this FileOutputStream
                fileOutputStream.flush();
            }
            fileOutputStream.close();
            inputStream.close();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "File uploaded successfully", ""));
        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "File upload error: " + ex.getMessage(), ""));
        }
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    public Boolean checkForPicture(){
        if (picture == null) {
            return true;
        } else {
            return false;
        }
    }

    public void createNewEvent(ActionEvent event) {
        try {
            System.out.println("****************************************" + picture);
            listDate = new Date();
            newEvent = new Event(eventName, description, location, picture, startDateTime, endDateTime, url, listDate);
            eventSessionBeanLocal.createNewEvent(newEvent, sellerId);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New event created successfully! (Id: " + newEvent.getEventId() + ")", null));
        } catch (CreateNewEventException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unknown Error occured while creating the event!", null));
        } catch (InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error occured: " + ex.getMessage(), null));
        } catch (EventNameExistsException ex) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Event name exist already! Please choose a new event name.", null));

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

    public void deleteEvent(ActionEvent event) {
        try {
            Event eventToDelete = (Event) event.getComponent().getAttributes().get("EventToDelete");
            eventSessionBeanLocal.deleteEvent(eventToDelete.getEventId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Event deleted successfully! (Id: " + eventToDelete.getEventId() + ")", null));
        } catch (EventNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Event not found. Please select another event.", null));
        }
    }

    public void addEvent(ActionEvent actionEvent) {
        if (scheduleEvent.getId() == null) {
            scheduleModel.addEvent(scheduleEvent);
        } else {
            scheduleModel.updateEvent(scheduleEvent);
        }

        scheduleEvent = new DefaultScheduleEvent();
    }

    public void onEventSelect(SelectEvent selectEvent) {
        scheduleEvent = (ScheduleEvent) selectEvent.getObject();
    }

    public void onDateSelect(SelectEvent selectEvent) {
        scheduleEvent = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
    }

    public void onEventMove(ScheduleEntryMoveEvent scheduleEvent) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Day delta:" + scheduleEvent.getDayDelta() + ", Minute delta:" + scheduleEvent.getMinuteDelta());

        addMessage(message);
    }

    public void onEventResize(ScheduleEntryResizeEvent scheduleEvent) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Day delta:" + scheduleEvent.getDayDelta() + ", Minute delta:" + scheduleEvent.getMinuteDelta());

        addMessage(message);
    }

    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    private Calendar today() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);

        return calendar;
    }

    private Date today1Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.HOUR, 1);

        return t.getTime();
    }

    private Date today6Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.HOUR, 6);

        return t.getTime();
    }

    public List<Event> getAllEvents() {
        return allEvents;
    }

    public void setAllEvents(List<Event> allEvents) {
        this.allEvents = allEvents;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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

    public Date getListDate() {
        return listDate;
    }

    public void setListDate(Date listDate) {
        this.listDate = listDate;
    }

    public List<Event> getEventsBySellerId() {
        return eventsBySellerId;
    }

    public void setEventsBySellerId(List<Event> eventsBySellerId) {
        this.eventsBySellerId = eventsBySellerId;
    }
}
