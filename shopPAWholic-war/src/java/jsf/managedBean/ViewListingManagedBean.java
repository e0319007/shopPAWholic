/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import entity.Listing;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author EileenLeong
 */
@Named(value = "viewListingManagedBean")
@ViewScoped
public class ViewListingManagedBean implements Serializable{
    private Listing listingToView;
    /**
     * Creates a new instance of ViewListingManagedBean
     */
    public ViewListingManagedBean() {
        listingToView = new Listing();
    }
    
    @PostConstruct 
    public void postConstruct() {
    
    }

    /**
     * @return the listingToView
     */
    public Listing getListingToView() {
        return listingToView;
    }

    /**
     * @param listingToView the listingToView to set
     */
    public void setListingToView(Listing listingToView) {
        this.listingToView = listingToView;
    }
    
}
