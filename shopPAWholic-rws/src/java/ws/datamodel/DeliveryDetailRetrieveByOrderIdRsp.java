/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

import entity.DeliveryDetail;

/**
 *
 * @author shizhan
 */
public class DeliveryDetailRetrieveByOrderIdRsp {
    private DeliveryDetail deliveryDetail;

    public DeliveryDetailRetrieveByOrderIdRsp() {
    }

    public DeliveryDetailRetrieveByOrderIdRsp(DeliveryDetail deliveryDetail) {
        this.deliveryDetail = deliveryDetail;
    }

    public DeliveryDetail getDeliveryDetail() {
        return deliveryDetail;
    }

    public void setDeliveryDetail(DeliveryDetail deliveryDetail) {
        this.deliveryDetail = deliveryDetail;
    }
    
}
