/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

import entity.OrderEntity;

/**
 *
 * @author shizhan
 */
public class OrderUpdateOrderReq {
    private OrderEntity order;
    private String email;
    private String password;

    public OrderUpdateOrderReq() {
    }

    public OrderUpdateOrderReq(OrderEntity order, String email, String password) {
        this.order = order;
        this.email = email;
        this.password = password;
    }

    
    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
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
