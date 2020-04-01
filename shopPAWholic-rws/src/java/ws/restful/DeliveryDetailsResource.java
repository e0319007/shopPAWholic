/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.DeliveryDetailSessionBeanLocal;
import ejb.session.stateless.UserSessionBeanLocal;
import entity.DeliveryDetail;
import entity.User;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.exception.DeliveryDetailNotFoundException;
import util.exception.InvalidLoginCredentialException;
import ws.datamodel.ErrorRsp;
/**
 *
 * @author shizhan
 */

@Path("DeliveryDetail")
public class DeliveryDetailsResource {
    @Context
    private UriInfo context;
    private final SessionBeanLookup sessionBeanLookup;
    private final DeliveryDetailSessionBeanLocal deliveryDetailSessionBeanLocal;
    private final UserSessionBeanLocal userSessionBeanLocal;
 
    public DeliveryDetailsResource() {
        sessionBeanLookup = new SessionBeanLookup();
        deliveryDetailSessionBeanLocal = sessionBeanLookup.lookupDeliveryDetailSessionBeanLocal();
        userSessionBeanLocal = sessionBeanLookup.lookupUserSessionBeanLocal();
    }
    
    //retrieve and get
    
    public Response retrieveDeliveryDetailByOrderId(@QueryParam("email") String email,
                                                    @QueryParam("password") String password,
                                                    @PathParam("deliveryDetailId") Long deliveryDetailId) {
        try {
            User user = userSessionBeanLocal.userLogin(email, password);
            System.out.println("********** DeliveryDetailsResource.retrieveDeliveryDetailByOrderId(): user " + user.getEmail()+ " login remotely via web service");
            DeliveryDetail deliveryDetail = deliveryDetailSessionBeanLocal.retrieveDeliveryDetailById(deliveryDetailId);
            
            return Response.status(Response.Status.OK).entity(deliveryDetail).build();
        } catch (InvalidLoginCredentialException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Response.Status.UNAUTHORIZED).entity(errorRsp).build();
        } catch (DeliveryDetailNotFoundException ex) {
           ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
           return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
}
