/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.OrderSessionBeanLocal;
import ejb.session.stateless.UserSessionBeanLocal;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
/**
 *
 * @author shizhan
 */
public class OrderEntityResource {
    @Context
    private UriInfo context;
    private final SessionBeanLookup sessionBeanLookup;
    private final OrderSessionBeanLocal orderSessionBeanLocal;
    private final UserSessionBeanLocal userSessionBeanLocal;

    public OrderEntityResource() {
        sessionBeanLookup = new SessionBeanLookup();
        orderSessionBeanLocal = sessionBeanLookup.lookupOrderSessionBeanLocal();
        userSessionBeanLocal = sessionBeanLookup.lookupUserSessionBeanLocal();
    }
    
    
    //createorder, change order status, retrieve order for customer/ seller.
    
    //when creating order, delivery detail and billing detail attributes to be passed in.
    
}
