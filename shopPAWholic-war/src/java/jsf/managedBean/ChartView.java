package jsf.managedBean;

import ejb.session.stateless.AdvertisementSessionBeanLocal;
import ejb.session.stateless.CustomerSessionBeanLocal;
import ejb.session.stateless.EventSessionBeanLocal;
import ejb.session.stateless.ListingSessionBeanLocal;
import ejb.session.stateless.SellerSessionBeanLocal;
import ejb.session.stateless.UserSessionBeanLocal;
import entity.Category;
import entity.Customer;
import entity.Seller;
import entity.User;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;
import org.primefaces.model.charts.polar.PolarAreaChartDataSet;
import org.primefaces.model.charts.polar.PolarAreaChartModel;

@Named(value = "chartView")
@RequestScoped
public class ChartView implements Serializable {

    @EJB(name = "EventSessionBeanLocal")
    private EventSessionBeanLocal eventSessionBeanLocal;

    @EJB(name = "AdvertisementSessionBeanLocal")
    private AdvertisementSessionBeanLocal advertisementSessionBeanLocal;

    @EJB(name = "ListingSessionBeanLocal")
    private ListingSessionBeanLocal listingSessionBeanLocal;

    @EJB(name = "CustomerSessionBeanLocal")
    private CustomerSessionBeanLocal customerSessionBeanLocal;

    @EJB(name = "SellerSessionBeanLocal")
    private SellerSessionBeanLocal sellerSessionBeanLocal;

    @EJB(name = "UserSessionBeanLocal")
    private UserSessionBeanLocal userSessionBeanLocal;

    private User currentUser;
    private Customer currentCustomer;
    private Seller currentSeller;

    //Dashboard
    private Integer totalNoOfUsers;
    private Integer totalNoOfListings;
    private Integer totalNoOfAdvertisements;
    private Integer totalNoOfEvents;
    private BigDecimal totalRevenue;

    //userAnalysis
    private BarChartModel userBarModel;
    private PieChartModel userPieModel;
    private LineChartModel userLineModel;

    //listingAnalysis
    private LineChartModel listingLineModel;
    private BarChartModel listingBarModel;
    private PolarAreaChartModel listingPolarAreaModel;

    //advertisementAnalysis
    private LineChartModel advertisementLineModel;
    private BarChartModel advertisementBarModel;

    //eventAnalysis
    private LineChartModel eventLineModel;
    private BarChartModel eventBarModel;

    //sellerSideAnalysis
    private BarChartModel sellerBarModel;
    private PieChartModel sellerPieModel;
    private LineChartModel sellerLineModel;

    public ChartView() {
        if (currentUser != null) {
            currentUser = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
            if (currentUser instanceof Customer) {
                currentCustomer = (Customer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
            } else {
                currentSeller = (Seller) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
            }
            System.out.println("#########" + currentSeller);
        }
    }

    @PostConstruct
    public void postConstruct() {
        totalNumberOfUsers();
        totalNumberOfListings();
        totalNumberOfAdvertisements();
        totalNumberOfEvents();
        totalRevenue();

        createUserBarModel();
        createUserPieModel();
        createUserLineModel();

        createListingLineModel();
        createListingBarModel();
        createListingPolarAreaModel();

        createAdvertisementLineModel();
        createAdvertisementBarModel();

        createEventLineModel();
        createEventBarModel();

        createSellerListingBarModel();
        createSellerListingPieModel();
    }

    private void createSellerListingBarModel() {
        sellerBarModel = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("Total Number of Listings Listed Per Month");

        Map<String, Integer> userPerMonth = listingSessionBeanLocal.retrieveListingsPerMonthBySellerId(currentSeller.getUserId());
        List<Number> values = new ArrayList<>();

        values.add(userPerMonth.get("January"));
        values.add(userPerMonth.get("February"));
        values.add(userPerMonth.get("March"));
        values.add(userPerMonth.get("April"));
        values.add(userPerMonth.get("May"));
        values.add(userPerMonth.get("June"));
        values.add(userPerMonth.get("July"));
        values.add(userPerMonth.get("August"));
        values.add(userPerMonth.get("September"));
        values.add(userPerMonth.get("October"));
        values.add(userPerMonth.get("November"));
        values.add(userPerMonth.get("December"));
        barDataSet.setData(values);

        List<String> bgColor = new ArrayList<>();
        bgColor.add("rgba(255, 99, 132, 0.2)");
        bgColor.add("rgba(255, 159, 64, 0.2)");
        bgColor.add("rgba(255, 205, 86, 0.2)");
        bgColor.add("rgba(75, 192, 192, 0.2)");
        bgColor.add("rgba(54, 162, 235, 0.2)");
        bgColor.add("rgba(153, 102, 255, 0.2)");
        bgColor.add("rgba(201, 203, 207, 0.2)");
        bgColor.add("rgba(255, 99, 132, 0.2)");
        bgColor.add("rgba(255, 159, 64, 0.2)");
        bgColor.add("rgba(255, 205, 86, 0.2)");
        bgColor.add("rgba(75, 192, 192, 0.2)");
        bgColor.add("rgba(54, 162, 235, 0.2)");
        barDataSet.setBackgroundColor(bgColor);

        List<String> borderColor = new ArrayList<>();
        borderColor.add("rgb(255, 99, 132)");
        borderColor.add("rgb(255, 159, 64)");
        borderColor.add("rgb(255, 205, 86)");
        borderColor.add("rgb(75, 192, 192)");
        borderColor.add("rgb(54, 162, 235)");
        borderColor.add("rgb(153, 102, 255)");
        borderColor.add("rgb(201, 203, 207)");
        barDataSet.setBorderColor(borderColor);
        barDataSet.setBorderWidth(1);

        data.addChartDataSet(barDataSet);

        List<String> labels = new ArrayList<>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");
        labels.add("July");
        labels.add("August");
        labels.add("September");
        labels.add("October");
        labels.add("November");
        labels.add("December");
        data.setLabels(labels);

        sellerBarModel.setData(data);
    }

    public void createSellerListingPieModel() {
        sellerPieModel = new PieChartModel();
        ChartData data = new ChartData();

        PieChartDataSet dataSet = new PieChartDataSet();

        Map<String, Integer> categoryList = listingSessionBeanLocal.retrieveAllCategories(currentSeller.getUserId());
        System.out.println(categoryList);
        List<Number> values = new ArrayList<>();
        for (String i : categoryList.keySet()) {
            values.add(categoryList.get(i));
        }
        System.out.println(values);
        dataSet.setData(values);

        List<String> bgColors = new ArrayList<>();
        bgColors.add("rgb(255, 99, 132)");
        bgColors.add("rgb(54, 162, 235)");
        bgColors.add("rgb((240,128,128)");
        bgColors.add("rgb(175,238,238)");
        bgColors.add("rgb(216,191,216)");
        bgColors.add("rgb(255,192,203)");
        bgColors.add("rgb(255,228,181))");
        bgColors.add("rgb(176,196,222)");
        bgColors.add("rgb(192,192,192)");
        bgColors.add("rgb(255, 205, 86)");
        bgColors.add("rgb(75, 192, 192)");
        bgColors.add("rgb(54, 162, 235)");
        bgColors.add("rgb(153, 102, 255)");
        bgColors.add("rgb(201, 203, 207)");

        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        for (String category : categoryList.keySet()) {
            labels.add(category);
        }

        data.setLabels(labels);

        sellerPieModel.setData(data);
    }

    public void setCurrentCustomer(Customer currentCustomer) {
        this.currentCustomer = currentCustomer;
    }

    public void setCurrentSeller(Seller currentSeller) {
        this.currentSeller = currentSeller;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public Customer getCurrentCustomer() {
        return currentCustomer;
    }

    public Seller getCurrentSeller() {
        return currentSeller;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public BarChartModel getUserBarModel() {
        return userBarModel;
    }

    public void setUserBarModel(BarChartModel userBarModel) {
        this.userBarModel = userBarModel;
    }

    public PieChartModel getUserPieModel() {
        return userPieModel;
    }

    public void setUserPieModel(PieChartModel userPieModel) {
        this.userPieModel = userPieModel;
    }

    public LineChartModel getUserLineModel() {
        return userLineModel;
    }

    public void setUserLineModel(LineChartModel userLineModel) {
        this.userLineModel = userLineModel;
    }

    public LineChartModel getListingLineModel() {
        return listingLineModel;
    }

    public void setListingLineModel(LineChartModel listingLineModel) {
        this.listingLineModel = listingLineModel;
    }

    public PolarAreaChartModel getListingPolarAreaModel() {
        return listingPolarAreaModel;
    }

    public void setListingPolarAreaModel(PolarAreaChartModel listingPolarAreaModel) {
        this.listingPolarAreaModel = listingPolarAreaModel;
    }

    public BarChartModel getAdvertisementBarModel() {
        return advertisementBarModel;
    }

    public LineChartModel getAdvertisementLineModel() {
        return advertisementLineModel;
    }

    public void setAdvertisementBarModel(BarChartModel advertisementBarModel) {
        this.advertisementBarModel = advertisementBarModel;
    }

    public void setAdvertisementLineModel(LineChartModel advertisementLineModel) {
        this.advertisementLineModel = advertisementLineModel;
    }

    public BarChartModel getEventBarModel() {
        return eventBarModel;
    }

    public LineChartModel getEventLineModel() {
        return eventLineModel;
    }

    public void setEventBarModel(BarChartModel eventBarModel) {
        this.eventBarModel = eventBarModel;
    }

    public void setEventLineModel(LineChartModel eventLineModel) {
        this.eventLineModel = eventLineModel;
    }

    public Integer getTotalNoOfAdvertisements() {
        return totalNoOfAdvertisements;
    }

    public Integer getTotalNoOfListings() {
        return totalNoOfListings;
    }

    public Integer getTotalNoOfUsers() {
        return totalNoOfUsers;
    }

    public Integer getTotalNoOfEvents() {
        return totalNoOfEvents;
    }

    public void setTotalNoOfEvents(Integer totalNoOfEvents) {
        this.totalNoOfEvents = totalNoOfEvents;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalNoOfAdvertisements(Integer totalNoOfAdvertisements) {
        this.totalNoOfAdvertisements = totalNoOfAdvertisements;
    }

    public void setTotalNoOfListings(Integer totalNoOfListings) {
        this.totalNoOfListings = totalNoOfListings;
    }

    public void setTotalNoOfUsers(Integer totalNoOfUsers) {
        this.totalNoOfUsers = totalNoOfUsers;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public BarChartModel getListingBarModel() {
        return listingBarModel;
    }

    public void setListingBarModel(BarChartModel listingBarModel) {
        this.listingBarModel = listingBarModel;
    }

    private void totalNumberOfUsers() {
        totalNoOfUsers = userSessionBeanLocal.retrieveAllUsers().size();
    }

    private void totalNumberOfListings() {
        totalNoOfListings = listingSessionBeanLocal.retrieveAllListings().size();
    }

    private void totalNumberOfAdvertisements() {
        totalNoOfAdvertisements = advertisementSessionBeanLocal.retrieveAllAdvertisements().size();
    }

    private void totalNumberOfEvents() {
        totalNoOfEvents = eventSessionBeanLocal.retrieveAllEvent().size();
    }

    private void totalRevenue() {
        totalRevenue = advertisementSessionBeanLocal.retrieveAllRevenue();
    }

    public BarChartModel getSellerBarModel() {
        return sellerBarModel;
    }

    public LineChartModel getSellerLineModel() {
        return sellerLineModel;
    }

    public PieChartModel getSellerPieModel() {
        return sellerPieModel;
    }

    public void setSellerBarModel(BarChartModel sellerBarModel) {
        this.sellerBarModel = sellerBarModel;
    }

    public void setSellerLineModel(LineChartModel sellerLineModel) {
        this.sellerLineModel = sellerLineModel;
    }

    public void setSellerPieModel(PieChartModel sellerPieModel) {
        this.sellerPieModel = sellerPieModel;
    }

    private void createUserBarModel() {
        userBarModel = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("Total Number of Users Per Month");

        Map<String, Integer> userPerMonth = userSessionBeanLocal.retrieveTotalNumberOfUsersForTheYear();
        List<Number> values = new ArrayList<>();

        values.add(userPerMonth.get("January"));
        values.add(userPerMonth.get("February"));
        values.add(userPerMonth.get("March"));
        values.add(userPerMonth.get("April"));
        values.add(userPerMonth.get("May"));
        values.add(userPerMonth.get("June"));
        values.add(userPerMonth.get("July"));
        values.add(userPerMonth.get("August"));
        values.add(userPerMonth.get("September"));
        values.add(userPerMonth.get("October"));
        values.add(userPerMonth.get("November"));
        values.add(userPerMonth.get("December"));
        barDataSet.setData(values);

        List<String> bgColor = new ArrayList<>();
        bgColor.add("rgba(255, 99, 132, 0.2)");
        bgColor.add("rgba(255, 159, 64, 0.2)");
        bgColor.add("rgba(255, 205, 86, 0.2)");
        bgColor.add("rgba(75, 192, 192, 0.2)");
        bgColor.add("rgba(54, 162, 235, 0.2)");
        bgColor.add("rgba(153, 102, 255, 0.2)");
        bgColor.add("rgba(201, 203, 207, 0.2)");
        bgColor.add("rgba(255, 99, 132, 0.2)");
        bgColor.add("rgba(255, 159, 64, 0.2)");
        bgColor.add("rgba(255, 205, 86, 0.2)");
        bgColor.add("rgba(75, 192, 192, 0.2)");
        bgColor.add("rgba(54, 162, 235, 0.2)");
        barDataSet.setBackgroundColor(bgColor);

        List<String> borderColor = new ArrayList<>();
        borderColor.add("rgb(255, 99, 132)");
        borderColor.add("rgb(255, 159, 64)");
        borderColor.add("rgb(255, 205, 86)");
        borderColor.add("rgb(75, 192, 192)");
        borderColor.add("rgb(54, 162, 235)");
        borderColor.add("rgb(153, 102, 255)");
        borderColor.add("rgb(201, 203, 207)");
        barDataSet.setBorderColor(borderColor);
        barDataSet.setBorderWidth(1);

        data.addChartDataSet(barDataSet);

        List<String> labels = new ArrayList<>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");
        labels.add("July");
        labels.add("August");
        labels.add("September");
        labels.add("October");
        labels.add("November");
        labels.add("December");
        data.setLabels(labels);

        userBarModel.setData(data);
    }

    private void createUserPieModel() {
        userPieModel = new PieChartModel();
        ChartData data = new ChartData();

        PieChartDataSet dataSet = new PieChartDataSet();

        List<Seller> sellersList = sellerSessionBeanLocal.retrieveAllSellers();
        List<Customer> customersList = customerSessionBeanLocal.retrieveAllCustomers();

        int totalSeller = sellersList.size();
        int totalCustomer = customersList.size();

        List<Number> values = new ArrayList<>();
        values.add(totalSeller);
        values.add(totalCustomer);
        dataSet.setData(values);

        List<String> bgColors = new ArrayList<>();
        bgColors.add("rgb(255, 99, 132)");
        bgColors.add("rgb(54, 162, 235)");
        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        labels.add("Total Number of Seller");
        labels.add("Total Number of Customer");
        data.setLabels(labels);

        userPieModel.setData(data);
    }

    public void createUserLineModel() {
        userLineModel = new LineChartModel();
        ChartData data = new ChartData();

        LineChartDataSet dataSet = new LineChartDataSet();

        Map<String, Integer> userPerDay = userSessionBeanLocal.retrieveTotalNumberOfUsersForDay();
        List<Number> values = new ArrayList<>();
        for (String i : userPerDay.keySet()) {
            values.add(userPerDay.get(i));
        }
        dataSet.setData(values);
        dataSet.setFill(false);
        dataSet.setLabel("Total Number of Users Registered Per Day");
        dataSet.setBorderColor("rgb(75, 192, 192)");
        dataSet.setLineTension(0.1);
        data.addChartDataSet(dataSet);

        List<String> labels = new ArrayList<>();
        for (String i : userPerDay.keySet()) {
            labels.add(i);
        }
        data.setLabels(labels);

        userLineModel.setData(data);
    }

    private void createListingLineModel() {
        listingLineModel = new LineChartModel();
        ChartData data = new ChartData();

        LineChartDataSet dataSet = new LineChartDataSet();

        Map<String, Integer> listingPerDay = listingSessionBeanLocal.retrieveTotalNumberOfListingsForDay();
        List<Number> values = new ArrayList<>();
        for (String i : listingPerDay.keySet()) {
            values.add(listingPerDay.get(i));
        }
        dataSet.setData(values);
        dataSet.setFill(false);
        dataSet.setLabel("Total Number of Listing Posted Per Day");
        dataSet.setBorderColor("rgb(75, 192, 192)");
        dataSet.setLineTension(0.1);
        data.addChartDataSet(dataSet);

        List<String> labels = new ArrayList<>();
        for (String i : listingPerDay.keySet()) {
            labels.add(i);
        }
        data.setLabels(labels);

        listingLineModel.setData(data);
    }

    private void createListingBarModel() {
        listingBarModel = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("Total Number of Listings Per Month");

        Map<String, Integer> userPerMonth = listingSessionBeanLocal.retrieveTotalNumberOfListingsForTheYear();
        List<Number> values = new ArrayList<>();

        values.add(userPerMonth.get("January"));
        values.add(userPerMonth.get("February"));
        values.add(userPerMonth.get("March"));
        values.add(userPerMonth.get("April"));
        values.add(userPerMonth.get("May"));
        values.add(userPerMonth.get("June"));
        values.add(userPerMonth.get("July"));
        values.add(userPerMonth.get("August"));
        values.add(userPerMonth.get("September"));
        values.add(userPerMonth.get("October"));
        values.add(userPerMonth.get("November"));
        values.add(userPerMonth.get("December"));
        barDataSet.setData(values);

        List<String> bgColor = new ArrayList<>();
        bgColor.add("rgba(255, 99, 132, 0.2)");
        bgColor.add("rgba(255, 159, 64, 0.2)");
        bgColor.add("rgba(255, 205, 86, 0.2)");
        bgColor.add("rgba(75, 192, 192, 0.2)");
        bgColor.add("rgba(54, 162, 235, 0.2)");
        bgColor.add("rgba(153, 102, 255, 0.2)");
        bgColor.add("rgba(201, 203, 207, 0.2)");
        bgColor.add("rgba(255, 99, 132, 0.2)");
        bgColor.add("rgba(255, 159, 64, 0.2)");
        bgColor.add("rgba(255, 205, 86, 0.2)");
        bgColor.add("rgba(75, 192, 192, 0.2)");
        bgColor.add("rgba(54, 162, 235, 0.2)");
        barDataSet.setBackgroundColor(bgColor);

        List<String> borderColor = new ArrayList<>();
        borderColor.add("rgb(255, 99, 132)");
        borderColor.add("rgb(255, 159, 64)");
        borderColor.add("rgb(255, 205, 86)");
        borderColor.add("rgb(75, 192, 192)");
        borderColor.add("rgb(54, 162, 235)");
        borderColor.add("rgb(153, 102, 255)");
        borderColor.add("rgb(201, 203, 207)");
        barDataSet.setBorderColor(borderColor);
        barDataSet.setBorderWidth(1);

        data.addChartDataSet(barDataSet);

        List<String> labels = new ArrayList<>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");
        labels.add("July");
        labels.add("August");
        labels.add("September");
        labels.add("October");
        labels.add("November");
        labels.add("December");
        data.setLabels(labels);

        listingBarModel.setData(data);
    }

    private void createListingPolarAreaModel() {
        listingPolarAreaModel = new PolarAreaChartModel();
        ChartData data = new ChartData();

        PolarAreaChartDataSet dataSet = new PolarAreaChartDataSet();
        Map<String, Integer> listingsPerCategory = listingSessionBeanLocal.retrieveTotalNumberOfListingsPerCategory();
        List<Number> values = new ArrayList<>();
        for (String i : listingsPerCategory.keySet()) {
            values.add(listingsPerCategory.get(i));
        }
        dataSet.setData(values);

        List<String> bgColors = new ArrayList<>();
        bgColors.add("rgb(255, 99, 132)");
        bgColors.add("rgb(75, 192, 192)");
        bgColors.add("rgb(255, 205, 86)");
        bgColors.add("rgb(201, 203, 207)");
        bgColors.add("rgb(54, 162, 235)");
        bgColors.add("rgb(255,192,203)");
        bgColors.add("rgb(160,82,45)");
        bgColors.add("rbg(176,196,222)");
        bgColors.add("rgb(255,240,245)");
        bgColors.add("rgb(255,218,185)");
        bgColors.add("rgb(255,218,185)");
        bgColors.add("rgb(188,143,143)");
        bgColors.add("rgb(176,196,222)");
        bgColors.add("rgb(240,255,240)");
        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        for (String i : listingsPerCategory.keySet()) {
            labels.add(i);
        }
        data.setLabels(labels);

        listingPolarAreaModel.setData(data);
    }

    public void createAdvertisementLineModel() {
        advertisementLineModel = new LineChartModel();
        ChartData data = new ChartData();

        LineChartDataSet dataSet = new LineChartDataSet();

        Map<String, Integer> advertisementPerDay = advertisementSessionBeanLocal.retrieveTotalNumberOfAdvertisementsForDay();
        List<Number> values = new ArrayList<>();
        for (String i : advertisementPerDay.keySet()) {
            values.add(advertisementPerDay.get(i));
        }
        dataSet.setData(values);
        dataSet.setFill(false);
        dataSet.setLabel("Total Number of Advertisements Posted Per Day");
        dataSet.setBorderColor("rgb(75, 192, 192)");
        dataSet.setLineTension(0.1);
        data.addChartDataSet(dataSet);

        List<String> labels = new ArrayList<>();
        for (String i : advertisementPerDay.keySet()) {
            labels.add(i);
        }
        data.setLabels(labels);

        advertisementLineModel.setData(data);
    }

    public void createAdvertisementBarModel() {
        advertisementBarModel = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("Total Number of Listings Per Month");

        Map<String, Integer> userPerMonth = advertisementSessionBeanLocal.retrieveTotalNumberOfAdvertisementsForTheYear();
        List<Number> values = new ArrayList<>();

        values.add(userPerMonth.get("January"));
        values.add(userPerMonth.get("February"));
        values.add(userPerMonth.get("March"));
        values.add(userPerMonth.get("April"));
        values.add(userPerMonth.get("May"));
        values.add(userPerMonth.get("June"));
        values.add(userPerMonth.get("July"));
        values.add(userPerMonth.get("August"));
        values.add(userPerMonth.get("September"));
        values.add(userPerMonth.get("October"));
        values.add(userPerMonth.get("November"));
        values.add(userPerMonth.get("December"));
        barDataSet.setData(values);

        List<String> bgColor = new ArrayList<>();
        bgColor.add("rgba(255, 99, 132, 0.2)");
        bgColor.add("rgba(255, 159, 64, 0.2)");
        bgColor.add("rgba(255, 205, 86, 0.2)");
        bgColor.add("rgba(75, 192, 192, 0.2)");
        bgColor.add("rgba(54, 162, 235, 0.2)");
        bgColor.add("rgba(153, 102, 255, 0.2)");
        bgColor.add("rgba(201, 203, 207, 0.2)");
        bgColor.add("rgba(255, 99, 132, 0.2)");
        bgColor.add("rgba(255, 159, 64, 0.2)");
        bgColor.add("rgba(255, 205, 86, 0.2)");
        bgColor.add("rgba(75, 192, 192, 0.2)");
        bgColor.add("rgba(54, 162, 235, 0.2)");
        barDataSet.setBackgroundColor(bgColor);

        List<String> borderColor = new ArrayList<>();
        borderColor.add("rgb(255, 99, 132)");
        borderColor.add("rgb(255, 159, 64)");
        borderColor.add("rgb(255, 205, 86)");
        borderColor.add("rgb(75, 192, 192)");
        borderColor.add("rgb(54, 162, 235)");
        borderColor.add("rgb(153, 102, 255)");
        borderColor.add("rgb(201, 203, 207)");
        barDataSet.setBorderColor(borderColor);
        barDataSet.setBorderWidth(1);

        data.addChartDataSet(barDataSet);

        List<String> labels = new ArrayList<>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");
        labels.add("July");
        labels.add("August");
        labels.add("September");
        labels.add("October");
        labels.add("November");
        labels.add("December");
        data.setLabels(labels);

        advertisementBarModel.setData(data);

    }

    private void createEventLineModel() {
        eventLineModel = new LineChartModel();
        ChartData data = new ChartData();

        LineChartDataSet dataSet = new LineChartDataSet();

        Map<String, Integer> advertisementPerDay = eventSessionBeanLocal.retrieveTotalNumberOfEventsForDay();
        List<Number> values = new ArrayList<>();
        for (String i : advertisementPerDay.keySet()) {
            values.add(advertisementPerDay.get(i));
        }
        dataSet.setData(values);
        dataSet.setFill(false);
        dataSet.setLabel("Total Number of Events Posted Per Day");
        dataSet.setBorderColor("rgb(75, 192, 192)");
        dataSet.setLineTension(0.1);
        data.addChartDataSet(dataSet);

        List<String> labels = new ArrayList<>();
        for (String i : advertisementPerDay.keySet()) {
            labels.add(i);
        }
        data.setLabels(labels);

        eventLineModel.setData(data);
    }

    private void createEventBarModel() {
        eventBarModel = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("Total Number of Listings Per Month");

        Map<String, Integer> userPerMonth = eventSessionBeanLocal.retrieveTotalNumberOfEventsForTheYear();
        List<Number> values = new ArrayList<>();

        values.add(userPerMonth.get("January"));
        values.add(userPerMonth.get("February"));
        values.add(userPerMonth.get("March"));
        values.add(userPerMonth.get("April"));
        values.add(userPerMonth.get("May"));
        values.add(userPerMonth.get("June"));
        values.add(userPerMonth.get("July"));
        values.add(userPerMonth.get("August"));
        values.add(userPerMonth.get("September"));
        values.add(userPerMonth.get("October"));
        values.add(userPerMonth.get("November"));
        values.add(userPerMonth.get("December"));
        barDataSet.setData(values);

        List<String> bgColor = new ArrayList<>();
        bgColor.add("rgba(255, 99, 132, 0.2)");
        bgColor.add("rgba(255, 159, 64, 0.2)");
        bgColor.add("rgba(255, 205, 86, 0.2)");
        bgColor.add("rgba(75, 192, 192, 0.2)");
        bgColor.add("rgba(54, 162, 235, 0.2)");
        bgColor.add("rgba(153, 102, 255, 0.2)");
        bgColor.add("rgba(201, 203, 207, 0.2)");
        bgColor.add("rgba(255, 99, 132, 0.2)");
        bgColor.add("rgba(255, 159, 64, 0.2)");
        bgColor.add("rgba(255, 205, 86, 0.2)");
        bgColor.add("rgba(75, 192, 192, 0.2)");
        bgColor.add("rgba(54, 162, 235, 0.2)");
        barDataSet.setBackgroundColor(bgColor);

        List<String> borderColor = new ArrayList<>();
        borderColor.add("rgb(255, 99, 132)");
        borderColor.add("rgb(255, 159, 64)");
        borderColor.add("rgb(255, 205, 86)");
        borderColor.add("rgb(75, 192, 192)");
        borderColor.add("rgb(54, 162, 235)");
        borderColor.add("rgb(153, 102, 255)");
        borderColor.add("rgb(201, 203, 207)");
        barDataSet.setBorderColor(borderColor);
        barDataSet.setBorderWidth(1);

        data.addChartDataSet(barDataSet);

        List<String> labels = new ArrayList<>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");
        labels.add("July");
        labels.add("August");
        labels.add("September");
        labels.add("October");
        labels.add("November");
        labels.add("December");
        data.setLabels(labels);

        eventBarModel.setData(data);
    }
}
