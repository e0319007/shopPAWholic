package jsf.managedBean;

import ejb.session.stateless.ListingSessionBeanLocal;
import entity.Listing;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import javax.inject.Inject;

@Named(value = "searchListingByNameManagedBean")
@ViewScoped
public class SearchListingsByNameManagedBean implements Serializable {

    @EJB(name = "ListingSessionBeanLocal")
    private ListingSessionBeanLocal listingSessionBeanLocal;

    @Inject
    private ViewListingManagedBean viewListingManagedBean;

    private String searchString;
    private List<Listing> listings;

    public SearchListingsByNameManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        searchString = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("listingSearchString");
        if (searchString == null || searchString.trim().length() == 0) {
            listings = listingSessionBeanLocal.retrieveAllListings();
        } else {
            listings = listingSessionBeanLocal.searchListingsByName(searchString);
        }
    }

    public void searchListing() {
        if (searchString == null || searchString.trim().length() == 0) {
            listings = listingSessionBeanLocal.retrieveAllListings();
        } else {
            listings = listingSessionBeanLocal.searchListingsByName(searchString);
        }
    }

    public ViewListingManagedBean getViewListingManagedBean() {
        return viewListingManagedBean;
    }

    public void setViewListingManagedBean(ViewListingManagedBean viewListingManagedBean) {
        this.viewListingManagedBean = viewListingManagedBean;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listingSearchStr", searchString);
    }

    public List<Listing> getListings() {
        return listings;
    }

    public void setListings(List<Listing> listings) {
        this.listings = listings;
    }
}
