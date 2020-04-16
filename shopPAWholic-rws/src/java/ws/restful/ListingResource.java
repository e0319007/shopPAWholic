/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;
import ejb.session.stateless.ListingSessionBeanLocal;
import ejb.session.stateless.UserSessionBeanLocal;
import entity.Listing;
import entity.OrderEntity;
import entity.Review;
import entity.Seller;
import entity.Tag;
import entity.User;
import java.util.List;
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
import util.exception.CreateNewListingException;
import util.exception.InvalidLoginCredentialException;
import util.exception.ListingNotFoundException;
import util.exception.UpdateListingException;
import ws.datamodel.ListingCreateReq;
import ws.datamodel.ErrorRsp;
import ws.datamodel.ListingRetrieveDetailRsp;
import ws.datamodel.ListingUpdateReq;
import ws.datamodel.ListingsRetrieveAllRsp;

/**
 *
 * @author shizhan
 */

@Path("Listing")
public class ListingResource {    
    @Context
    private UriInfo context;
    
    private final SessionBeanLookup sessionBeanLookup;
    
    private final UserSessionBeanLocal userSessionBeanLocal;
    private final ListingSessionBeanLocal listingSessionBeanLocal;
    
    
    
    public ListingResource() 
    {
        sessionBeanLookup = new SessionBeanLookup();
        
        userSessionBeanLocal = sessionBeanLookup.lookupUserSessionBeanLocal();
        listingSessionBeanLocal = sessionBeanLookup.lookupListingSessionBeanLocal();
    }
    
    public Seller getSellerInstance(String email, String password, String methodName) throws InvalidLoginCredentialException {
        User user =  userSessionBeanLocal.userLogin(email, password);
        System.out.println("********** ListingResource." + methodName + "(): User " + user.getEmail()+ " login remotely via web service");
        if(user instanceof Seller) return (Seller) user;
        else throw new InvalidLoginCredentialException();
    }
    
    @Path("retrieveAllListings")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllListings() {
        try {
            List<Listing> listings = listingSessionBeanLocal.retrieveAllListings();
            for(Listing l:listings) {
                if(l.getCategory().getParentCategory() != null) {
                    l.getCategory().setParentCategory(null);
                    l.getCategory().getListings().clear();
                    l.getCategory().getSubCategories().clear();
                }
                
                for(Tag t:l.getTags()) {
                    t.getListings().clear();
                }
                l.getSeller().getListings().clear();
                l.getSeller().getAdvertisements().clear();
                l.getSeller().getBillingDetails().clear();
                l.getSeller().getEvents().clear();
                l.getSeller().getOrders().clear();
                
                for (Review r: l.getReviews()) {
                    r.setListing(null);
                    r.getCustomer().setBillingDetails(null);
                    r.getCustomer().setCart(null);
                    r.getCustomer().setComments(null);
                    r.getCustomer().setForumPosts(null);
                    r.getCustomer().setReviews(null);
                    r.getCustomer().setOrders(null);
                }
                l.getOrders().clear();
                
                
            }
            return Response.status(Status.OK).entity(new ListingsRetrieveAllRsp(listings)).build();
        }
        catch(Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    
    
    @Path("retrieveListing/{listingId}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveListing(@PathParam("listingId") Long listingId) {
        System.out.println("RetrieveListing by id is called");
        try {
            Listing listing = listingSessionBeanLocal.retrieveListingByListingId(listingId);
            
            if(listing.getCategory().getParentCategory() != null) {
                listing.getCategory().setParentCategory(null);
                listing.getCategory().getListings().clear();
                listing.getCategory().getSubCategories().clear();
            }

            for(Tag t:listing.getTags()) {
                t.getListings().clear();
            }
            listing.getSeller().getListings().clear();
            listing.getSeller().getAdvertisements().clear();
            listing.getSeller().getBillingDetails().clear();
            listing.getSeller().getEvents().clear();
            listing.getSeller().getOrders().clear();

            for (Review r: listing.getReviews()) {
                r.setListing(null);
                r.getCustomer().setBillingDetails(null);
                r.getCustomer().setCart(null);
                r.getCustomer().setComments(null);
                r.getCustomer().setForumPosts(null);
                r.getCustomer().setReviews(null);
                r.getCustomer().setOrders(null);
            }
            listing.getOrders().clear();
                
            return Response.status(Status.OK).entity(new ListingRetrieveDetailRsp(listing)).build(); 
        }
        catch(ListingNotFoundException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
        }
        catch(Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());   
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createListing(ListingCreateReq createListingReq)
    {
        if(createListingReq != null) {
            try {
                Seller seller = getSellerInstance(createListingReq.getEmail(), createListingReq.getPassword(), "createListing");
                Listing listing  = listingSessionBeanLocal.createNewListing(createListingReq.getListing(), createListingReq.getCategoryId(), createListingReq.getTagIds(), seller.getUserId());
                
                return Response.status(Response.Status.OK).entity(listing.getListingId()).build();
            }
            catch(InvalidLoginCredentialException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                return Response.status(Status.UNAUTHORIZED).entity(errorRsp).build();
            }
            catch(CreateNewListingException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
            }
            catch(Exception ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        }
        else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid create new product request");
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
    
   
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateListing(ListingUpdateReq listingUpdateReq)
    {
        if(listingUpdateReq != null) {
            try {                
                Seller seller = getSellerInstance(listingUpdateReq.getEmail(), listingUpdateReq.getPassword(), "updateListing");
                
                listingSessionBeanLocal.updateListing(listingUpdateReq.getListing(), listingUpdateReq.getCategoryId(), listingUpdateReq.getTagIds());
                
                return Response.status(Response.Status.OK).build();
            }
            catch(InvalidLoginCredentialException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                return Response.status(Status.UNAUTHORIZED).entity(errorRsp).build();
            }
            catch(UpdateListingException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
            }
            catch(Exception ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        }
        else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid update product request");
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
    
    
    
    @Path("{listingId}")
    @DELETE
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteListing(@QueryParam("email") String email, 
                                        @QueryParam("password") String password,
                                        @PathParam("listingId") Long listingId) {
        try {
            Seller seller = getSellerInstance(email, password, "deleteListing");
            listingSessionBeanLocal.deleteListing(listingId);
            return Response.status(Status.OK).build();
        }
        catch(ListingNotFoundException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
        }
        catch(Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
}

