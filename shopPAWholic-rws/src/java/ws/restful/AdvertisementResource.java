/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.AdvertisementSessionBeanLocal;
import ejb.session.stateless.UserSessionBeanLocal;
import entity.Advertisement;
import entity.Seller;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javax.ws.rs.core.Response.Status;
import util.exception.AdvertisementNotFoundException;
import util.exception.CreateNewAdvertisementException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import ws.datamodel.AdvertisementCreateNewReq;
import ws.datamodel.AdvertisementCreateNewRsp;
import ws.datamodel.ErrorRsp;
import ws.datamodel.AdvertisementRetrieveAllRsp;
import ws.datamodel.AdvertisementRetrieveByIdRsp;
import ws.datamodel.AdvertisementUpdateReq;

/**
 *
 * @author shizhan
 */
@Path("Advertisement")
public class AdvertisementResource {
    
    @Context
    private UriInfo context;
    
    private final SessionBeanLookup sessionBeanLookup;
    private final AdvertisementSessionBeanLocal advertisementSessionBeanLocal;
    private final UserSessionBeanLocal userSessionBeanLocal;
    
    public AdvertisementResource() {
        sessionBeanLookup = new SessionBeanLookup();
        advertisementSessionBeanLocal = sessionBeanLookup.lookupAdvertisementSessionBeanLocal();
        userSessionBeanLocal = sessionBeanLookup.lookupUserSessionBeanLocal();
    }
    
    @Path("retrieveAllAdvertisements")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllAdvertisements() {
        try {
            List<Advertisement> advertisements = advertisementSessionBeanLocal.retrieveAllAdvertisements();
            for(Advertisement a : advertisements) {
                a.getBillingDetail().setAdvertisement(null);
                a.getSeller().getAdvertisements().clear();
                a.getSeller().getListings().clear();
                a.getSeller().getAdvertisements().clear();
                a.getSeller().getBillingDetails().clear();
                a.getSeller().getEvents().clear();
                a.getSeller().getOrders().clear();
            }
            return Response.status(Status.OK).entity(new AdvertisementRetrieveAllRsp(advertisements)).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    @Path("retrieveAdvertisement/{advertisementId}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAdvertisement(@QueryParam("email") String email, 
                                        @QueryParam("password") String password,
                                        @PathParam("advertisementId") Long advertisementId) {
        
        try {
            Seller seller = (Seller) userSessionBeanLocal.userLogin(email, password);
            System.out.println("********** Advertisement.retrieveAdvertisement(): Seller " + seller.getEmail()+ " login remotely via web service");
            Advertisement advertisement  = advertisementSessionBeanLocal.retrieveAdvertisementById(advertisementId);
            advertisement.getBillingDetail().setAdvertisement(null);
            advertisement.getSeller().getAdvertisements().clear();
            return Response.status(Response.Status.OK).entity(advertisement).build();
            
        } catch (InvalidLoginCredentialException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Response.Status.UNAUTHORIZED).entity(errorRsp).build();
        } catch (AdvertisementNotFoundException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        } catch(Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build(); 
        }
    }
    
    
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewAdvertisement(AdvertisementCreateNewReq createNewAdvertisementReq) {
        try {
            Seller seller = (Seller) userSessionBeanLocal.userLogin(createNewAdvertisementReq.getEmail(), createNewAdvertisementReq.getPassword());
            System.out.println("********** Advertisement.createNewAdvertisement(): Seller " + seller.getEmail()+ " login remotely via web service");
            //trying to circumvent the ts -> java date problem and the @notnull on both start and end date for ads
            System.out.println("***** Start Year: " + createNewAdvertisementReq.getStartYear());
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String startDate = createNewAdvertisementReq.getStartDay() + "-" + createNewAdvertisementReq.getStartMth() + "-" + createNewAdvertisementReq.getStartYear();
            Date start = sdf.parse(startDate);
            System.out.println("*****formatted start date: " + start);
        
            createNewAdvertisementReq.getAdvertisement().setStartDate(start);
            
            String endDate = createNewAdvertisementReq.getEndDay() + "-" + createNewAdvertisementReq.getEndMth() + "-" + createNewAdvertisementReq.getEndYear();
            Date end = sdf.parse(endDate);
            System.out.println("****** formatted End Date: " + end);
            createNewAdvertisementReq.getAdvertisement().setEndDate(end);
            
            BigDecimal price = new BigDecimal(createNewAdvertisementReq.getPrice());
            createNewAdvertisementReq.getAdvertisement().setPrice(price);
            
            String picture = createNewAdvertisementReq.getPictures();
            createNewAdvertisementReq.getAdvertisement().setPicture(picture);
            System.out.println("picture: " + createNewAdvertisementReq.getAdvertisement().getPicture());
            
            Date today = new Date();
            createNewAdvertisementReq.getAdvertisement().setListDate(today);
            
            System.out.println("***** CCNum: " + createNewAdvertisementReq.getCcNum());
            
            Advertisement advertisement  = advertisementSessionBeanLocal.createNewAdvertisement(createNewAdvertisementReq.getAdvertisement(), seller.getUserId(), createNewAdvertisementReq.getCcNum()); 
            //CreateNewAdvertisementRsp createNewAdvertisementRsp = new CreateNewAdvertisementRsp(advertisement.getAdvertisementId());
            return Response.status(Response.Status.OK).entity(new AdvertisementCreateNewRsp(advertisement.getAdvertisementId())).build();
            
        } catch (InvalidLoginCredentialException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.UNAUTHORIZED).entity(errorRsp).build();
        } catch (CreateNewAdvertisementException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        } catch (InputDataValidationException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
        } catch(Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build(); 
        }
    }
    
    @POST
    @Path("updateAdvertisement")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAdvertisement(AdvertisementUpdateReq updateAdvertisementReq) {
        try {
            if (updateAdvertisementReq != null) {
                Seller seller = (Seller) userSessionBeanLocal.userLogin(updateAdvertisementReq.getEmail(), updateAdvertisementReq.getPassword());
                System.out.println("********** Advertisement.updateAdvertisement(): Seller " + seller.getEmail()+ " login remotely via web service");
                advertisementSessionBeanLocal.updateAdvertisement(updateAdvertisementReq.getAdvertisement());
                if (!Objects.equals(updateAdvertisementReq.getAdvertisement().getSeller().getUserId(), seller.getUserId())) {
                    throw new InvalidLoginCredentialException();
                }
            }
        } catch (InvalidLoginCredentialException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.UNAUTHORIZED).entity(errorRsp).build();
        } catch (InputDataValidationException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
        }catch(Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build(); 
        }
        return Response.status(Response.Status.OK).build();
    }
    
    @Path("{advertisementId}")
    @DELETE
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAdvertisement(@QueryParam("username") String email, 
                                        @QueryParam("password") String password,
                                        @PathParam("advertisementId") Long advertisementId) {
        try {
            Seller seller = (Seller) userSessionBeanLocal.userLogin(email, password);
            System.out.println("********** Advertisement.deleteAdvertisement(): Seller " + seller.getEmail()+ " login remotely via web service");
            advertisementSessionBeanLocal.deleteAdvertisement(advertisementId);
            return Response.status(Status.OK).build();
        } catch (InvalidLoginCredentialException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.UNAUTHORIZED).entity(errorRsp).build();
        } catch (AdvertisementNotFoundException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        } catch(Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build(); 
        }
    }
    
    
}
