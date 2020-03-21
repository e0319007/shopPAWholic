/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.DeliveryDetailSessionBean;
import ejb.session.stateless.ListingSessionBeanLocal;
import ejb.session.stateless.OrderSessionBeanLocal;
import entity.Listing;
import entity.OrderEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import util.enumeration.OrderStatus;
import util.exception.CreateNewOrderException;
import util.exception.InputDataValidationException;
import util.exception.OrderNotFoundException;

/**
 *
 * @author EileenLeong
 */
@Named(value = "orderManagedBean")
@ViewScoped
public class OrderManagedBean implements Serializable{

    @EJB(name = "ListingSessionBeanLocal")
    private ListingSessionBeanLocal listingSessionBeanLocal;

    @EJB(name = "DeliveryDetailSessionBeanLocal")
    private DeliveryDetailSessionBean deliveryDetailSessionBeanLocal;

    @EJB(name = "OrderSessionBeanLocal")
    private OrderSessionBeanLocal orderSessionBeanLocal;
    

    private BigDecimal totalPrice;
    private Date orderDate;
    private OrderStatus orderStatus;
    
    private OrderEntity newOrder; 
    private List<OrderEntity> orders;
    private Long sellerIdNew;
    private List<Long> listingIdsNew; 
    private Long customerIdNew; 
    private Long deliveryDetailIdNew; 
    private Long bilingDetailIdNew; 
    private List<Listing> listings; 
    private String ccNum;
    
    
    /**
     * Creates a new instance of OrderManagedBean
     */
    public OrderManagedBean() {
        newOrder = new OrderEntity();
    }
    
    @PostConstruct 
    public void postConstruct() {
        setOrders(orderSessionBeanLocal.retrieveAllOrders());
        setListings(listingSessionBeanLocal.retrieveAllListings());
        
    }
    
  
    
    public void changeOrderStatus(ActionEvent event) {
        OrderEntity orderStatusToChange = (OrderEntity) event.getComponent().getAttributes().get("orderStatusToChange");
      
        orderSessionBeanLocal.changeOrderStatus(orderStatusToChange.getOrderStatus(), getSellerIdNew());
       
    }
           

    /**
     * @return the totalPrice
     */
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    /**
     * @param totalPrice the totalPrice to set
     */
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * @return the orderDate
     */
    public Date getOrderDate() {
        return orderDate;
    }

    /**
     * @param orderDate the orderDate to set
     */
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * @return the orderStatus
     */
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    /**
     * @param orderStatus the orderStatus to set
     */
    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * @return the newOrder
     */
    public OrderEntity getNewOrder() {
        return newOrder;
    }

    /**
     * @param newOrder the newOrder to set
     */
    public void setNewOrder(OrderEntity newOrder) {
        this.newOrder = newOrder;
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

    /**
     * @return the sellerIdNew
     */
    public Long getSellerIdNew() {
        return sellerIdNew;
    }

    /**
     * @param sellerIdNew the sellerIdNew to set
     */
    public void setSellerIdNew(Long sellerIdNew) {
        this.sellerIdNew = sellerIdNew;
    }

    /**
     * @return the listingIdsNew
     */
    public List<Long> getListingIdsNew() {
        return listingIdsNew;
    }

    /**
     * @param listingIdsNew the listingIdsNew to set
     */
    public void setListingIdsNew(List<Long> listingIdsNew) {
        this.listingIdsNew = listingIdsNew;
    }

    /**
     * @return the customerIdNew
     */
    public Long getCustomerIdNew() {
        return customerIdNew;
    }

    /**
     * @param customerIdNew the customerIdNew to set
     */
    public void setCustomerIdNew(Long customerIdNew) {
        this.customerIdNew = customerIdNew;
    }

    /**
     * @return the deliveryDetailIdNew
     */
    public Long getDeliveryDetailIdNew() {
        return deliveryDetailIdNew;
    }

    /**
     * @param deliveryDetailIdNew the deliveryDetailIdNew to set
     */
    public void setDeliveryDetailIdNew(Long deliveryDetailIdNew) {
        this.deliveryDetailIdNew = deliveryDetailIdNew;
    }

    /**
     * @return the bilingDetailIdNew
     */
    public Long getBilingDetailIdNew() {
        return bilingDetailIdNew;
    }

    /**
     * @param bilingDetailIdNew the bilingDetailIdNew to set
     */
    public void setBilingDetailIdNew(Long bilingDetailIdNew) {
        this.bilingDetailIdNew = bilingDetailIdNew;
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

    /**
     * @return the ccNum
     */
    public String getCcNum() {
        return ccNum;
    }

    /**
     * @param ccNum the ccNum to set
     */
    public void setCcNum(String ccNum) {
        this.ccNum = ccNum;
    }
    
}
