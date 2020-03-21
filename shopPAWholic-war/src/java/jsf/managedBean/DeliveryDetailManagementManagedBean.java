/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.DeliveryDetailSessionBeanLocal;
import entity.DeliveryDetail;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import util.exception.CreateNewDeliveryDetailException;
import util.exception.DeliveryDetailNotFoundException;
import util.exception.InputDataValidationException;

/**
 *
 * @author Joanna Ng
 */
@Named(value = "deliveryDetailManagementManagedBean")
@ViewScoped
public class DeliveryDetailManagementManagedBean implements Serializable{
    
    @EJB(name = "DeliveryDetailSessionBeanLocal")
    private DeliveryDetailSessionBeanLocal deliveryDetailSessionBeanLocal;
    
    @Inject
    private ViewDeliveryDetailManagedBean viewDeliveryDetailBean;
    
    private DeliveryDetail newDeliveryDetail;
    private DeliveryDetail deliveryDetailToUpdate;

    /**
     * Creates a new instance of DeliveryDetailManagementManagedBean
     */
    public DeliveryDetailManagementManagedBean() {
        newDeliveryDetail = new DeliveryDetail();
    }
    
    @PostConstruct
    public void postConstruct() {
        
    }
    
    public void viewDeliveryDetail(ActionEvent event) throws IOException {
        Long deliveryDetailIdToView = (Long)event.getComponent().getAttributes().get("deliveryDetailId");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("deliveryDetailIdToView", deliveryDetailIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect("ViewDeliveryDetails.xhtml");
    }
    
    public void createDeliveryDetail(ActionEvent event) {
          try {
            DeliveryDetail dd = deliveryDetailSessionBeanLocal.createNewDeliveryDetail(getNewDeliveryDetail());
            setNewDeliveryDetail(new DeliveryDetail());
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New Delivery Detail created successfully (Delivery ID: " + dd.getDeliveryDetailId() + ")", null));
            
        } catch (InputDataValidationException | CreateNewDeliveryDetailException ex) {
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new delivery detail: " + ex.getMessage(), null));
        
        }
    }
    
    public void doUpdateDeliveryDetail(ActionEvent event) {
        deliveryDetailToUpdate = (DeliveryDetail)event.getComponent().getAttributes().get("deliveryDetailToUpdate");       
    }
    
    public void updateDeliveryDetail(ActionEvent event) {
        try {
            deliveryDetailSessionBeanLocal.updateDeliveryDetail(getDeliveryDetailToUpdate());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Delivery Detail updated successfully", null));
        } catch (InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating Delivery Detail: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
    
    public void deleteDeliveryDetail(ActionEvent event) {
         try {
            DeliveryDetail deliveryDetailToDelete = (DeliveryDetail)event.getComponent().getAttributes().get("deliveryDetailToDelete");
            deliveryDetailSessionBeanLocal.deleteDeliveryDetail(deliveryDetailToDelete.getDeliveryDetailId());
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Delivery Detail deleted successfully", null));
            
        } catch (DeliveryDetailNotFoundException ex){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting the product details: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
    

    /**
     * @return the newDeliveryDetail
     */
    public DeliveryDetail getNewDeliveryDetail() {
        return newDeliveryDetail;
    }

    /**
     * @param newDeliveryDetail the newDeliveryDetail to set
     */
    public void setNewDeliveryDetail(DeliveryDetail newDeliveryDetail) {
        this.newDeliveryDetail = newDeliveryDetail;
    }

    /**
     * @return the deliveryDetailToUpdate
     */
    public DeliveryDetail getDeliveryDetailToUpdate() {
        return deliveryDetailToUpdate;
    }

    /**
     * @param deliveryDetailToUpdate the deliveryDetailToUpdate to set
     */
    public void setDeliveryDetailToUpdate(DeliveryDetail deliveryDetailToUpdate) {
        this.deliveryDetailToUpdate = deliveryDetailToUpdate;
    }

    /**
     * @return the viewDeliveryDetailBean
     */
    public ViewDeliveryDetailManagedBean getViewDeliveryDetailBean() {
        return viewDeliveryDetailBean;
    }

    /**
     * @param viewDeliveryDetailBean the viewDeliveryDetailBean to set
     */
    public void setViewDeliveryDetailBean(ViewDeliveryDetailManagedBean viewDeliveryDetailBean) {
        this.viewDeliveryDetailBean = viewDeliveryDetailBean;
    }
    
    
    
}
