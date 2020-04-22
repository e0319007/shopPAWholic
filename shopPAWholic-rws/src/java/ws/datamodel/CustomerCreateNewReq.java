/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

/**
 *
 * @author Joanna Ng
 */



import entity.Customer;
import java.util.Date;


public class CustomerCreateNewReq {
    private Customer customer;
    private String name;
    private String email;
    private String password;
    private String contactNumber;
    private Date accCreatedDate;

    public CustomerCreateNewReq() {
    }

    public CustomerCreateNewReq(Customer customer, String name, String email, String password, String contactNumber, Date accCreatedDate) {
        this.customer = customer;
        this.name = name;
        this.email = email;
        this.password = password;
        this.contactNumber = contactNumber;
        this.accCreatedDate = accCreatedDate;
    }

    /**
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the contactNumber
     */
    public String getContactNumber() {
        return contactNumber;
    }

    /**
     * @param contactNumber the contactNumber to set
     */
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    /**
     * @return the accCreatedDate
     */
    public Date getAccCreatedDate() {
        return accCreatedDate;
    }

    /**
     * @param accCreatedDate the accCreatedDate to set
     */
    public void setAccCreatedDate(Date accCreatedDate) {
        this.accCreatedDate = accCreatedDate;
    }
    
    
    
}
