package jsf.managedBean;

import ejb.session.stateless.CategorySessionBeanLocal;
import ejb.session.stateless.ListingSessionBeanLocal;
import entity.Category;
import entity.Listing;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import util.exception.CategoryNotFoundException;

@Named(value = "filterListingsByCategoryManagedBean")
@ViewScoped
public class FilterListingsByCategoryManagedBean implements Serializable {

    @EJB(name = "ListingSessionBeanLocal")
    private ListingSessionBeanLocal listingSessionBeanLocal;

    @EJB(name = "CategorySessionBeanLocal")
    private CategorySessionBeanLocal categorySessionBeanLocal;

    @Inject
    private ViewListingManagedBean viewListingManagedBean;

    private TreeNode treeNode;
    private TreeNode selectedTreeNode;

    private List<Listing> listings;

    public FilterListingsByCategoryManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        List<Category> categories = categorySessionBeanLocal.retrieveAllCategories();
        treeNode = new DefaultTreeNode("Root", null);

        for (Category category : categories) {
            createTreeNode(category, treeNode);
        }
        
        System.out.println(treeNode);

        Long selectedCategoryId = (Long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("listingFilterCategory");

        System.out.println("################### selectedCategoryId: "+selectedCategoryId);
        if (selectedCategoryId != null) {
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+treeNode.getChildren());
            for (TreeNode tn : treeNode.getChildren()) {
                Category c = (Category) tn.getData();

                if (c.getCategoryId().equals(selectedCategoryId)) {
                    selectedTreeNode = tn;
                                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+selectedTreeNode);

                    
                    break;
                } else {
                    selectedTreeNode = searchTreeNode(selectedCategoryId, tn);
                }
            }
        }

        filterListing();
    }

    public void filterListing() {
        System.out.println("################### selectedTreeNode: "+selectedTreeNode);
        if (selectedTreeNode != null) {
            try {
                Category c = (Category) selectedTreeNode.getData();
                System.out.println(c.getCategoryId());
                listings = listingSessionBeanLocal.filterListingsByCategory(c.getCategoryId());
            } catch (CategoryNotFoundException ex) {
                listings = listingSessionBeanLocal.retrieveAllListings();
            }
        } else {
            listings = listingSessionBeanLocal.retrieveAllListings();
        }
    }

    public void viewListingDetails(ActionEvent event) throws IOException {
        Long listingIdToView = (Long) event.getComponent().getAttributes().get("listingId");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("listingIdToView", listingIdToView);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("backMode", "filterListingsByCategory");
        FacesContext.getCurrentInstance().getExternalContext().redirect("viewListingDetails.xhtml");
    }

    private void createTreeNode(Category category, TreeNode parentTreeNode) {
        TreeNode treeNode = new DefaultTreeNode(category, parentTreeNode);
    }

    private TreeNode searchTreeNode(Long selectedCategoryId, TreeNode treeNode) {
        for (TreeNode tn : treeNode.getChildren()) {
            Category c = (Category) tn.getData();

            if (c.getCategoryId().equals(selectedCategoryId)) {
                return tn;
            } else {
                return searchTreeNode(selectedCategoryId, tn);
            }
        }
        return null;
    }

    public ViewListingManagedBean getViewListingManagedBean() {
        return viewListingManagedBean;
    }

    public void setViewListingManagedBean(ViewListingManagedBean viewListingManagedBean) {
        this.viewListingManagedBean = viewListingManagedBean;
    }

    public TreeNode getTreeNode() {
        return treeNode;
    }

    public void setTreeNode(TreeNode treeNode) {
        this.treeNode = treeNode;
    }

    public TreeNode getSelectedTreeNode() {
        return selectedTreeNode;
    }

    public void setSelectedTreeNode(TreeNode selectedTreeNode) {
        this.selectedTreeNode = selectedTreeNode;
        if (selectedTreeNode != null) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listingFilterCategory", ((Category) selectedTreeNode.getData()).getCategoryId());
        }
    }

    public List<Listing> getListings() {
        return listings;
    }

    public void setListings(List<Listing> listings) {
        this.listings = listings;
    }
}
