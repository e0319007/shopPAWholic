/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.SellerSessionBeanLocal;
import java.util.Date;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.exception.UnknownPersistenceException;
import util.exception.UserUsernameExistException;
import ws.datamodel.CustomerCreateNewReq;
import ws.datamodel.ErrorRsp;
import ws.datamodel.SellerCreateNewReq;
import ws.datamodel.SellerCreateNewRsp;
import ws.datamodel.UserRetrieveByIdRsp;

/**
 * REST Web Service
 *
 * @author Joanna Ng
 */
@Path("Seller")
public class SellerResource {

    @Context
    private UriInfo context;
    
    private final SessionBeanLookup sessionBeanLookup;
    private SellerSessionBeanLocal sellerSessionBeanLocal;
    
    /**
     * Creates a new instance of SellerResource
     */
    public SellerResource() {
        sessionBeanLookup = new SessionBeanLookup();
        sellerSessionBeanLocal = sessionBeanLookup.lookupSellerSessionBeanLocal();
    }


     @PUT
    @Consumes(MediaType.APPLICATION_JSON) //create/register new user?
    @Produces(MediaType.APPLICATION_JSON)    
    public Response registerSeller(SellerCreateNewReq createNewSellerReq) {
        
        try {
            createNewSellerReq.getSeller().setAccCreatedDate(new Date());
            createNewSellerReq.getSeller().setVerified(false);
            Long sellerId = sellerSessionBeanLocal.createNewSeller(createNewSellerReq.getSeller());
            
            return Response.status(Response.Status.OK).entity(new SellerCreateNewRsp(sellerId)).build();
            
        } catch (UserUsernameExistException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        } catch (UnknownPersistenceException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
        
    }
    

    /**
     * @return the sellerSessionBeanLocal
     */
    public SellerSessionBeanLocal getSellerSessionBeanLocal() {
        return sellerSessionBeanLocal;
    }

    /**
     * @param sellerSessionBeanLocal the sellerSessionBeanLocal to set
     */
    public void setSellerSessionBeanLocal(SellerSessionBeanLocal sellerSessionBeanLocal) {
        this.sellerSessionBeanLocal = sellerSessionBeanLocal;
    }
}
