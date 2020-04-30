/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.AdvertisementSessionBeanLocal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

@Named(value = "imageGalleriaManagedBean")
@RequestScoped
public class ImageGalleriaManagedBean {

    @EJB(name = "AdvertisementSessionBeanLocal")
    private AdvertisementSessionBeanLocal advertisementSessionBeanLocal;

    private List<String> images;

    @PostConstruct
    public void init() {
        images = new ArrayList<String>();
        images = advertisementSessionBeanLocal.retrieveAdvertisementImages();
        System.out.println("##################" + images);
    }

    public List<String> getImages() {
        return images;
    }
}
