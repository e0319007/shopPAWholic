package jsf.managedBean;

import entity.Customer;
import entity.Seller;
import entity.User;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

@Named(value = "viewUserManagedBean")
@ViewScoped
public class ViewUserManagedBean implements Serializable {
    
    private User userToView;
    private Customer customerToView;
    private Seller sellerToView;
    
    public ViewUserManagedBean() {
    }
    
    @PostConstruct
    public void postConstruct(){
        
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
