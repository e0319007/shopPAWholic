package jsf.managedBean;

import ejb.session.stateless.AdvertisementSessionBeanLocal;
import entity.Advertisement;
import entity.User;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Shi Zhan
 */
@Named(value = "advertisementManagedBean")
@ViewScoped
public class AdvertisementManagedBean implements Serializable {

    @EJB(name = "AdvertisementSessionBeanLocal")
    private AdvertisementSessionBeanLocal advertisementSessionBeanLocal;

    //For creating advertisements
    private Advertisement newAdvertisement;
    private List<Advertisement> advertisements;
    private String description;
    private Date startDate;
    private Date endDate;
    private BigDecimal price;
    private String url;
    private String ccNum;
    private String picture;
    private Date listDate;
    
    private Long seller;
    
    private Long sellerId;
    
    //for fileUpload
    private UploadedFile file;

    //For updating advertisements
    private Advertisement selectedAdvertisementToUpdate;

    public AdvertisementManagedBean() {
        newAdvertisement = new Advertisement();
    }

    @PostConstruct
    public void PostConstruct() {
        advertisements = advertisementSessionBeanLocal.retrieveAllAdvertisements();
        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser") != null) {
            User currentUser = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
            sellerId = currentUser.getUserId();
        }

    }

    public void generatePrice() {
        //have to input in days by this part
        long days = ChronoUnit.DAYS.between(startDate.toInstant(), endDate.toInstant());
        price = BigDecimal.ONE;
        price = price.multiply(new BigDecimal(days));
    }

    public void handleFileUpload(FileUploadEvent event) {
        User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
        try {
            String destination = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("alternatedocroot_1") + System.getProperty("file.separator");
            String secDest = "Seller"
                    + System.getProperty("file.separator")
                    + user.getUserId()
                    + System.getProperty("file.separator")
                    + "Advertisement"
                    + System.getProperty("file.separator");
            File newPath = new File(destination + secDest);

            newPath.mkdirs();
            System.err.println("********** FileUploadView.handleFileUpload(): File name: " + event.getFile().getFileName());
            System.err.println("********** FileUploadView.handleFileUpload(): newFilePath: " + newPath);

            File file = new File(newPath + "/" + event.getFile().getFileName());
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            //Creates a FileOutputStream to write to the file represented by the specified file

            int a;
            int BUFFER_SIZE = 8192;
            byte[] buffer = new byte[BUFFER_SIZE];

            InputStream inputStream = event.getFile().getInputstream();
            //This getInputStream() method of the uploadedFile represents the file content
            setPicture("http://localhost:8080/shopPAWholic-war/uploadedFiles/" + secDest + event.getFile().getFileName());

            while (true) {
                a = inputStream.read(buffer);

                if (a < 0) {
                    break;
                }
                fileOutputStream.write(buffer, 0, a);
                //write a bytes from the specified bytes array starting at offset 0 to this FileOutputStream
                fileOutputStream.flush();
            }
            fileOutputStream.close();
            inputStream.close();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "File uploaded successfully", ""));
        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "File upload error: " + ex.getMessage(), ""));
        }
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void createNewAdvertisement(ActionEvent event) {
        try {
            listDate = new Date();
            newAdvertisement = new Advertisement(description, startDate, endDate, price, picture, url, listDate);
            Advertisement advertisement = advertisementSessionBeanLocal.createNewAdvertisement(newAdvertisement, sellerId, ccNum);
            advertisements.add(advertisement);
            newAdvertisement = new Advertisement();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New advertisement " + advertisement.getAdvertisementId()+ " created successfully", null));
        } catch (InputDataValidationException | CreateNewAdvertisementException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new advertisement: " + ex.getMessage(), null));
        }
    }

    public void deleteAdvertisement(ActionEvent event) {
        try {
            Advertisement advertisementToDelete = (Advertisement) event.getComponent().getAttributes().get("AdvertisementToDelete");
            advertisementSessionBeanLocal.deleteAdvertisement(advertisementToDelete.getAdvertisementId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Forum deleted successfully! (Id: " + advertisementToDelete.getAdvertisementId() + ")", null));
        } catch (AdvertisementNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unknown Error occured while creating the advertisement!", null));
        }
    }

    public void updateAdvertisement(ActionEvent event) {
        try {
            selectedAdvertisementToUpdate = (Advertisement) event.getComponent().getAttributes().get("AdvertisementToUpdate");
            advertisementSessionBeanLocal.updateAdvertisement(selectedAdvertisementToUpdate);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Forum updated successfully! (Id: " + selectedAdvertisementToUpdate.getAdvertisementId() + ")", null));
        } catch (InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Input validation error: " + ex.getMessage(), null));
        }

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getListDate() {
        return listDate;
    }

    public void setListDate(Date listDate) {
        this.listDate = listDate;
    }
    
    public Advertisement getNewAdvertisement() {
        return newAdvertisement;
    }

    /**
     * @param newAdvertisement the newAdvertisement to set
     */
    public void setNewAdvertisement(Advertisement newAdvertisement) {
        this.newAdvertisement = newAdvertisement;
    }

    /**
     * @return the sellerId
     */
    public Long getSellerId() {
        return sellerId;
    }

    /**
     * @param sellerId the sellerId to set
     */
    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    /**
     * @return the ccNum
     */
    public String getCcNum() {
        return ccNum;
    }

    /**
     * @param ccNum the ccNum to set
     */
    public void setCcNum(String ccNum) {
        this.ccNum = ccNum;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
