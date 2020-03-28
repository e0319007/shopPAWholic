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
import java.io.Serializable;
import javax.inject.Inject;

/**
 *
 * @author EileenLeong
 */
@Named(value = "searchListingByNameManagedBean")
@ViewScoped
public class SearchListingsByNameManagedBean implements Serializable{

    @EJB(name = "ListingSessionBeanLocal")
    private ListingSessionBeanLocal listingSessionBeanLocal;
    
    @Inject 
    private ViewListingManagedBean viewListingManagedBean;
    
    private String searchStr; 
    private List<Listing> listings;

    /**
     * Creates a new instance of SearchListingByNameManagedBean
     */
    public SearchListingsByNameManagedBean() {
    }
    
    @PostConstruct 
    public void postConstruct() {
        searchStr = (String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("listingSearchStr");
        
        if (searchStr == null || searchStr.trim().length() == 0) {
            listings = listingSessionBeanLocal.retrieveAllListings();
        } else {
            listings = listingSessionBeanLocal.searchListingsByName(searchStr);
        }
    }
    
    public void searchListing() {
        if (searchStr == null || searchStr.trim().length() == 0) {
            listings = listingSessionBeanLocal.retrieveAllListings();
        } else {
            listings = listingSessionBeanLocal.searchListingsByName(searchStr);
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
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listingSearchStr", searchStr);
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
