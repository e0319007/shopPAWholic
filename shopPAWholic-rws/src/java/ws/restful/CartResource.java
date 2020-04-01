/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.CartSessionBeanLocal;
import ejb.session.stateless.UserSessionBeanLocal;
import entity.Cart;
import entity.Customer;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import util.exception.CartNotFoundException;
import util.exception.InvalidLoginCredentialException;
import ws.datamodel.CartUpdateReq;
import ws.datamodel.ErrorRsp;

/**
 *
 * @author shizhan
 */
@Path("Cart")
public class CartResource { //cart transactions will be managed by service
    
    private final SessionBeanLookup sessionBeanLookup;
    private final CartSessionBeanLocal cartSessionBean;
    private final UserSessionBeanLocal userSessionBean;
    
    private UriInfo context;

    public CartResource() {
        sessionBeanLookup = new SessionBeanLookup();
        cartSessionBean =  sessionBeanLookup.lookupCartSessionBeanLocal();
        userSessionBean = sessionBeanLookup.lookupUserSessionBeanLocal();
    }
    
    @Path("retrieveCart")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveCartByCustomerId(@QueryParam("email") String email, @QueryParam("password") String password) {
        try {
            Customer customer = (Customer) userSessionBean.userLogin(email, password);
            System.out.println("********** CartResource.retrieveCartByCustomerId(): Customer " + customer.getEmail()+ " login remotely via web service");
            Cart cart = cartSessionBean.getCartByCustomerId(customer.getUserId());
            cart.getCustomer().setCart(null);
            return Response.status(Response.Status.OK).entity(cart).build();
        } catch (InvalidLoginCredentialException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.UNAUTHORIZED).entity(errorRsp).build(); 
        } catch(Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build(); 
        }   
    }
    
    @Path("saveCart")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveCartToDatabase(CartUpdateReq cartUpdateReq) {
        try {
            Customer customer = (Customer) userSessionBean.userLogin(cartUpdateReq.getEmail(), cartUpdateReq.getPassword());
            System.out.println("********** CartResource.saveCartToDatabase(): Customer " + customer.getEmail()+ " login remotely via web service");
            cartSessionBean.clearCart(customer.getCart().getCartId());
            cartSessionBean.updateCart(cartUpdateReq.getCart());
            return Response.status(Response.Status.OK).entity(cartUpdateReq.getCart()).build();
        } catch (CartNotFoundException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
        } catch (InvalidLoginCredentialException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.UNAUTHORIZED).entity(errorRsp).build(); 
        } catch(Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build(); 
        }
    }
    
}
