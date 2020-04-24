/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;
import entity.Advertisement;
import java.util.Date;
import java.util.List;

/**
 *
 * @author shizhan
 */
public class AdvertisementCreateNewReq {

    private Advertisement advertisement;
    private String email;
    private String password;
    private Date startDate;
    private int startYear;
    private int startMth;
    private int startDay;
    private Date endDate;
    private int endYear;
    private int endMth;
    private int endDay;
    private String ccNum;
    private List<String> pictures;
    private String url;
    private String description;
    private String price;
    

    public AdvertisementCreateNewReq() {
    }

    public AdvertisementCreateNewReq(Advertisement advertisement, String email, String password, Date startDate, Date endDate, String ccNum, List<String> pictures, String url) {
        this.advertisement = advertisement;
        this.email = email;
        this.password = password;
        this.startDate = startDate;
        this.endDate = endDate;
        this.ccNum = ccNum;
        this.pictures = pictures;
        this.url = url;
        this.price = price;
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

    /**
     * @return the pictures
     */
    public List<String> getPictures() {
        return pictures;
    }

    /**
     * @param pictures the pictures to set
     */
    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the startYear
     */
    public int getStartYear() {
        return startYear;
    }

    /**
     * @param startYear the startYear to set
     */
    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    /**
     * @return the startMth
     */
    public int getStartMth() {
        return startMth;
    }

    /**
     * @param startMth the startMth to set
     */
    public void setStartMth(int startMth) {
        this.startMth = startMth;
    }

    /**
     * @return the startDay
     */
    public int getStartDay() {
        return startDay;
    }

    /**
     * @param startDay the startDay to set
     */
    public void setStartDay(int startDay) {
        this.startDay = startDay;
    }

    /**
     * @return the endYear
     */
    public int getEndYear() {
        return endYear;
    }

    /**
     * @param endYear the endYear to set
     */
    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    /**
     * @return the endMth
     */
    public int getEndMth() {
        return endMth;
    }

    /**
     * @param endMth the endMth to set
     */
    public void setEndMth(int endMth) {
        this.endMth = endMth;
    }

    /**
     * @return the endDay
     */
    public int getEndDay() {
        return endDay;
    }

    /**
     * @param endDay the endDay to set
     */
    public void setEndDay(int endDay) {
        this.endDay = endDay;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the price
     */
    public String getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(String price) {
        this.price = price;
    }
}
