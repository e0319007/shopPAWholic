/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.BillingDetail;
import java.util.List;
import javax.ejb.Local;
import util.exception.BillingDetailNotFoundException;
import util.exception.CreateNewBillingDetailException;
import util.exception.InputDataValidationException;

/**
 *
 * @author Joanna Ng
 */
@Local
public interface BillingDetailSessionBeanLocal {

    public BillingDetail createNewBillingDetail(BillingDetail billingDetail) throws CreateNewBillingDetailException, InputDataValidationException;

    public void updateBillingDetail(BillingDetail billingDetail) throws InputDataValidationException;

    public BillingDetail getBillingDetailById(Long id) throws BillingDetailNotFoundException;
    
    public void deleteBillingDetail(Long id) throws BillingDetailNotFoundException;
    
    public List<BillingDetail> retrieveBillingDetailByCustomer(Long customerId);
    
    public List<BillingDetail> retrieveBillingDetailBySeller(Long sellerId);
}
