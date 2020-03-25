/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

import entity.BillingDetail;

/**
 *
 * @author shizhan
 */
public class BillingDetailRetrieveAllByCustomerRsp {
    BillingDetail billingDetail;

    public BillingDetail getBillingDetail() {
        return billingDetail;
    }

    public void setBillingDetail(BillingDetail billingDetail) {
        this.billingDetail = billingDetail;
    }

    public BillingDetailRetrieveAllByCustomerRsp() {
    }

    public BillingDetailRetrieveAllByCustomerRsp(BillingDetail billingDetail) {
        this.billingDetail = billingDetail;
    }
    
    
}
