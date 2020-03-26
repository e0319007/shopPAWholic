/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.DeliveryDetailSessionBeanLocal;
import ejb.session.stateless.UserSessionBeanLocal;
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
import javax.ws.rs.core.Response.Status;
/**
 *
 * @author shizhan
 */
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
    
//    public 
}
