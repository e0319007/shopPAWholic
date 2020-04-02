package jsf.managedBean;

import entity.User;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

@Named(value = "viewUserManagedBean")
@ViewScoped
public class ViewUserManagedBean implements Serializable {
    
    private User userToView;
    
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
}
