/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import entity.DeliveryDetail;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author Joanna Ng
 */
@Named(value = "viewDeliveryDetailManagedBean")
@RequestScoped
public class ViewDeliveryDetailManagedBean implements Serializable {

    private DeliveryDetail deliveryDetailToView;
    
    /**
     * Creates a new instance of ViewDeliveryDetailManagedBean
     */
    public ViewDeliveryDetailManagedBean() {
        deliveryDetailToView = new DeliveryDetail();
    }
    
    
    @PostConstruct
    public void postConstruct() {
        
    }
    
     public void back(ActionEvent event) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("DeliveryDetailIdToView", deliveryDetailToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect("ViewDeliveryDetails.xhtml");
    }

    /**
     * @return the deliveryDetailToView
     */
    public DeliveryDetail getDeliveryDetailToView() {
        return deliveryDetailToView;
    }

    /**
     * @param deliveryDetailToView the deliveryDetailToView to set
     */
    public void setDeliveryDetailToView(DeliveryDetail deliveryDetailToView) {
        this.deliveryDetailToView = deliveryDetailToView;
    }

    
}
