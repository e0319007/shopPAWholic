package jsf.managedBean;

import ejb.session.stateless.EventSessionBeanLocal;
import entity.Event;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

@Named(value = "adminCalendarView")
@ViewScoped
public class AdminCalendarView implements Serializable {

    @EJB(name = "EventSessionBeanLocal")
    private EventSessionBeanLocal eventSessionBeanLocal;

    private ScheduleModel scheduleModel;
    private ScheduleEvent scheduleEvent;

    //to add all the events created by users on calendar
    private List<Event> events;

    public AdminCalendarView() {
        scheduleModel = new DefaultScheduleModel();
        scheduleEvent = new DefaultScheduleEvent();
        events = new ArrayList<>();
    }

    @PostConstruct
    public void postConstruct() {
        events = eventSessionBeanLocal.retrieveAllEvent();
        events.forEach((e) -> {
            scheduleModel.addEvent(new DefaultScheduleEvent(e.getEventName(), e.getStartDateTime(), e.getEndDateTime()));
        });
    }

    public ScheduleEvent getScheduleEvent() {
        return scheduleEvent;
    }

    public ScheduleModel getScheduleModel() {
        return scheduleModel;
    }

    public void setScheduleEvent(ScheduleEvent scheduleEvent) {
        this.scheduleEvent = scheduleEvent;
    }

    public void setScheduleModel(ScheduleModel scheduleModel) {
        this.scheduleModel = scheduleModel;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public void onEventSelect(SelectEvent selectEvent) {
        scheduleEvent = (ScheduleEvent) selectEvent.getObject();
        System.out.println(scheduleEvent.getStartDate());
        System.out.println(scheduleEvent.getEndDate());
    }

}
