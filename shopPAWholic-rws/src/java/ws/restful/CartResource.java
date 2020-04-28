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
import entity.Listing;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import util.exception.CartNotFoundException;
import util.exception.InvalidLoginCredentialException;
import ws.datamodel.CartRerieveByIdRsp;
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
            
            System.out.println("************* retrieveCartByCustomerId: " + email + "; " + password);
            
            Customer customer = (Customer) userSessionBean.userLogin(email, password);
            System.out.println("********** CartResource.retrieveCartByCustomerId(): Customer " + customer.getEmail()+ " login remotely via web service");
            //Cart cart = cartSessionBean.getCartByCustomerId(customer.getUserId());
            Cart cart = customer.getCart();
            cart.getCustomer().getBillingDetails().clear();
            cart.getCustomer().getReviews().clear();
            cart.getCustomer().getComments().clear();
            cart.getCustomer().getForumPosts().clear();
            cart.getCustomer().getOrders().clear();
            cart.getCustomer().setCart(null);
            
            for (Listing l:cart.getListings()) {
                l.setCategory(null);
                l.getTags().clear();
                l.getOrders().clear();
                l.getReviews().clear();
               
                l.getSeller().getOrders().clear();
                l.getSeller().getListings().clear();
                l.getSeller().getAdvertisements().clear();
                l.getSeller().getBillingDetails().clear();
                l.getSeller().getEvents().clear();
                l.getSeller().getOrders().clear();
            }
            return Response.status(Response.Status.OK).entity(new CartRerieveByIdRsp(cart)).build();
        } catch (InvalidLoginCredentialException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.UNAUTHORIZED).entity(errorRsp).build(); 
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build(); 
        }   
    }
    
    
    @POST
    @Path("saveCartToDatabase")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveCartToDatabase(CartUpdateReq cartUpdateReq) {
        System.out.println("Called save cart to database");
        System.out.println("Cart Update req length of listings: " + cartUpdateReq.getCart().getListings());
        try {
            Customer customer = (Customer) userSessionBean.userLogin(cartUpdateReq.getEmail(), cartUpdateReq.getPassword());
            System.out.println("********** CartResource.saveCartToDatabase(): Customer " + customer.getEmail()+ " login remotely via web service");
            
            cartSessionBean.clearCart(customer.getCart().getCartId());
            System.out.println("Resource cleared cart");
            cartSessionBean.updateCart(cartUpdateReq.getCart());
            System.out.println("Resource update cart");
            return Response.status(Response.Status.OK).build();
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
