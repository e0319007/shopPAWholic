/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.ListingSessionBeanLocal;
import entity.Listing;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author EileenLeong
 */
@Named(value = "searchListingByNameManagedBean")
@ViewScoped
public class SearchListingByNameManagedBean {

    @EJB(name = "ListingSessionBeanLocal")
    private ListingSessionBeanLocal listingSessionBeanLocal;
    
    @Inject 
    private ViewListingManagedBean viewListingManagedBean;
    
    private String searchStr; 
    private List<Listing> listings;

    /**
     * Creates a new instance of SearchListingByNameManagedBean
     */
    public SearchListingByNameManagedBean() {
    }
    
    @PostConstruct 
    public void postConstruct() {
        setSearchStr((String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("listingSearchStr"));
        
        if (getSearchStr() == null || getSearchStr().trim().length() == 0) {
            setListings(listingSessionBeanLocal.retrieveAllListings());
        } else {
            setListings(listingSessionBeanLocal.searchListingsByName(getSearchStr()));
        }
    }
    
    public void searchListing() {
        if (getSearchStr() == null || getSearchStr().trim().length() == 0) {
            setListings(listingSessionBeanLocal.retrieveAllListings());
        } else {
            setListings(listingSessionBeanLocal.searchListingsByName(getSearchStr()));
        }
    }

    /**
     * @return the viewListingManagedBean
     */
    public ViewListingManagedBean getViewListingManagedBean() {
        return viewListingManagedBean;
    }

    /**
     * @param viewListingManagedBean the viewListingManagedBean to set
     */
    public void setViewListingManagedBean(ViewListingManagedBean viewListingManagedBean) {
        this.viewListingManagedBean = viewListingManagedBean;
    }

    /**
     * @return the searchStr
     */
    public String getSearchStr() {
        return searchStr;
    }

    /**
     * @param searchStr the searchStr to set
     */
    public void setSearchStr(String searchStr) {
        this.searchStr = searchStr;
    }

    /**
     * @return the listings
     */
    public List<Listing> getListings() {
        return listings;
    }

    /**
     * @param listings the listings to set
     */
    public void setListings(List<Listing> listings) {
        this.listings = listings;
    }
}
