/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.OrderSessionBeanLocal;
import ejb.session.stateless.UserSessionBeanLocal;
import entity.Customer;
import entity.Listing;
import entity.OrderEntity;
import entity.Seller;
import entity.User;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import ws.datamodel.OrderCreateNewReq;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import util.exception.CreateNewOrderException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import ws.datamodel.ErrorRsp;
import ws.datamodel.OrderCreateNewRsp;
import ws.datamodel.OrderRetrieveAllRsp;
import ws.datamodel.OrderUpdateOrderReq;
import java.util.Date;
/**
 *
 * @author shizhan
 */

@Path("Order")
public class OrderEntityResource {
    @Context
    private UriInfo context;
    private final SessionBeanLookup sessionBeanLookup;
    private final OrderSessionBeanLocal orderSessionBeanLocal;
    private final UserSessionBeanLocal userSessionBeanLocal;

    public OrderEntityResource() {
        sessionBeanLookup = new SessionBeanLookup();
        orderSessionBeanLocal = sessionBeanLookup.lookupOrderSessionBeanLocal();
        userSessionBeanLocal = sessionBeanLookup.lookupUserSessionBeanLocal();
    }
    
    
    
    //createorder, change order status, retrieve order for customer/ seller.
    //when creating order, delivery detail and billing detail attributes to be passed in.
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewOrder(OrderCreateNewReq orderCreateNewReq) {
        if(orderCreateNewReq != null) {
            try {
                User user = getUser(orderCreateNewReq.getEmail(), orderCreateNewReq.getPassword(), "OrderEntityResource.createNewOrder()");
                if (user instanceof Customer == false) throw new InvalidLoginCredentialException();
                orderCreateNewReq.getOrderEntity().setOrderDate(new Date());
                OrderEntity orderEntity = orderSessionBeanLocal.createNewOrder(orderCreateNewReq.getOrderEntity(), orderCreateNewReq.getDeliveryDetailId(), 
                                                                                orderCreateNewReq.getCcNum(), user.getUserId(), orderCreateNewReq.getListings(), 
                                                                                orderCreateNewReq.getSeller().getUserId());
                
                return Response.status(Response.Status.OK).entity(new OrderCreateNewRsp(orderEntity.getOrderId())).build();
            } catch (InvalidLoginCredentialException ex) {
                 ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                return Response.status(Status.UNAUTHORIZED).entity(errorRsp).build();
            } catch (CreateNewOrderException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
            } catch (InputDataValidationException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
            }
            catch(Exception ex){
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        } else {
           ErrorRsp errorRsp = new ErrorRsp("Invalid create new product request");
           return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build(); 
        }
    }
    
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllOrderByUser(@QueryParam("email") String email, 
                                        @QueryParam("password") String password) {
        try {
            User user = getUser(email, password, "OrderEntityResource.retrieveAllOrderByCustomerId()");
            
            List<OrderEntity> orders;
            if (user instanceof Customer) orders = orderSessionBeanLocal.retrieveOrderByCustomerId(user.getUserId());
            else orders = orderSessionBeanLocal.retrieveOrderBySellerId(user.getUserId());
            System.out.println("***************** ORDERS SIZE: " + orders.size());
            System.out.println("***************** LISTING SIZE: " + orders.get(0).getListings());
            for(OrderEntity o:orders) {
                o.getCustomer().getOrders().clear();
                o.getSeller().getOrders().clear();
                o.getSeller().getListings().clear();
                o.getSeller().getAdvertisements().clear();
                o.getSeller().getBillingDetails().clear();
                o.getSeller().getEvents().clear();
                o.getSeller().getOrders().clear();
                o.getCustomer().getBillingDetails().clear();
                o.getCustomer().getReviews().clear();
                o.getCustomer().getComments().clear();
                o.getCustomer().getForumPosts().clear();
                o.getCustomer().getOrders().clear();
                o.getCustomer().setCart(null);
                o.getBillingDetail().setOrder(null);
                o.getBillingDetail().setCustomer(null);
                o.getBillingDetail().setSeller(null);
                o.getBillingDetail().setAdvertisement(null);
                for(Listing l: o.getListings()) {
                    l.setCategory(null);
                    l.setSeller(null);
                    l.getReviews().clear();
                    l.getTags().clear();
                    l.getOrders().clear();
                }
                
            }
            return Response.status(Status.OK).entity(new OrderRetrieveAllRsp(orders)).build();
        } catch (InvalidLoginCredentialException ex) {
                 ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                return Response.status(Status.UNAUTHORIZED).entity(errorRsp).build();
        } catch(Exception ex){
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @POST
    @Path("changeOrderStatusByCustomer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response changeOrderStatusByCustomer(OrderUpdateOrderReq orderUpdateOrderReq) {
        try {
            User user = getUser(orderUpdateOrderReq.getEmail(), orderUpdateOrderReq.getPassword(), "OrderEntityResource.changeOrderStatusByCustomer()");
            if (user instanceof Customer)
                orderSessionBeanLocal.changeOrderStatus(orderUpdateOrderReq.getOrder().getOrderStatus(), orderUpdateOrderReq.getOrder().getOrderId());
            else throw new InvalidLoginCredentialException();
            return Response.status(Status.OK).entity(orderUpdateOrderReq.getOrder().getOrderId()).build();
        } catch (InvalidLoginCredentialException ex) {
                 ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                return Response.status(Status.UNAUTHORIZED).entity(errorRsp).build();
        } catch(Exception ex){
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    @POST
    @Path("changeOrderStatusBySeller")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response changeOrderStatusBySeller(OrderUpdateOrderReq orderUpdateOrderReq) {
        try {
            User user = getUser(orderUpdateOrderReq.getEmail(), orderUpdateOrderReq.getPassword(), "OrderEntityResource.changeOrderStatusBySeller()");
            if (user instanceof Seller)
                orderSessionBeanLocal.changeOrderStatus(orderUpdateOrderReq.getOrder().getOrderStatus(), orderUpdateOrderReq.getOrder().getOrderId());
            else throw new InvalidLoginCredentialException();
            return Response.status(Status.OK).entity(orderUpdateOrderReq.getOrder().getOrderId()).build();
        } catch (InvalidLoginCredentialException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
           return Response.status(Status.UNAUTHORIZED).entity(errorRsp).build();
        } catch(Exception ex){
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    @POST
    @Path("updateDeliveryStatusOfOrder")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateOrder(OrderUpdateOrderReq orderUpdateOrderReq) {
        try {
            User user = getUser(orderUpdateOrderReq.getEmail(), orderUpdateOrderReq.getPassword(), "OrderEntityResource.changeOrderStatusBySeller()");
            if (user instanceof Seller)
                orderSessionBeanLocal.updateDeliveryStatusOfOrder(orderUpdateOrderReq.getOrder());
            else throw new InvalidLoginCredentialException();
            System.out.println("Status list" + orderUpdateOrderReq.getOrder().getDeliveryDetail().getStatusLists());
            return Response.status(Status.OK).entity(orderUpdateOrderReq.getOrder().getOrderId()).build();
        } catch (InvalidLoginCredentialException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.UNAUTHORIZED).entity(errorRsp).build();
        }
    }
    
    public User getUser(String email, String password, String methodName) throws InvalidLoginCredentialException {
        User user = userSessionBeanLocal.userLogin(email, password);
        System.out.println("********** " + methodName + ": User " + user.getEmail()+ " login remotely via web service");
        return user;
    } 
}
