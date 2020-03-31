/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

/**
 *
 * @author shizhan
 */
public class OrderCreateNewRsp {
    private Long orderEntityId;

    public OrderCreateNewRsp() {
    }

    public OrderCreateNewRsp(Long orderEntityId) {
        this.orderEntityId = orderEntityId;
    }

    public Long getOrderEntityId() {
        return orderEntityId;
    }

    public void setOrderEntityId(Long orderEntityId) {
        this.orderEntityId = orderEntityId;
    }
    
}
