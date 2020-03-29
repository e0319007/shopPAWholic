/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

import entity.OrderEntity;
import java.util.List;

/**
 *
 * @author shizhan
 */
public class OrderRetrieveAllRsp {
    
    List<OrderEntity> orders;

    public OrderRetrieveAllRsp() {
    }

    public OrderRetrieveAllRsp(List<OrderEntity> orders) {
        this.orders = orders;
    }

    public List<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderEntity> orders) {
        this.orders = orders;
    }
    
    
}
