/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;


import ejb.session.stateless.BillingDetailSessionBeanLocal;
import ejb.session.stateless.UserSessionBeanLocal;
import entity.Customer;
import entity.BillingDetail;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;
import util.exception.InvalidLoginCredentialException;
import ws.datamodel.BillingDetailRetrieveAllByCustomerRsp;
import ws.datamodel.ErrorRsp;

/**
 *
 * @author shizhan
 */
@Path("BillingDetail")
public class BillingDetailResource {
    
    @Context
    private UriInfo context;
    private final SessionBeanLookup sessionBeanLookup;
    
    private final BillingDetailSessionBeanLocal billingDetailSessionBeanLocal;
    private final UserSessionBeanLocal userSessionBeanLocal;

    public BillingDetailResource() {
        sessionBeanLookup = new SessionBeanLookup();
        billingDetailSessionBeanLocal = sessionBeanLookup.lookupBillingDetailSessionBeanLocal();
        userSessionBeanLocal = sessionBeanLookup.lookupUserSessionBeanLocal();
    }
    
    @Path("retrieveAllBillingDetails") 
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllBillingDetailByCustomer(@QueryParam("email") String email, 
                                        @QueryParam("password") String password)
    {
        try {
            Customer customer = (Customer) userSessionBeanLocal.userLogin(email, password);

            System.out.println("********** BillingDetailResource.retrieveAllBillingDetail(): Customer " + customer.getEmail() + " login remotely via web service");

            List<BillingDetail> billingDetails = billingDetailSessionBeanLocal.retrieveBillingDetailByCustomer(customer.getUserId());
            return Response.status(Status.OK).entity(new BillingDetailRetrieveAllByCustomerRsp((BillingDetail) billingDetails)).build();
        } catch (InvalidLoginCredentialException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.UNAUTHORIZED).entity(errorRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
}
