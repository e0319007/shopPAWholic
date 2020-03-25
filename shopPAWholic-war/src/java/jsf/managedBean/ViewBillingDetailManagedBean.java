/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import entity.BillingDetail;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Joanna Ng
 */
@Named(value = "viewBillingDetailManagedBean")
@ViewScoped
public class ViewBillingDetailManagedBean implements Serializable {
    
    private BillingDetail billingDetailToView;
    
    /**
     * Creates a new instance of ViewBillingDetailManagedBean
     */
    public ViewBillingDetailManagedBean() {
        billingDetailToView = new BillingDetail();
    }
    
    
     @PostConstruct
    public void postConstruct(){
        
    }
    
    public void foo() {        
    }
//    
//     public void back(ActionEvent event) throws IOException {
//        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("DeliveryDetailIdToView", deliveryDetailIdToUpdate);
//        FacesContext.getCurrentInstance().getExternalContext().redirect("ViewDeliveryDetails.xhtml");
//    }

    /**
     * @return the billingDetailToView
     */
    public BillingDetail getBillingDetailToView() {
        return billingDetailToView;
    }

    /**
     * @param billingDetailToView the billingDetailToView to set
     */
    public void setBillingDetailToView(BillingDetail billingDetailToView) {
        this.billingDetailToView = billingDetailToView;
    }
    
    
}
