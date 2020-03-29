/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

import entity.Listing;
import entity.OrderEntity;
import entity.Seller;
import entity.User;
import java.util.List;

/**
 *
 * @author shizhan
 */
public class OrderCreateNewReq {
    private Long deliveryDetailId;
    private String ccNum;
    private OrderEntity orderEntity;
    private String customerEmail;
    private Seller seller;
    private List<Listing> listings;
    private String email;
    private String password;
    
    
    public OrderCreateNewReq(Long deliveryDetailId, String ccNum, OrderEntity orderEntity, String customerEmail, Seller seller, List<Listing> listings, String email, String password) {
        this.deliveryDetailId = deliveryDetailId;
        this.ccNum = ccNum;
        this.orderEntity = orderEntity;
        this.customerEmail = customerEmail;
        this.seller = seller;
        this.listings = listings;
        this.email = email;
        this.password = password;
    }

    public OrderCreateNewReq() {
    }

    public List<Listing> getListings() {
        return listings;
    }

    public void setListings(List<Listing> listings) {
        this.listings = listings;
    }

    public Long getDeliveryDetailId() {
        return deliveryDetailId;
    }

    public void setDeliveryDetailId(Long deliveryDetailId) {
        this.deliveryDetailId = deliveryDetailId;
    }

    public String getCcNum() {
        return ccNum;
    }

    public void setCcNum(String ccNum) {
        this.ccNum = ccNum;
    }

    public OrderEntity getOrderEntity() {
        return orderEntity;
    }

    public void setOrderEntity(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
