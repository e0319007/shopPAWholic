package jsf.managedBean;

import ejb.session.stateless.CustomerSessionBeanLocal;
import ejb.session.stateless.SellerSessionBeanLocal;
import ejb.session.stateless.UserSessionBeanLocal;
import entity.Customer;
import entity.Seller;
import entity.User;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import util.exception.CustomerNotFoundException;
import util.exception.InputDataValidationException;

@Named(value = "userManagementManagedBean")
@ViewScoped
public class UserManagementManagedBean implements Serializable {

    @EJB(name = "SellerSessionBeanLocal")
    private SellerSessionBeanLocal sellerSessionBeanLocal;

    @EJB(name = "CustomerSessionBeanLocal")
    private CustomerSessionBeanLocal customerSessionBeanLocal;

    @EJB(name = "UserSessionBeanLocal")
    private UserSessionBeanLocal userSessionBeanLocal;

    @Inject
    private ViewUserManagedBean viewUserManagedBean;

    private List<User> userList;
    private List<User> filteredUserList;

    private List<Customer> customerList;
    private List<Customer> filteredCustomerList;

    private List<Seller> sellerList;
    private List<Seller> filteredSellerList;

    private User selectedUserToView;
    private Customer selectedCustomerToView;
    private Seller selectedSellerrToView;

    private User userToView;
    private Customer customerToView;
    private Seller sellerToView;

    private Customer selectedCustomerToUpdate;
    private Long customerIdUpdate;

    public UserManagementManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        setUserList(userSessionBeanLocal.retrieveAllUsers());
        setCustomerList(customerSessionBeanLocal.retrieveAllCustomers());
        setSellerList(sellerSessionBeanLocal.retrieveAllSellers());
    }

    public void viewUserDetails(ActionEvent event) throws IOException {
        Long userIdToView = (Long) event.getComponent().getAttributes().get("userId");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("userIdToView", userIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect("adminViewUserDetails.xhtml");
    }

    public void updateCustomer(ActionEvent event) throws InputDataValidationException {

        try {
            customerSessionBeanLocal.updateCustomer(selectedCustomerToUpdate);

        } catch (CustomerNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating customer: " + ex.getMessage(), null));
        }
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public List<Seller> getSellerList() {
        return sellerList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }

    public void setSellerList(List<Seller> sellerList) {
        this.sellerList = sellerList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<User> getFilteredUserList() {
        return filteredUserList;
    }

    public void setFilteredUserList(List<User> filteredUserList) {
        this.filteredUserList = filteredUserList;
    }

    public User getSelectedUserToView() {
        return selectedUserToView;
    }

    public void setSelectedUserToView(User selectedUserToView) {
        this.selectedUserToView = selectedUserToView;
    }

    public ViewUserManagedBean getViewUserManagedBean() {
        return viewUserManagedBean;
    }

    public void setViewUserManagedBean(ViewUserManagedBean viewUserManagedBean) {
        this.viewUserManagedBean = viewUserManagedBean;
    }

    public Customer getSelectedCustomerToView() {
        return selectedCustomerToView;
    }

    public Seller getSelectedSellerrToView() {
        return selectedSellerrToView;
    }

    public void setSelectedCustomerToView(Customer selectedCustomerToView) {
        this.selectedCustomerToView = selectedCustomerToView;
    }

    public void setSelectedSellerrToView(Seller selectedSellerrToView) {
        this.selectedSellerrToView = selectedSellerrToView;
    }

    public List<Customer> getFilteredCustomerList() {
        return filteredCustomerList;
    }

    public List<Seller> getFilteredSellerList() {
        return filteredSellerList;
    }

    public void setFilteredCustomerList(List<Customer> filteredCustomerList) {
        this.filteredCustomerList = filteredCustomerList;
    }

    public void setFilteredSellerList(List<Seller> filteredSellerList) {
        this.filteredSellerList = filteredSellerList;
    }

    public User getUserToView() {
        return userToView;
    }

    public void setUserToView(User userToView) {
        this.userToView = userToView;
    }

    public Customer getCustomerToView() {
        return customerToView;
    }

    public Seller getSellerToView() {
        return sellerToView;
    }

    public void setCustomerToView(Customer customerToView) {
        this.customerToView = customerToView;
    }

    public void setSellerToView(Seller sellerToView) {
        this.sellerToView = sellerToView;
    }

    public Customer getSelectedCustomerToUpdate() {
        return selectedCustomerToUpdate;
    }

    public void setSelectedCustomerToUpdate(Customer selectedCustomerToUpdate) {
        this.selectedCustomerToUpdate = selectedCustomerToUpdate;
    }

    public Long getCustomerIdUpdate() {
        return customerIdUpdate;
    }

    public void setCustomerIdUpdate(Long customerIdUpdate) {
        this.customerIdUpdate = customerIdUpdate;
    }

}
