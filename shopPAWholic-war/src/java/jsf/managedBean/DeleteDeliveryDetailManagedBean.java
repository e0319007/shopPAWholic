/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.DeliveryDetailSessionBeanLocal;
import entity.DeliveryDetail;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import util.exception.DeliveryDetailNotFoundException;

/**
 *
 * @author Joanna Ng
 */
@Named(value = "deleteDeliveryDetailManagedBean")
@ViewScoped
public class DeleteDeliveryDetailManagedBean implements Serializable{

    @EJB(name = "DeliveryDetailSessionBeanLocal")
    private DeliveryDetailSessionBeanLocal deliveryDetailSessionBeanLocal;

    private Long deliveryDetailIdToDelete;
    private DeliveryDetail deliveryDetailToDelete;
    
    /**
     * Creates a new instance of DeleteDeliveryDetailManagedBean
     */
    public DeleteDeliveryDetailManagedBean() {
    }
    
    @PostConstruct
    public void postConstruct() {
        deliveryDetailIdToDelete = (Long)FacesContext.getCurrentInstance().getExternalContext().getFlash().get("deliveryDetailIdToDelete");
        
        try {
            setDeliveryDetailToDelete(deliveryDetailSessionBeanLocal.retrieveDeliveryDetailById(deliveryDetailIdToDelete));
        } catch (DeliveryDetailNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving the product details: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
    
    public void deleteDeliveryDetail() {
        try {
            deliveryDetailSessionBeanLocal.deleteDeliveryDetail(deliveryDetailIdToDelete);   
            deliveryDetailIdToDelete = null;
            setDeliveryDetailToDelete(null);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Delivery Detail deleted successfully", null));
            
        } catch (DeliveryDetailNotFoundException ex){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting the product details: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
        
    }

    /**
     * @return the deliveryDetailToDelete
     */
    public DeliveryDetail getDeliveryDetailToDelete() {
        return deliveryDetailToDelete;
    }

    /**
     * @param deliveryDetailToDelete the deliveryDetailToDelete to set
     */
    public void setDeliveryDetailToDelete(DeliveryDetail deliveryDetailToDelete) {
        this.deliveryDetailToDelete = deliveryDetailToDelete;
    }
    
    
    
}
