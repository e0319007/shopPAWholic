/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

import entity.Customer;
import entity.DeliveryDetail;

/**
 *
 * @author shizhan
 */
public class DeliveryDetailCreateNewReq {
    private DeliveryDetail deliveryDetail;
    private String email;
    private String password;

    public DeliveryDetailCreateNewReq(DeliveryDetail deliveryDetail, String email, String password) {
        this.deliveryDetail = deliveryDetail;
        this.email = email;
        this.password = password;
    }

    public DeliveryDetailCreateNewReq() {
    }

    
    public DeliveryDetail getDeliveryDetail() {
        return deliveryDetail;
    }

    public void setDeliveryDetail(DeliveryDetail deliveryDetail) {
        this.deliveryDetail = deliveryDetail;
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
