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

@Named(value = "filterListingsByTagsManagedBean")
@ViewScoped
public class FilterListingsByTagsManagedBean implements Serializable {

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

    public FilterListingsByTagsManagedBean() {
        condition = "OR";
    }

    @PostConstruct
    public void postConstruct() {
        List<Tag> tags = tagSessionBeanLocal.retrieveAllTags();
        setSelectItems(new ArrayList<>());

        for (Tag tag : tags) {
            getSelectItems().add(new SelectItem(tag.getTagId(), tag.getName(), tag.getName()));
        }

        setCondition((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("listingFilterCondition"));
        setSelectedTagIds((List<Long>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("listingFilterTags"));

        filterListing();
    }

    @PreDestroy()
    public void preDestroy() {

    }

    public void filterListing() {
        if (selectedTagIds != null && selectedTagIds.size() > 0) {
            System.out.println("################################### listings is in");
            listings = listingSessionBeanLocal.filterListingsByTags(selectedTagIds, condition);
        } else {
            listings = listingSessionBeanLocal.retrieveAllListings();
        }
    }

    public void viewListingDetails(ActionEvent event) throws IOException {
        Long listingIdToView = (Long) event.getComponent().getAttributes().get("listingId");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("listingIdToView", listingIdToView);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("backMode", "filterListingByTags");
        FacesContext.getCurrentInstance().getExternalContext().redirect("viewListingDetails.xhtml");
    }

    public String getCondition() {
        return condition;
    }

    public List<SelectItem> getSelectItems() {
        return selectItems;
    }

    public List<Long> getSelectedTagIds() {
        return selectedTagIds;
    }

    public ViewListingManagedBean getViewListingManagedBean() {
        return viewListingManagedBean;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setSelectItems(List<SelectItem> selectItems) {
        this.selectItems = selectItems;
    }

    public void setSelectedTagIds(List<Long> selectedTagIds) {
        this.selectedTagIds = selectedTagIds;
    }

    public void setViewListingManagedBean(ViewListingManagedBean viewListingManagedBean) {
        this.viewListingManagedBean = viewListingManagedBean;
    }

    public List<Listing> getListings() {
        return listings;
    }

    public void setListings(List<Listing> listings) {
        this.listings = listings;
    }
}
