/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

/**
 *
 * @author Shi Zhan
 */
@Entity
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;
    
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
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private ServiceProvider serviceProvider;

    public Event() {
        pictures = new ArrayList<>();
    }

    public Event(String eventName, String description, String location, List<String> pictures, Date startDateTime, Date endDateTime, String url) {
        this();
        this.eventName = eventName;
        this.description = description;
        this.location = location;
        this.pictures = pictures;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.url = url;
    }
    
    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eventId != null ? eventId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the eventId fields are not set
        if (!(object instanceof Event)) {
            return false;
        }
        Event other = (Event) object;
        if ((this.eventId == null && other.eventId != null) || (this.eventId != null && !this.eventId.equals(other.eventId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Event[ id=" + eventId + " ]";
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
    
//    public ServiceProvider getUrl() {
//        return serviceProvider;
//    }
//
//    public void setUrl(ServiceProvider serviceProvider) {
//        this.serviceProvider = serviceProvider;
//    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }
    
}
