package jsf.managedBean;

import ejb.session.stateless.CustomerSessionBeanLocal;
import ejb.session.stateless.SellerSessionBeanLocal;
import ejb.session.stateless.UserSessionBeanLocal;
import entity.Customer;
import entity.Seller;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.PieChartModel;

@Named(value = "chartView")
@RequestScoped
public class ChartView {

    @EJB(name = "CustomerSessionBeanLocal")
    private CustomerSessionBeanLocal customerSessionBeanLocal;

    @EJB(name = "SellerSessionBeanLocal")
    private SellerSessionBeanLocal sellerSessionBeanLocal;

    @EJB(name = "UserSessionBeanLocal")
    private UserSessionBeanLocal userSessionBeanLocal;

    private BarChartModel barModel;
    private PieChartModel pieModel;
    private LineChartModel dateModel;

    private ScheduleModel scheduleModel;
    private ScheduleEvent scheduleEvent;

    public ChartView() {
        scheduleModel = new DefaultScheduleModel();
        scheduleEvent = new DefaultScheduleEvent();
    }

    @PostConstruct
    public void postConstruct() {
        createBarModel();
        createPieModel();
        createDateModel();
        scheduleModel.addEvent(new DefaultScheduleEvent("Champions League Match", previousDay8Pm(), previousDay11Pm()));
        scheduleModel.addEvent(new DefaultScheduleEvent("Birthday Party", today1Pm(), today6Pm()));
        scheduleModel.addEvent(new DefaultScheduleEvent("Breakfast at Tiffanys", nextDay9Am(), nextDay11Am()));
        scheduleModel.addEvent(new DefaultScheduleEvent("Plant the new garden stuff", theDayAfter3Pm(), fourDaysLater3pm()));
  
    }

    public BarChartModel getBarModel() {
        return barModel;
    }

    public void setBarModel(BarChartModel barModel) {
        this.barModel = barModel;
    }

    public PieChartModel getPieModel() {
        return pieModel;
    }

    public void setPieModel(PieChartModel pieModel) {
        this.pieModel = pieModel;
    }

    public LineChartModel getDateModel() {
        return dateModel;
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

    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();

        ChartSeries numberOfUsers = new ChartSeries();
        numberOfUsers.setLabel("Number of Users");
        Map<String, Integer> userPerMonth = userSessionBeanLocal.retrieveTotalNumberOfUsersForTheYear();
        numberOfUsers.set("January", userPerMonth.get("January"));
        numberOfUsers.set("February", userPerMonth.get("February"));
        numberOfUsers.set("March", userPerMonth.get("March"));
        numberOfUsers.set("April", userPerMonth.get("April"));
        numberOfUsers.set("May", userPerMonth.get("May"));
        numberOfUsers.set("June", userPerMonth.get("June"));
        numberOfUsers.set("July", userPerMonth.get("July"));
        numberOfUsers.set("August", userPerMonth.get("August"));
        numberOfUsers.set("September", userPerMonth.get("September"));
        numberOfUsers.set("October", userPerMonth.get("October"));
        numberOfUsers.set("November", userPerMonth.get("November"));
        numberOfUsers.set("December", userPerMonth.get("December"));

        model.addSeries(numberOfUsers);
        return model;
    }

    private void createBarModel() {
        barModel = initBarModel();

        barModel.setTitle("Total number of users in " + Calendar.getInstance().get(Calendar.YEAR));
        barModel.setLegendPosition("ne");

        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Months");
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Number of users");
    }

    private void createPieModel() {
        pieModel = new PieChartModel();
        List<Seller> sellersList = sellerSessionBeanLocal.retrieveAllSellers();
        List<Customer> customersList = customerSessionBeanLocal.retrieveAllCustomers();

        int totalSeller = sellersList.size();
        int totalCustomer = customersList.size();

        pieModel.set("Sellers", totalSeller);
        pieModel.set("Customers", totalCustomer);
        pieModel.setTitle("2020");
        pieModel.setLegendPosition("w");
    }

    private void createDateModel() {
        System.out.println("$$$$$$$");
        dateModel = new LineChartModel();
        LineChartSeries series = new LineChartSeries();
        Map<String, Integer> userPerDay = userSessionBeanLocal.retrieveTotalNumberOfUsersForDay();
        System.out.println(userPerDay);
        
        for (String i : userPerDay.keySet()) {
            series.set(i, userPerDay.get(i));
        }

        dateModel.addSeries(series);
        dateModel.setTitle("Total Number of Users Registered Per Day");
        dateModel.setZoom(true);
        dateModel.getAxis(AxisType.Y).setLabel("Number of users");
        dateModel.getAxis(AxisType.Y).setMin(0);
        DateAxis axis = new DateAxis("Dates");
        axis.setTickFormat("%b %#d, %y");
 
        dateModel.getAxes().put(AxisType.X, axis);
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
        scheduleEvent = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
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

    private Date previousDay8Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.DATE, t.get(Calendar.DATE) - 1);
        t.set(Calendar.HOUR, 8);

        return t.getTime();
    }

    private Date previousDay11Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.DATE, t.get(Calendar.DATE) - 1);
        t.set(Calendar.HOUR, 11);

        return t.getTime();
    }

    private Date today1Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.HOUR, 1);

        return t.getTime();
    }

    private Date theDayAfter3Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.DATE, t.get(Calendar.DATE) + 2);
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.HOUR, 3);

        return t.getTime();
    }

    private Date today6Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.HOUR, 6);

        return t.getTime();
    }

    private Date nextDay9Am() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.AM);
        t.set(Calendar.DATE, t.get(Calendar.DATE) + 1);
        t.set(Calendar.HOUR, 9);

        return t.getTime();
    }

    private Date nextDay11Am() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.AM);
        t.set(Calendar.DATE, t.get(Calendar.DATE) + 1);
        t.set(Calendar.HOUR, 11);

        return t.getTime();
    }

    private Date fourDaysLater3pm() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.DATE, t.get(Calendar.DATE) + 4);
        t.set(Calendar.HOUR, 3);

        return t.getTime();
    }
}
