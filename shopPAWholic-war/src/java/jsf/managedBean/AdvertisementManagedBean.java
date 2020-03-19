/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.AdvertisementSessionBeanLocal;
import entity.Advertisement;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import util.exception.AdvertisementNotFoundException;
import util.exception.CreateNewAdvertisementException;
import util.exception.InputDataValidationException;
import java.io.Serializable;

/**
 *
 * @author Shi Zhan
 */
@Named(value = "advertisementManagedBean")
@ViewScoped
public class AdvertisementManagedBean implements Serializable{

    @EJB(name = "AdvertisementSessionBeanLocal")
    private AdvertisementSessionBeanLocal advertisementSessionBeanLocal;
    //For creating advertisements
    private List<Advertisement> advertisements;
    private String description;
    private Date startDate;
    private Date endDate;
    private BigDecimal price;
    private List<String> pictures;
    private String url;
    private String ccNum;
    
    //For updating advertisements
    private Advertisement selectedAdvertisementToUpdate;
    
    private Long serviceProviderId;
    

    /**
     * Creates a new instance of AdvertisementManagedBean
     */
    
    
    public AdvertisementManagedBean() {
    }
    
    @PostConstruct
    public void PostConstruct() {
        advertisements = advertisementSessionBeanLocal.retrieveAllAdvertisements();
        List<String> pictures = new ArrayList<>();
    }
    
    public void generatePrice(ActionEvent event) {
        //have to input in days by this part
        long days = ChronoUnit.DAYS.between(startDate.toInstant(), endDate.toInstant());
        price = BigDecimal.ONE;
        price = price.multiply(new BigDecimal(days));
    }
    
    public void createNewAdvertisements(ActionEvent event) { //incomplete, add my advertisment ccnum first using get attribute
        try {
            Advertisement advertisement = new Advertisement(description, startDate, endDate, price, pictures, url);
            advertisementSessionBeanLocal.createNewAdvertisement(advertisement, serviceProviderId, ccNum);
        } catch (CreateNewAdvertisementException ex) {
            Logger.getLogger(AdvertisementManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InputDataValidationException ex) {
            Logger.getLogger(AdvertisementManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteAdvertisement(ActionEvent event) {
        try {
            Advertisement advertisementToDelete = (Advertisement) event.getComponent().getAttributes().get("AdvertisementToDelete");
            advertisementSessionBeanLocal.deleteAdvertisement(advertisementToDelete.getAdvertisementId());
        } catch (AdvertisementNotFoundException ex) {
            Logger.getLogger(AdvertisementManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateAdvertisement(ActionEvent event) {
        try {
            selectedAdvertisementToUpdate = (Advertisement) event.getComponent().getAttributes().get("AdvertisementToUpdate");
            advertisementSessionBeanLocal.updateAdvertisement(selectedAdvertisementToUpdate);
        } catch (InputDataValidationException ex) {
            Logger.getLogger(AdvertisementManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void updateAdvertisements() {
        
    }

    public AdvertisementSessionBeanLocal getAdvertisementSessionBeanLocal() {
        return advertisementSessionBeanLocal;
    }

    public void setAdvertisementSessionBeanLocal(AdvertisementSessionBeanLocal advertisementSessionBeanLocal) {
        this.advertisementSessionBeanLocal = advertisementSessionBeanLocal;
    }

    public List<Advertisement> getAdvertisements() {
        return advertisements;
    }

    public void setAdvertisements(List<Advertisement> advertisements) {
        this.advertisements = advertisements;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
}
