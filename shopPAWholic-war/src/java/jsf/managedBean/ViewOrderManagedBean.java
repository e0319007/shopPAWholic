/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import POJO.CheckoutListingPOJO;
import entity.OrderEntity;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author EileenLeong
 */
@Named(value = "viewOrderManagedBean")
@ViewScoped
public class ViewOrderManagedBean implements Serializable{
    
    
    private OrderEntity orderToView;
    //private List<CheckoutListingPOJO> checkoutList;
    /**
     * Creates a new instance of ViewOrderManagedBean
     */
    public ViewOrderManagedBean() {
        orderToView = new OrderEntity();
    }
    
    @PostConstruct 
    public void postConstruct() {
    
    }

    /**
     * @return the orderToView
     */
    public OrderEntity getOrderToView() {
        return orderToView;
    }

    /**
     * @param orderToView the orderToView to set
     */
    public void setOrderToView(OrderEntity orderToView) {
        this.orderToView = orderToView;
    }
    
}
