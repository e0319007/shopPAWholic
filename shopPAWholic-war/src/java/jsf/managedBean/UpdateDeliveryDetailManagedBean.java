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
import util.exception.DeliveryDetailNotFoundException;
import util.exception.InputDataValidationException;

/**
 *
 * @author Joanna Ng
 */
@Named(value = "updateDeliveryDetailManagedBean")
@ViewScoped
public class UpdateDeliveryDetailManagedBean implements Serializable{

    @EJB(name = "DeliveryDetailSessionBeanLocal")
    private DeliveryDetailSessionBeanLocal deliveryDetailSessionBeanLocal;

    
    private Long deliveryDetailIdToUpdate;
    private DeliveryDetail deliveryDetailToUpdate;
    
    /**
     * Creates a new instance of UpdateDeliveryDetailManagedBean
     */
    public UpdateDeliveryDetailManagedBean() {
    }
    
    @PostConstruct
    public void postConstruct() {
        deliveryDetailIdToUpdate = (Long)FacesContext.getCurrentInstance().getExternalContext().getFlash().get("deliveryDetailIdToUpdate");
    
        try {
            setDeliveryDetailToUpdate(deliveryDetailSessionBeanLocal.retrieveDeliveryDetailById(deliveryDetailIdToUpdate));
        } catch (DeliveryDetailNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving the product details: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
    
    
   
    
    public void updateDeliveryDetail() {
        try {
            deliveryDetailSessionBeanLocal.updateDeliveryDetail(getDeliveryDetailToUpdate());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Delivery Detail updated successfully", null));
        } catch (InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating Delivery Detail: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
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
    
}
