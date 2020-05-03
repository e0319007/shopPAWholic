package jsf.managedBean;

import ejb.session.stateless.SellerSessionBeanLocal;
import entity.Customer;
import entity.Seller;
import entity.User;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

@Named(value = "viewUserManagedBean")
@ViewScoped
public class ViewUserManagedBean implements Serializable {

    @EJB(name = "SellerSessionBeanLocal")
    private SellerSessionBeanLocal sellerSessionBeanLocal;

    private User userToView;
    private Customer customerToView;
    private Seller sellerToView;

    public ViewUserManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {

    }

    public void verifyAdmin(ActionEvent event) {
        System.out.println("########################### " + sellerToView);
        sellerSessionBeanLocal.updateVerifiedAdmin(sellerToView);
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
}
