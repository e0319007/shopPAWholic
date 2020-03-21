/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.DeliveryDetailSessionBeanLocal;
import entity.DeliveryDetail;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import util.exception.CreateNewDeliveryDetailException;
import util.exception.InputDataValidationException;

/**
 *
 * @author Joanna Ng
 */
@Named(value = "createDeliveryDetailManagedBean")
@RequestScoped
public class CreateDeliveryDetailManagedBean implements Serializable {

    @EJB(name = "DeliveryDetailSessionBeanLocal")
    private DeliveryDetailSessionBeanLocal deliveryDetailSessionBeanLocal;
    
    private DeliveryDetail newDeliveryDetail;
//    private List<DeliveryDetail> deliveryDetails;
    
    /**
     * Creates a new instance of CreateDeliveryDetailManagedBean
     */
    public CreateDeliveryDetailManagedBean() {
        newDeliveryDetail = new DeliveryDetail();
    }
    
    @PostConstruct
    public void postConstruct() {
        
    }
    
    public void createNewDeliveryDetail() {
        try {
            DeliveryDetail dd = deliveryDetailSessionBeanLocal.createNewDeliveryDetail(getNewDeliveryDetail());
            setNewDeliveryDetail(new DeliveryDetail());
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New Delivery Detail created successfully (Delivery ID: " + dd.getDeliveryDetailId() + ")", null));
            
        } catch (InputDataValidationException | CreateNewDeliveryDetailException ex) {
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new delivery detail: " + ex.getMessage(), null));
        
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

//    /**
//     * @return the deliveryDetails
//     */
//    public List<DeliveryDetail> getDeliveryDetails() {
//        return deliveryDetails;
//    }
//
//    /**
//     * @param deliveryDetails the deliveryDetails to set
//     */
//    public void setDeliveryDetails(List<DeliveryDetail> deliveryDetails) {
//        this.deliveryDetails = deliveryDetails;
//    }
    
    
}
