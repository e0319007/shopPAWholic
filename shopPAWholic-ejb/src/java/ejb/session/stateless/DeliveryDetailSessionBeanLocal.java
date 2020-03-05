/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.DeliveryDetail;
import javax.ejb.Local;
import util.exception.CreateNewDeliveryDetailException;
import util.exception.DeliveryDetailNotFoundException;
import util.exception.InputDataValidationException;

/**
 *
 * @author Joanna Ng
 */
@Local
public interface DeliveryDetailSessionBeanLocal {
    
    public DeliveryDetail createNewDeliveryDetail(DeliveryDetail deliveryDetail) throws CreateNewDeliveryDetailException, InputDataValidationException;
    
    public void updateDeliveryDetail(DeliveryDetail deliveryDetail) throws InputDataValidationException;
    
    public void deleteDeliveryDetail(Long id) throws DeliveryDetailNotFoundException;
    
    public DeliveryDetail retrieveDeliveryDetailById(Long id) throws DeliveryDetailNotFoundException;
}
