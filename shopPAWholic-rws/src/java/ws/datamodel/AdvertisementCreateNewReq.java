/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;
import entity.Advertisement;
import java.util.Date;

/**
 *
 * @author shizhan
 */
public class AdvertisementCreateNewReq {

    private Advertisement advertisement;
    private String email;
    private String password;
    private String ccNum;
    private Date startDate;
    private Date endDate;
    

    public AdvertisementCreateNewReq() {
    }

    public AdvertisementCreateNewReq(Advertisement advertisement, String email, String password, String ccNum, Date startDate, Date endDate) {
        this.advertisement = advertisement;
        this.email = email;
        this.password = password;
        this.ccNum = ccNum;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Advertisement getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(Advertisement advertisement) {
        this.advertisement = advertisement;
    }

    public String getCcNum() {
        return ccNum;
    }

    public void setCcNum(String ccNum) {
        this.ccNum = ccNum;
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
