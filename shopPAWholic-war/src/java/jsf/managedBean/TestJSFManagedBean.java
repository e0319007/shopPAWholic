/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.AdvertisementSessionBeanLocal;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author EileenLeong
 */
@Named(value = "testJSFManagedBean")
@RequestScoped
public class TestJSFManagedBean {

    @EJB(name = "AdvertisementSessionBeanLocal")
    private AdvertisementSessionBeanLocal advertisementSessionBeanLocal;

    /**
     * Creates a new instance of TestJSFManagedBean
     */
    public TestJSFManagedBean() {
    }
    
}
