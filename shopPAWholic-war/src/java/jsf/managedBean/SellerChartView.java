package jsf.managedBean;

import ejb.session.stateless.AdvertisementSessionBeanLocal;
import ejb.session.stateless.CustomerSessionBeanLocal;
import ejb.session.stateless.EventSessionBeanLocal;
import ejb.session.stateless.ListingSessionBeanLocal;
import ejb.session.stateless.SellerSessionBeanLocal;
import ejb.session.stateless.UserSessionBeanLocal;
import entity.Customer;
import entity.Seller;
import entity.User;
import java.io.Serializable;
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
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;

@Named(value = "sellerChartView")
@RequestScoped
public class SellerChartView implements Serializable {
    
        private User currentUser;
    private Customer currentCustomer;
    private Seller currentSeller;

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
    
        //sellerSideAnalysis
    private BarChartModel sellerBarModel;
    private PieChartModel sellerPieModel;
    private LineChartModel sellerLineModel;
    

    public SellerChartView() {
            currentUser = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
            if (currentUser instanceof Customer) {
                currentCustomer = (Customer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
            } else {
                currentSeller = (Seller) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
            }
            System.out.println("#########" + currentSeller);
        
    }

    @PostConstruct
    public void postConstruct() {
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
}
