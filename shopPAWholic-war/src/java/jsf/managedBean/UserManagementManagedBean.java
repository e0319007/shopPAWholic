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
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

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
    private List<Seller> sellerList;

    private User selectedUserToView;

    public UserManagementManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        setUserList(userSessionBeanLocal.retrieveAllUsers());
        setCustomerList(customerSessionBeanLocal.retrieveAllCustomer());
        setSellerList(sellerSessionBeanLocal.retrieveAllSellers());
    }

    public void viewUserDetails(ActionEvent event) throws IOException {
        Long userIdToView = (Long) event.getComponent().getAttributes().get("userId");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("userIdToView", userIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect("adminViewUserDetails.xhtml");
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

}
