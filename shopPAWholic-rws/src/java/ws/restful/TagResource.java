/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import entity.Seller;
import entity.Tag;
import entity.User;
import java.util.List;
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
import util.exception.CreateNewTagException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import ws.datamodel.ErrorRsp;
import ws.datamodel.TagCreateNewReq;
import ws.datamodel.TagCreateNewRsp;
import ws.datamodel.TagRetrieveAllRsp;

/**
 * REST Web Service
 *
 * @author Joanna Ng
 */
@Path("Tag")
public class TagResource {

    @Context
    private UriInfo context;
    
    private final SessionBeanLookup sessionBeanLookup;

    /**
     * Creates a new instance of TagResource
     */
    public TagResource() {
        sessionBeanLookup = new SessionBeanLookup();
    }

    /**
     * Retrieves representation of an instance of ws.restful.TagResource
     * @return an instance of java.lang.String
     */
    @Path("retrieveAllTags")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllTags() {
        
        try {
            List<Tag> tags = sessionBeanLookup.lookupTagSessionBeanLocal().retrieveAllTags();
            for (Tag t: tags) {
                t.getListings().clear();
            }
             return Response.status(Status.OK).entity(new TagRetrieveAllRsp(tags)).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    /**
     * PUT method for updating or creating an instance of TagResource
     * @param tagCreateNewReq
     * @return 
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewTag(TagCreateNewReq tagCreateNewReq) {    
        try {
            User user = getUser(tagCreateNewReq.getEmail(), tagCreateNewReq.getPassword(), "TagResource.createNewTag()");
            if (user instanceof Seller) {
                Tag tag = sessionBeanLookup.lookupTagSessionBeanLocal().createNewTag(tagCreateNewReq.getTag());
                return Response.status(Status.OK).entity(new TagCreateNewRsp(tag.getTagId())).build();         
            } else { //not instance of seller
                throw new InvalidLoginCredentialException();
            }
        } catch (InvalidLoginCredentialException ex) {
                 ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                return Response.status(Status.UNAUTHORIZED).entity(errorRsp).build();
        } catch (CreateNewTagException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        } catch (InputDataValidationException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
    
    
    
    public User getUser(String email, String password, String methodName) throws InvalidLoginCredentialException {
        User user = sessionBeanLookup.lookupUserSessionBeanLocal().userLogin(email, password);
        System.out.println("********** " + methodName + ": User " + user.getEmail()+ " login remotely via web service");
        return user;
    } 
    
}
