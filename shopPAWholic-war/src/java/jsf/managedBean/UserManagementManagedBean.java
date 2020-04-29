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
import util.exception.SellerNotFoundException;
import util.exception.UserNotFoundException;
import util.security.CryptographicHelper;

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

    private User currentUser;
    private Customer currentCustomer;
    private Seller currentSeller;

    private User userToView;
    private Customer customerToView;
    private Seller sellerToView;

    private Customer selectedCustomerToUpdate;
    private Long customerIdUpdate;

    private Seller selectedSellerToUpdate;
    private Long sellerIdUpdate;

    private User selectedUserToUpdate;

    //changePassword
    private String currentPassword;
    private String newPassword;

    public UserManagementManagedBean() {
        currentUser = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
        if (currentUser instanceof Customer) {
            currentCustomer = (Customer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
        } else {
            currentSeller = (Seller) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
        }
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

    public void updateSeller(ActionEvent event) {

        try {
            sellerSessionBeanLocal.updateSeller(selectedSellerToUpdate);

        } catch (SellerNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating seller: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public void updateUser(ActionEvent event) {

        try {
            userSessionBeanLocal.updateUser(selectedUserToUpdate);

        } catch (UserNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating user: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public void checkCurrentPassword() {
        String passwordHash = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(currentPassword + currentUser.getSalt()));
        System.out.println("############################ "+passwordHash);
        System.out.println("############################ "+currentUser.getPassword());
        if (!currentUser.getPassword().equals(passwordHash)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wrong current password!", null));

        }
    }

    public void doUpdateUser(ActionEvent event) {
        selectedUserToUpdate = (User) event.getComponent().getAttributes().get("userToUpdate");
    }

    public void doUpdateSeller(ActionEvent event) {
        selectedSellerToUpdate = (Seller) event.getComponent().getAttributes().get("sellerToUpdate");
    }

    public void doUpdateCustomer(ActionEvent event) {
        selectedCustomerToUpdate = (Customer) event.getComponent().getAttributes().get("customerToUpdate");
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

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser() {
        this.currentUser = currentUser;
    }

    public Customer getCurrentCustomer() {
        return currentCustomer;
    }

    public Seller getCurrentSeller() {
        return currentSeller;
    }

    public void setCurrentCustomer(Customer currentCustomer) {
        this.currentCustomer = currentCustomer;
    }

    public void setCurrentSeller(Seller currentSeller) {
        this.currentSeller = currentSeller;
    }

    public Seller getSelectedSellerToUpdate() {
        return selectedSellerToUpdate;
    }

    public void setSelectedSellerToUpdate(Seller selectedSellerToUpdate) {
        this.selectedSellerToUpdate = selectedSellerToUpdate;
    }

    public Long getSellerIdUpdate() {
        return sellerIdUpdate;
    }

    public void setSellerIdUpdate(Long sellerIdUpdate) {
        this.sellerIdUpdate = sellerIdUpdate;
    }

    public User getSelectedUserToUpdate() {
        return selectedUserToUpdate;
    }

    public void setSelectedUserToUpdate(User selectedUserToUpdate) {
        this.selectedUserToUpdate = selectedUserToUpdate;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
