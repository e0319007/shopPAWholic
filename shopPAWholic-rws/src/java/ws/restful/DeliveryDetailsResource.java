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
import util.enumeration.DeliveryMethod;
import util.exception.CreateNewDeliveryDetailException;
import util.exception.DeliveryDetailNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import ws.datamodel.DeliveryDetailCreateNewReq;
import ws.datamodel.DeliveryDetailCreateNewRsp;
import ws.datamodel.DeliveryDetailRetrieveByOrderIdRsp;
import ws.datamodel.DeliveryDetailUpdateReq;
import ws.datamodel.ErrorRsp;
import java.util.Date;
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
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createDeliveryDetail(DeliveryDetailCreateNewReq deliveryDetailCreateNewReq) {
        try {
            User user = userSessionBeanLocal.userLogin(deliveryDetailCreateNewReq.getEmail(), deliveryDetailCreateNewReq.getPassword());
            System.out.println("********** DeliveryDetailsResource.retrieveDeliveryDetailByOrderId(): user " + user.getEmail()+ " login remotely via web service");
            DeliveryDetail toCreate = new DeliveryDetail(deliveryDetailCreateNewReq.getDeliveryDetail().getAddress(), deliveryDetailCreateNewReq.getDeliveryDetail().getContactNumber(), new Date(), deliveryDetailCreateNewReq.getDeliveryDetail().getDeliveryMethod());
            System.out.println("Delivery detail to create address: " + toCreate.getAddress());
            System.out.println("Delivery detail to create price: " + toCreate.getDeliveryPrice());
            System.out.println("Delivery detail to create date: " + toCreate.getDeliveryDate());
            DeliveryDetail deliveryDetail = deliveryDetailSessionBeanLocal.createNewDeliveryDetail(toCreate);
            return Response.status(Response.Status.OK).entity(new DeliveryDetailCreateNewRsp(deliveryDetail)).build();
        } catch (InvalidLoginCredentialException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Response.Status.UNAUTHORIZED).entity(errorRsp).build();
        } catch (CreateNewDeliveryDetailException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        } catch (InputDataValidationException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Response.Status.UNAUTHORIZED).entity(errorRsp).build();
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDeliveryDetail(DeliveryDetailUpdateReq deliveryDetailUpdateReq) {
        try {
            User user = userSessionBeanLocal.userLogin(deliveryDetailUpdateReq.getEmail(), deliveryDetailUpdateReq.getPassword());
            System.out.println("********** DeliveryDetailsResource.updateDeliveryDetail(): user " + user.getEmail()+ " login remotely via web service");
            
            deliveryDetailUpdateReq.getDeliveryDetail().getStatusLists().add(deliveryDetailUpdateReq.getStatusToAdd());
            deliveryDetailSessionBeanLocal.updateDeliveryDetail(deliveryDetailUpdateReq.getDeliveryDetail());
            
            System.out.println("delivery status list: " + deliveryDetailUpdateReq.getDeliveryDetail().getStatusLists());
            return Response.status(Response.Status.OK).build();
        } catch (InvalidLoginCredentialException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Response.Status.UNAUTHORIZED).entity(errorRsp).build();
        } catch (InputDataValidationException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Response.Status.UNAUTHORIZED).entity(errorRsp).build();
        }
    }
    
    //retrieve and get
    @Path("retrieveDeliveryDetail/{deliveryDetailId}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveDeliveryDetailByOrderId(@QueryParam("email") String email,
                                                    @QueryParam("password") String password,
                                                    @PathParam("deliveryDetailId") Long deliveryDetailId) {
        try {
            User user = userSessionBeanLocal.userLogin(email, password);
            System.out.println("********** DeliveryDetailsResource.retrieveDeliveryDetailByOrderId(): user " + user.getEmail()+ " login remotely via web service");
            System.out.println("Finding delivery detail id: " + deliveryDetailId);
            DeliveryDetail deliveryDetail = deliveryDetailSessionBeanLocal.retrieveDeliveryDetailByOrderId(deliveryDetailId);
            System.out.println("Retrieved delivery detail with status list length: " + deliveryDetail.getStatusLists());
            System.out.println(deliveryDetail);
            return Response.status(Response.Status.OK).entity(new DeliveryDetailRetrieveByOrderIdRsp(deliveryDetail)).build();
        } catch (InvalidLoginCredentialException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Response.Status.UNAUTHORIZED).entity(errorRsp).build();
        } catch (DeliveryDetailNotFoundException ex) {
           ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
           return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
}
