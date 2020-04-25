/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.CategorySessionBeanLocal;
import ejb.session.stateless.UserSessionBean;
import ejb.session.stateless.UserSessionBeanLocal;
import entity.Category;
import entity.Seller;
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
import ws.datamodel.ErrorRsp;
import ws.datamodel.CategoriesRetrieveAllRsp;

/**
 *
 * @author shizhan
 */
@Path("Category")
public class CategoryResource
{    
    @Context
    private UriInfo context;
    
    private final SessionBeanLookup sessionBeanLookup;
    
    private final UserSessionBeanLocal userSessionBeanLocal;
    private final CategorySessionBeanLocal categorySessionBeanLocal;

    public CategoryResource() {
        
        sessionBeanLookup = new SessionBeanLookup();
        userSessionBeanLocal = sessionBeanLookup.lookupUserSessionBeanLocal();
        categorySessionBeanLocal = sessionBeanLookup.lookupCategorySessionBeanLocal();
    }
    
    @Path("retrieveAllCategories")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllCategories(@QueryParam("email") String email, 
                                        @QueryParam("password") String password)
    {
        try
        {
            Seller seller = (Seller) userSessionBeanLocal.userLogin(email, password);
            System.out.println("********** CategoryResource.retrieveAllCategories(): seller " + seller.getEmail()+ " login remotely via web service");

            List<Category> categoryEntities = categorySessionBeanLocal.retrieveAllCategories();
            
            for(Category category:categoryEntities) {
                category.getListings().clear();
            }
            return Response.status(Status.OK).entity(new CategoriesRetrieveAllRsp(categoryEntities)).build();
        }
        catch(InvalidLoginCredentialException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Status.UNAUTHORIZED).entity(errorRsp).build();
        }
        catch(Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
}