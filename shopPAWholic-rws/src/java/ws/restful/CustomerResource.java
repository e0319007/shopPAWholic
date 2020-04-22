/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.CustomerSessionBeanLocal;
import entity.Customer;
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
import ws.datamodel.CustomerCreateNewRsp;
import ws.datamodel.ErrorRsp;
import ws.datamodel.UserCreateNewReq;
import ws.datamodel.UserRetrieveByIdRsp;

/**
 * REST Web Service
 *
 * @author Joanna Ng
 */
@Path("Customer")
public class CustomerResource {

    @Context
    private UriInfo context;
    
    private final SessionBeanLookup sessionBeanLookup;
    private CustomerSessionBeanLocal customerSessionBeanLocal;

    /**
     * Creates a new instance of CustomerResource
     */
    public CustomerResource() {
        sessionBeanLookup = new SessionBeanLookup();
        customerSessionBeanLocal = sessionBeanLookup.lookupCustomerSessionBeanLocal();
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON) //create/register new user?
    @Produces(MediaType.APPLICATION_JSON)    
    public Response registerCustomer(CustomerCreateNewReq createNewCustomerReq) {
        
        try {
            createNewCustomerReq.getCustomer().setAccCreatedDate(new Date());
            Long customerId = customerSessionBeanLocal.createNewCustomer(createNewCustomerReq.getCustomer());
            
            
            return Response.status(Response.Status.OK).entity(new CustomerCreateNewRsp(customerId)).build();
            
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
     * @return the customerSessionBeanLocal
     */
    public CustomerSessionBeanLocal getCustomerSessionBeanLocal() {
        return customerSessionBeanLocal;
    }

    /**
     * @param customerSessionBeanLocal the customerSessionBeanLocal to set
     */
    public void setCustomerSessionBeanLocal(CustomerSessionBeanLocal customerSessionBeanLocal) {
        this.customerSessionBeanLocal = customerSessionBeanLocal;
    }


}
