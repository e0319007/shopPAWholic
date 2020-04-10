/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.UserSessionBeanLocal;
import entity.Customer;
import entity.Seller;
import entity.User;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import util.exception.InvalidLoginCredentialException;
import util.exception.UnknownPersistenceException;
import util.exception.UserUsernameExistException;
import ws.datamodel.ErrorRsp;
import ws.datamodel.UserCreateNewReq;
import ws.datamodel.UserLoginRsp;
import ws.datamodel.UserRetrieveByIdRsp;

/**
 * REST Web Service
 *
 * @author Joanna Ng
 */
@Path("User")
public class UserResource {

    @Context
    private UriInfo context;
    
    private final SessionBeanLookup sessionBeanLookup;
    private final UserSessionBeanLocal userSessionBeanLocal;

    /**
     * Creates a new instance of UserResource
     */
    public UserResource() {
        sessionBeanLookup = new SessionBeanLookup();
        userSessionBeanLocal = sessionBeanLookup.lookupUserSessionBeanLocal();
    }

    /**
     * Retrieves representation of an instance of ws.restful.UserResource
     * @param email
     * @param password
     * @return an instance of java.lang.String
     */
    @Path("userLogin")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response UserLogin(@QueryParam("email") String email, @QueryParam("password") String password) {
        try {           
            User user = userSessionBeanLocal.userLogin(email, password);
            System.out.println("********** UserResource.userLogin(): User " + user.getEmail() + " login remotely via web service");
            
            user.setPassword(null);
            user.setSalt(null);          
            //might need to set all the stuff to null based on the different user types
            
            if (user instanceof Customer) {
                Customer customer = (Customer) user;
                customer.getBillingDetails().clear();
                customer.getForumPosts().clear();
                customer.setCart(null);
                customer.getComments().clear();
                customer.getOrders().clear();
                customer.getReviews().clear();
            } else if (user instanceof Seller) {
                Seller seller = (Seller) user;
                seller.getAdvertisements().clear();
                seller.getBillingDetails().clear();
                seller.getEvents().clear();
                seller.getListings().clear();
                seller.getOrders().clear();
            }
            
            return Response.status(Status.OK).entity(new UserLoginRsp(user)).build();           
        } catch (InvalidLoginCredentialException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Response.Status.UNAUTHORIZED).entity(errorRsp).build();
        } catch(Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }     
    }

    /**
     * PUT method for updating or creating an instance of UserResource
     * @param createNewUserReq
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON) //create/register new user?
    @Produces(MediaType.APPLICATION_JSON)    
    public Response registerUser(UserCreateNewReq createNewUserReq) {
        
        try {
            Long userId = userSessionBeanLocal.createNewUser(createNewUserReq.getUser());
            
            return Response.status(Response.Status.OK).entity(new UserRetrieveByIdRsp(userSessionBeanLocal.retrieveUserByUserId(userId))).build();
            
        } catch (UserUsernameExistException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
        } catch (UnknownPersistenceException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
        
    }
 
    
}
