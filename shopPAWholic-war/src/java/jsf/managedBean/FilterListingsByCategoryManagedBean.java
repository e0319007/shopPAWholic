/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author EileenLeong
 */
@Named(value = "filterListingsByCategoryManagedBean")
@ViewScoped
public class FilterListingsByCategoryManagedBean implements Serializable{

    @EJB(name = "ListingSessionBeanLocal")
    private ListingSessionBeanLocal listingSessionBeanLocal;

    @EJB(name = "CategorySessionBeanLocal")
    private CategorySessionBeanLocal categorySessionBeanLocal;
    
    @Inject
    private ViewListingManagedBean viewListingManagedBean;
        
    private TreeNode treeNode;
    private TreeNode selectedTreeNode;
    
    private List<Listing> listings;

    /**
     * Creates a new instance of FilterListingsByCategoryManagedBean
     */
    public FilterListingsByCategoryManagedBean() {
    }
    
    @PostConstruct
    public void postConstruct()
    {
        List<Category> categories = categorySessionBeanLocal.retrieveAllRootCategories();
        setTreeNode(new DefaultTreeNode("Root", null));
        
        for(Category category:categories)
        {
            createTreeNode(category, getTreeNode());
        }
        
        Long selectedCategoryId = (Long)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("listingFilterCategory");
        
        if(selectedCategoryId != null)
        {
            for(TreeNode tn:getTreeNode().getChildren())
            {
                Category c = (Category)tn.getData();

                if(c.getCategoryId().equals(selectedCategoryId))
                {
                    setSelectedTreeNode(tn);
                    break;
                }
                else
                {
                    setSelectedTreeNode(searchTreeNode(selectedCategoryId, tn));
                }            
            }
        }
        
        filterListing();
    }
    
    public void filterListing()
    {
        if(getSelectedTreeNode() != null)
        {               
            try
            {
                Category c = (Category)getSelectedTreeNode().getData();
                setListings(listingSessionBeanLocal.filterListingsByCategory(c.getCategoryId()));
            }
            catch(CategoryNotFoundException ex)
            {
                setListings(listingSessionBeanLocal.retrieveAllListings());
            }
        }
        else
        {
            setListings(listingSessionBeanLocal.retrieveAllListings());
        }
    }
    
    public void viewListingDetails(ActionEvent event) throws IOException
    {
        Long listingIdToView = (Long)event.getComponent().getAttributes().get("listingId");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("listingIdToView", listingIdToView);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("backMode", "filterListingsByCategory");
        FacesContext.getCurrentInstance().getExternalContext().redirect("viewListingDetails.xhtml");
    }
    
    
    
    private void createTreeNode(Category category, TreeNode parentTreeNode)
    {
        TreeNode treeNode = new DefaultTreeNode(category, parentTreeNode);
                
        for(Category c:category.getSubCategories())
        {
            createTreeNode(c, treeNode);
        }
    }
    
    
    
    private TreeNode searchTreeNode(Long selectedCategoryId, TreeNode treeNode)
    {
        for(TreeNode tn:treeNode.getChildren())
        {
            Category c = (Category)tn.getData();
            
            if(c.getCategoryId().equals(selectedCategoryId))
            {
                return tn;
            }
            else
            {
                return searchTreeNode(selectedCategoryId, tn);
            }            
        }
        
        return null;
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
     * @return the treeNode
     */
    public TreeNode getTreeNode() {
        return treeNode;
    }

    /**
     * @param treeNode the treeNode to set
     */
    public void setTreeNode(TreeNode treeNode) {
        this.treeNode = treeNode;
    }

    /**
     * @return the selectedTreeNode
     */
    public TreeNode getSelectedTreeNode() {
        return selectedTreeNode;
    }

    /**
     * @param selectedTreeNode the selectedTreeNode to set
     */
    public void setSelectedTreeNode(TreeNode selectedTreeNode) {
        this.selectedTreeNode = selectedTreeNode;
        if(selectedTreeNode != null)
        {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listingFilterCategory", ((Category)selectedTreeNode.getData()).getCategoryId());
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
}
