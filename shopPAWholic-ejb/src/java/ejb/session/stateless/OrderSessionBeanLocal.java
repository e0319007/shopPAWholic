/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Order;
import java.util.List;
import javax.ejb.Local;
import util.exception.CreateNewOrderException;
import util.exception.InputDataValidationException;
import util.exception.OrderNotFoundException;

/**
 *
 * @author EileenLeong
 */
@Local
public interface OrderSessionBeanLocal {
    
    public Order createNewOrder (Order newOrder) throws CreateNewOrderException, InputDataValidationException;

    public Order getOrderById(Long orderId) throws OrderNotFoundException;
    
    public List<Order> retrieveAllOrders();
}
