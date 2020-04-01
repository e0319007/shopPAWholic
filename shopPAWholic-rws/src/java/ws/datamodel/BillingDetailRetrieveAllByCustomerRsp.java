/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

import entity.BillingDetail;
import java.util.List;

/**
 *
 * @author shizhan
 */
public class BillingDetailRetrieveAllByCustomerRsp {
    List<BillingDetail> billingDetails;

    public List<BillingDetail> getBillingDetails() {
        return billingDetails;
    }

    public void setBillingDetails(List<BillingDetail> billingDetails) {
        this.billingDetails = billingDetails;
    }

    public BillingDetailRetrieveAllByCustomerRsp() {
    }

    public BillingDetailRetrieveAllByCustomerRsp(List<BillingDetail> billingDetail) {
        this.billingDetails = billingDetail;
    }
    
    
}
