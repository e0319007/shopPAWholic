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
public class OrderRetrieveByIdRsp {
    private OrderEntity orderEntity;

    public OrderRetrieveByIdRsp(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }

    public OrderRetrieveByIdRsp() {
    }

    public OrderEntity getOrderEntity() {
        return orderEntity;
    }

    public void setOrderEntity(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }
    
}