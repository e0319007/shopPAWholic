package jsf.managedBean;

import ejb.session.stateless.CategorySessionBeanLocal;
import ejb.session.stateless.ListingSessionBeanLocal;
import ejb.session.stateless.OrderSessionBeanLocal;
import ejb.session.stateless.ReviewSessionBeanLocal;
import ejb.session.stateless.TagSessionBeanLocal;
import ejb.session.stateless.UserSessionBeanLocal;
import entity.Listing;
import entity.Tag;
import entity.Category;
import entity.OrderEntity;
import entity.Review;
import entity.Seller;
import entity.User;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.event.ActionEvent;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import util.exception.CreateNewListingException;
import util.exception.InputDataValidationException;
import util.exception.ListingNotFoundException;
import util.exception.ListingSkuCodeExistException;

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

    @EJB(name = "UserSessionBeanLocal")
    private UserSessionBeanLocal userSessionBeanLocal;

    private List<Listing> listings;
    private List<Listing> filteredListings;

    private Listing newListing;
    private Long categoryIdNew;
    private List<Long> tagIdsNew;
    private List<Category> categories;
    private List<Tag> tags;

    //view
    private Listing selectedListingToView;

    //update
    private Listing selectedListingToUpdate;
    private Long categoryIdUpdate;
    private List<Long> tagIdsUpdate;

    private List<Review> reviews;
    private List<OrderEntity> orders;

    private Seller seller;

    //fileUpload
    private UploadedFile file;
    private String imagePath;

    public ListingManagementManagedBean() {
        newListing = new Listing();
    }

    public void handleFileUpload(FileUploadEvent event) {
        User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
        try {
            String destination = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("alternatedocroot_1") + System.getProperty("file.separator");
            String secDest = "Seller"
                    + System.getProperty("file.separator")
                    + user.getUserId()
                    + System.getProperty("file.separator")
                    + "Listing"
                    + System.getProperty("file.separator");
            File newPath = new File(destination + secDest);
            // advertisementID
            newPath.mkdirs();
            System.err.println("********** FileUploadView.handleFileUpload(): File name: " + event.getFile().getFileName());
            System.err.println("********** FileUploadView.handleFileUpload(): newFilePath: " + newPath);

            File file = new File(newPath + "/" + event.getFile().getFileName());
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            //Creates a FileOutputStream to write to the file represented by the specified file

            int a;
            int BUFFER_SIZE = 8192;
            byte[] buffer = new byte[BUFFER_SIZE];

            InputStream inputStream = event.getFile().getInputstream();
            //This getInputStream() method of the uploadedFile represents the file content
            imagePath = secDest + event.getFile().getFileName();

            while (true) {
                a = inputStream.read(buffer);

                if (a < 0) {
                    break;
                }
                fileOutputStream.write(buffer, 0, a);
                //write a bytes from the specified bytes array starting at offset 0 to this FileOutputStream
                fileOutputStream.flush();
            }
            fileOutputStream.close();
            inputStream.close();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "File uploaded successfully", ""));
        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "File upload error: " + ex.getMessage(), ""));
        }
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    @PostConstruct
    public void postConstruct() {
        //setListings(listingSessionBeanLocal.retrieveAllListings());
        //setCategories(categorySessionBeanLocal.retrieveAllLeafCategories());
        //setTags(tagSessionBeanLocal.retrieveAllTags());
        //setReviews(reviewSessionBeanLocal.retrieveAllReviews());
        //setOrders(orderSessionBeanLocal.retrieveAllOrders());
        listings = listingSessionBeanLocal.retrieveAllListings();
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
        try {
            Listing l = listingSessionBeanLocal.createNewListing(newListing, categoryIdNew, tagIdsNew, seller.getUserId());
            System.out.println("############################# new Listing: " + newListing);
            newListing.setListDate(new Date(System.currentTimeMillis()));
            newListing.setPicture(imagePath);
            System.out.println("############################# categoryIdNew: " + categoryIdNew);
            System.out.println("############################# tagIdsNew: " + tagIdsNew);
            System.out.println("############################# seller: " + seller.getUserId());
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
