/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import java.util.Set;
import java.util.HashSet;
import javax.ws.rs.core.Application;

/**
 *
 * @author Shi Zhan
 */
@javax.ws.rs.ApplicationPath("Resources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(ws.restful.AdvertisementResource.class);
        resources.add(ws.restful.BillingDetailResource.class);
        resources.add(ws.restful.CartResource.class);
        resources.add(ws.restful.CategoryResource.class);
        resources.add(ws.restful.CustomerResource.class);
        resources.add(ws.restful.DeliveryDetailsResource.class);
        resources.add(ws.restful.ListingResource.class);
        resources.add(ws.restful.OrderEntityResource.class);
        resources.add(ws.restful.ReviewResource.class);
        resources.add(ws.restful.SellerResource.class);
        resources.add(ws.restful.TagResource.class);
        resources.add(ws.restful.UserResource.class);
        resources.add(ws.restful.resources.CorsFilter.class);

    }
}
