/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Listing;
import entity.OrderEntity;
import java.util.List;
import javax.ejb.Local;
import util.enumeration.OrderStatus;
import util.exception.CreateNewOrderException;
import util.exception.InputDataValidationException;
import util.exception.OrderNotFoundException;

/**
 *
 * @author EileenLeong
 */
@Local
public interface OrderSessionBeanLocal {

    public OrderEntity getOrderById(Long orderId) throws OrderNotFoundException;
    
    public List<OrderEntity> retrieveAllOrders();
    
    public List<OrderEntity> retrieveOrderByCustomerId(Long customerId);

    public List<OrderEntity> retrieveOrderBySellerId(Long sellerId);

    public String changeOrderStatus(OrderStatus os, Long orderId);

    public OrderEntity createNewOrder(OrderEntity newOrder, Long DeliveryDetailId, String ccNum, Long customerId, List<Listing> listings, Long sellerId) throws CreateNewOrderException, InputDataValidationException;

    public void updateDeliveryStatusOfOrder(OrderEntity order);
}
