/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.ListingSessionBeanLocal;
import ejb.session.stateless.TagSessionBeanLocal;
import entity.Listing;
import entity.Tag;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author EileenLeong
 */
@Named(value = "filterListingsByTagsManagedBean")
@ViewScoped
public class FilterListingsByTagsManagedBean implements Serializable{

    @EJB(name = "ListingSessionBeanLocal")
    private ListingSessionBeanLocal listingSessionBeanLocal;

    @EJB(name = "TagSessionBeanLocal")
    private TagSessionBeanLocal tagSessionBeanLocal;
    
    @Inject
    private ViewListingManagedBean viewListingManagedBean;
    
    private String condition;
    private List<Long> selectedTagIds;
    private List<SelectItem> selectItems;
    private List<Listing> listings;
    

    /**
     * Creates a new instance of FilterListingsByTagsManagedBean
     */
    public FilterListingsByTagsManagedBean() {
        condition = "OR";
    }
    
    
    @PostConstruct
    public void postConstruct()
    {
        List<Tag> tags = tagSessionBeanLocal.retrieveAllTags();
        selectItems = new ArrayList<>();
        
        for(Tag tag:tags)
        {
            selectItems.add(new SelectItem(tag.getTagId(), tag.getName(), tag.getName()));
        }
      
        
        condition = (String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("listingFilterCondition");        
        selectedTagIds = (List<Long>)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("listingFilterTags");
        
        filterListing();
    }
     
    @PreDestroy
    public void preDestroy()
    {
      
    }
    
    public void filterListing()
    {        
        if(selectedTagIds != null && selectedTagIds.size() > 0)
        {
            listings = listingSessionBeanLocal.filterListingsByTags(selectedTagIds, condition);
        }
        else
        {
            listings = listingSessionBeanLocal.retrieveAllListings();
        }
    }
    
    public void viewListingDetails(ActionEvent event) throws IOException
    {
        Long listingIdToView = (Long)event.getComponent().getAttributes().get("listingId");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("listingIdToView", listingIdToView);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("backMode", "filterListingsByTags");
        FacesContext.getCurrentInstance().getExternalContext().redirect("viewListingDetails.xhtml");
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
     * @return the condition
     */
    public String getCondition() {
        return condition;
    }

    /**
     * @param condition the condition to set
     */
    public void setCondition(String condition) {
        this.condition = condition;
    }

    /**
     * @return the selectedTagIds
     */
    public List<Long> getSelectedTagIds() {
        return selectedTagIds;
    }

    /**
     * @param selectedTagIds the selectedTagIds to set
     */
    public void setSelectedTagIds(List<Long> selectedTagIds) {
        this.selectedTagIds = selectedTagIds;
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listingFilterTags", selectedTagIds);
    }

    /**
     * @return the selectItems
     */
    public List<SelectItem> getSelectItems() {
        return selectItems;
    }

    /**
     * @param selectItems the selectItems to set
     */
    public void setSelectItems(List<SelectItem> selectItems) {
        this.selectItems = selectItems;
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
