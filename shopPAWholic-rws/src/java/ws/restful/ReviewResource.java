/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.ReviewSessionBeanLocal;
import ejb.session.stateless.UserSessionBeanLocal;
import entity.Customer;
import entity.Listing;
import entity.Review;
import java.util.List;
import java.util.Objects;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import util.exception.CreateNewReviewException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.ListingNotFoundException;
import ws.datamodel.ErrorRsp;
import ws.datamodel.ReviewCreateNewReq;
import ws.datamodel.ReviewCreateNewRsp;
import ws.datamodel.ReviewRetrieveAllByListingIdRsp;
import ws.datamodel.ReviewUpdateReq;

/**
 * REST Web Service
 *
 * @author Joanna Ng
 */
@Path("Review")
public class ReviewResource {

    @Context
    private UriInfo context;
    
    private final SessionBeanLookup sessionBeanLookup;
    
    private final ReviewSessionBeanLocal reviewSessionBeanLocal;
    private final UserSessionBeanLocal userSessionBeanLocal;

    /**
     * Creates a new instance of ReviewResource
     */
    public ReviewResource() {
        sessionBeanLookup = new SessionBeanLookup();
        reviewSessionBeanLocal = sessionBeanLookup.lookupReviewSessionBeanLocal();
        userSessionBeanLocal = sessionBeanLookup.lookupUserSessionBeanLocal();
    }

    /**
     * Retrieves representation of an instance of ws.restful.ReviewResource
     * @param ListingId
     * @return an instance of java.lang.String
     */
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllReviewsByListingId(@PathParam("listingId") Long ListingId) {       
        try {       
            Listing listing = sessionBeanLookup.lookupListingSessionBeanLocal().retrieveListingByListingId(ListingId);            
            List<Review> reviews = listing.getReviews();          
            for (Review r: reviews) {
                r.setCustomer(null);
                r.setListing(null);
            }
            return Response.status(Response.Status.OK).entity(new ReviewRetrieveAllByListingIdRsp(reviews)).build();
        } catch (ListingNotFoundException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
           return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            System.out.println(errorRsp.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    /**
     * PUT method for updating or creating an instance of ReviewResource
     * @param createNewReviewReq
     * @return 
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewReview(ReviewCreateNewReq createNewReviewReq) {
        try {
            Customer customer = (Customer) sessionBeanLookup.lookupUserSessionBeanLocal().userLogin(createNewReviewReq.getEmail(), createNewReviewReq.getPassword());
            System.out.println("********** ReviewResource.createReview(): Customer " + customer.getEmail() + " login remotely via web service");
            Review review = reviewSessionBeanLocal.createNewReview(createNewReviewReq.getReview(), createNewReviewReq.getListingId(), customer.getUserId());
            return Response.status(Status.OK).entity(new ReviewCreateNewRsp(review.getReviewId())).build();              
        } catch (InvalidLoginCredentialException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Response.Status.UNAUTHORIZED).entity(errorRsp).build();
        } catch (CreateNewReviewException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        } catch (InputDataValidationException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
    
    @POST
    @Path("updateReview")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)    
    public Response updateReview(ReviewUpdateReq updateReviewReq) {
        try {
            if (updateReviewReq != null) {
                Customer customer = (Customer) userSessionBeanLocal.userLogin(updateReviewReq.getEmail(), updateReviewReq.getPassword());
                System.out.println("********** ReviewResource.updateReview(): Customer " + customer.getEmail()+ " login remotely via web service");
                reviewSessionBeanLocal.updateReview(updateReviewReq.getReview());             
                if (!Objects.equals(updateReviewReq.getReview().getCustomer().getUserId(), customer.getUserId())) {
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
    
}
