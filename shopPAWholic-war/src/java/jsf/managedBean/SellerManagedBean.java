package jsf.managedBean;

import ejb.session.stateless.AdvertisementSessionBeanLocal;
import ejb.session.stateless.EventSessionBeanLocal;
import ejb.session.stateless.ListingSessionBeanLocal;
import entity.Customer;
import entity.Seller;
import entity.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;

@Named(value = "sellerManagedBean")
@ViewScoped
public class SellerManagedBean implements Serializable {


    @EJB(name = "EventSessionBeanLocal")
    private EventSessionBeanLocal eventSessionBeanLocal;

    @EJB(name = "AdvertisementSessionBeanLocal")
    private AdvertisementSessionBeanLocal advertisementSessionBeanLocal;

    @EJB(name = "ListingSessionBeanLocal")
    private ListingSessionBeanLocal listingSessionBeanLocal;

    private User currentUser;
    private Customer currentCustomer;
    private Seller currentSeller;

    //for homepage analysis
    private Integer totalUnitsListed;
    private Integer totalAdvtsPosted;
    private Integer totalEventsPosted;

    //for chart 
    public SellerManagedBean() {
        currentUser = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
        if (currentUser instanceof Customer) {
            currentCustomer = (Customer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
        } else {
            currentSeller = (Seller) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
        }
    }

    @PostConstruct
    public void postConstruct() {
        setTotalUnitsListed((Integer) listingSessionBeanLocal.retrieveListingsBySellerId(getCurrentSeller().getUserId()).size());
        setTotalAdvtsPosted((Integer) advertisementSessionBeanLocal.retrieveAdvertisementsBySellerId(getCurrentSeller().getUserId()).size());
        setTotalEventsPosted((Integer) eventSessionBeanLocal.retrieveEventsBySellerId(getCurrentSeller().getUserId()).size());
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

    public Integer getTotalAdvtsPosted() {
        return totalAdvtsPosted;
    }

    public Integer getTotalEventsPosted() {
        return totalEventsPosted;
    }

    public Integer getTotalUnitsListed() {
        return totalUnitsListed;
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

    public void setTotalAdvtsPosted(Integer totalAdvtsPosted) {
        this.totalAdvtsPosted = totalAdvtsPosted;
    }

    public void setTotalEventsPosted(Integer totalEventsPosted) {
        this.totalEventsPosted = totalEventsPosted;
    }

    public void setTotalUnitsListed(Integer totalUnitsListed) {
        this.totalUnitsListed = totalUnitsListed;
    }


   
}
