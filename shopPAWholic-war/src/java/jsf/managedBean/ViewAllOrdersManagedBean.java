/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.OrderSessionBeanLocal;
import entity.OrderEntity;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author EileenLeong
 */
@Named(value = "viewAllOrdersManagedBean")
@ViewScoped

public class ViewAllOrdersManagedBean implements Serializable {

    @EJB(name = "OrderSessionBeanLocal")
    private OrderSessionBeanLocal orderSessionBeanLocal;

    private List<OrderEntity> orders;
    /**
     * Creates a new instance of ViewAllOrdersManagedBean
     */
    public ViewAllOrdersManagedBean() {
    }
    
    @PostConstruct
    public void postConstruct()
    {
        setOrders(orderSessionBeanLocal.retrieveAllOrders());
    }
    
    public void viewOrderDetails(ActionEvent event) throws IOException
    {
        Long orderIdToView = (Long)event.getComponent().getAttributes().get("orderId");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("orderIdToView", orderIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect("viewOrderDetails.xhtml");
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
}
