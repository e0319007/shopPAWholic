package jsf.managedBean;

import ejb.session.stateless.CategorySessionBeanLocal;
import ejb.session.stateless.ListingSessionBeanLocal;
import ejb.session.stateless.OrderSessionBeanLocal;
import ejb.session.stateless.ReviewSessionBeanLocal;
import ejb.session.stateless.TagSessionBeanLocal;
import entity.Listing;
import entity.Tag;
import entity.Category;
import entity.OrderEntity;
import entity.Review;
import entity.Seller;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.faces.event.ActionEvent;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.inject.Inject;
import util.exception.CreateNewListingException;
import util.exception.InputDataValidationException;
import util.exception.ListingNotFoundException;
import util.exception.ListingSkuCodeExistException;

/**
 *
 * @author EileenLeong
 */
@Named(value = "listingManagementManagedBean")
@ViewScoped

public class ListingManagementManagedBean implements Serializable {

    @EJB(name = "OrderSessionBeanLocal")
    private OrderSessionBeanLocal orderSessionBeanLocal;

    @EJB(name = "ReviewSessionBeanLocal")
    private ReviewSessionBeanLocal reviewSessionBeanLocal;

    @EJB(name = "TagSessionBeanLocal")
    private TagSessionBeanLocal tagSessionBeanLocal;

    @EJB(name = "CategorySessionBeanLocal")
    private CategorySessionBeanLocal categorySessionBeanLocal;

    @EJB(name = "ListingSessionBeanLocal")
    private ListingSessionBeanLocal listingSessionBeanLocal;

    //@Inject
    //private ViewListingManagedBean viewListingManagedBean;

    //private String name;
    //private String description;
    //private String skuCode;
    //private BigDecimal unitPrice;
    //private List<String> pictures;
    //private Integer quantityAtHand;

    private List<Listing> listings;
    private List<Listing> filteredListings;

    private Listing newListing;
    private Long categoryIdNew;
    private List<Long> tagIdsNew;
    private List<Category> categories;
    private List<Tag> tags;
    
    private Listing selectedListingToView;
    private Listing selectedListingToUpdate;
    private Long categoryIdUpdate;
    private List<Long> tagIdsUpdate;

    private List<Review> reviews;
    private List<OrderEntity> orders;

    private Seller seller;
    


     /**
     * Creates a new instance of ProductManagementManagedBean
     */
    public ListingManagementManagedBean() {
        newListing = new Listing();
        //List<String> pictures = new ArrayList<>();
    }
    
    @PostConstruct
    public void postConstruct() {
        //setListings(listingSessionBeanLocal.retrieveAllListings());
        //setCategories(categorySessionBeanLocal.retrieveAllLeafCategories());
        //setTags(tagSessionBeanLocal.retrieveAllTags());
        //setReviews(reviewSessionBeanLocal.retrieveAllReviews());
        //setOrders(orderSessionBeanLocal.retrieveAllOrders());
        listings = listingSessionBeanLocal.retrieveAllListings();
        categories = categorySessionBeanLocal.retrieveAllLeafCategories();
        tags = tagSessionBeanLocal.retrieveAllTags();
        //reviews = reviewSessionBeanLocal.retrieveAllReviews();
        Seller seller = (Seller) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentSellerEntity");
    }

   

    public void viewListingDetails(ActionEvent event) throws IOException {
        Long listingIdToView = (Long) event.getComponent().getAttributes().get("listingId");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("listingIdToView", listingIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect("viewListingDetails.xhtml");
    }

    public void createNewListing(ActionEvent event) {
        if (categoryIdNew == 0) {
            categoryIdNew = null;
        }
        try {
            Listing l = listingSessionBeanLocal.createNewListing(newListing, categoryIdNew, tagIdsNew, seller.getUserId());
            listings.add(l);
            //Listing l = new Listing(skuCode, name, description, unitPrice, pictures, getQuantityAtHand());
            //listingSessionBeanLocal.createNewListing(l, getCategoryIdNew(), getTagIdsNew());
            //listingSessionBeanLocal.createNewListing(getNewListing(), getCategoryIdNew(), getTagIdsNew(), getPictures());
            //getListings().add(l);

            if (filteredListings != null) {
                filteredListings.add(l);
            }

            newListing = new Listing();
            categoryIdNew = null;
            tagIdsNew = null;
            //setNewListing(new Listing());
            //setCategoryIdNew(null);
            //setTagIdsNew(null); 

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New listing created successfully! (Listing Id: " + l.getListingId() + ")", null));

        } catch (InputDataValidationException | CreateNewListingException | ListingSkuCodeExistException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new listing: " + ex.getMessage(), null));
        }
    }

    public void deleteListing(ActionEvent event) {
        try {
            Listing listingToDelete = (Listing) event.getComponent().getAttributes().get("listingToDelete");
            listingSessionBeanLocal.deleteListing(listingToDelete.getListingId());

            listings.remove(listingToDelete);

            if (filteredListings != null) {
                filteredListings.remove(listingToDelete);
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Listing deleted successfully", null));
        } catch (ListingNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting listing: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public void updateListing(ActionEvent event) {
        if (categoryIdUpdate == 0) {
            categoryIdUpdate = null;
        }

        try {
            listingSessionBeanLocal.updateListing(selectedListingToUpdate, categoryIdUpdate, tagIdsUpdate);

            for (Category c : categories) {
                if (c.getCategoryId().equals(categoryIdUpdate)) {
                    selectedListingToUpdate.setCategory(c);
                    break;
                }
            }

            selectedListingToUpdate.getTags().clear();

            for (Tag t : tags) {
                if (tagIdsUpdate.contains(t.getTagId())) {
                    selectedListingToUpdate.getTags().add(t);
                }
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Listing updated successfully", null));
        } catch (ListingNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating listing: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public void doUpdateListing(ActionEvent event) {
        selectedListingToUpdate = (Listing) event.getComponent().getAttributes().get("listingToUpdate");

        categoryIdUpdate = selectedListingToUpdate.getCategory().getCategoryId();
        tagIdsUpdate = new ArrayList<>();

        for (Tag tag : selectedListingToUpdate.getTags()) {
            tagIdsUpdate.add(tag.getTagId());
        }
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

    /**
     * @return the filteredListings
     */
    public List<Listing> getFilteredListings() {
        return filteredListings;
    }

    /**
     * @param filteredListings the filteredListings to set
     */
    public void setFilteredListings(List<Listing> filteredListings) {
        this.filteredListings = filteredListings;
    }

    /**
     * @return the newListing
     */
    public Listing getNewListing() {
        return newListing;
    }

    /**
     * @param newListing the newListing to set
     */
    public void setNewListing(Listing newListing) {
        this.newListing = newListing;
    }

    /**
     * @return the categoryIdNew
     */
    public Long getCategoryIdNew() {
        return categoryIdNew;
    }

    /**
     * @param categoryIdNew the categoryIdNew to set
     */
    public void setCategoryIdNew(Long categoryIdNew) {
        this.categoryIdNew = categoryIdNew;
    }

    /**
     * @return the tagIdsNew
     */
    public List<Long> getTagIdsNew() {
        return tagIdsNew;
    }

    /**
     * @param tagIdsNew the tagIdsNew to set
     */
    public void setTagIdsNew(List<Long> tagIdsNew) {
        this.tagIdsNew = tagIdsNew;
    }

    /**
     * @return the categories
     */
    public List<Category> getCategories() {
        return categories;
    }

    /**
     * @param categories the categories to set
     */
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    /**
     * @return the tags
     */
    public List<Tag> getTags() {
        return tags;
    }

    /**
     * @param tags the tags to set
     */
    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    /**
     * @return the selectedListingToUpdate
     */
    public Listing getSelectedListingToUpdate() {
        return selectedListingToUpdate;
    }

    /**
     * @param selectedListingToUpdate the selectedListingToUpdate to set
     */
    public void setSelectedListingToUpdate(Listing selectedListingToUpdate) {
        this.selectedListingToUpdate = selectedListingToUpdate;
    }

    /**
     * @return the categoryIdUpdate
     */
    public Long getCategoryIdUpdate() {
        return categoryIdUpdate;
    }

    /**
     * @param categoryIdUpdate the categoryIdUpdate to set
     */
    public void setCategoryIdUpdate(Long categoryIdUpdate) {
        this.categoryIdUpdate = categoryIdUpdate;
    }

    /**
     * @return the tagIdsUpdate
     */
    public List<Long> getTagIdsUpdate() {
        return tagIdsUpdate;
    }

    /**
     * @param tagIdsUpdate the tagIdsUpdate to set
     */
    public void setTagIdsUpdate(List<Long> tagIdsUpdate) {
        this.tagIdsUpdate = tagIdsUpdate;
    }

    /**
     * @return the viewListingManagedBean
     */
    /*public ViewListingManagedBean getViewListingManagedBean() {
        return viewListingManagedBean;
    }*/

    /**
     * @param viewListingManagedBean the viewListingManagedBean to set
     */
    /*public void setViewListingManagedBean(ViewListingManagedBean viewListingManagedBean) {
        this.viewListingManagedBean = viewListingManagedBean;
    }*/

    /**
     * @return the reviews
     */
    public List<Review> getReviews() {
        return reviews;
    }

    /**
     * @param reviews the reviews to set
     */
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    /**
     * @return the orders
     */
    public List<OrderEntity> getOrders() {
        return orders;
    }

    /**
     * @param orders the orders to set
     */
    public void setOrders(List<OrderEntity> orders) {
        this.orders = orders;
    }

    public Listing getSelectedListingToView() {
        return selectedListingToView;
    }

    public void setSelectedListingToView(Listing selectedListingToView) {
        this.selectedListingToView = selectedListingToView;
    }

    

}
