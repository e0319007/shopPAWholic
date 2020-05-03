package jsf.managedBean;

import ejb.session.stateless.AdvertisementSessionBeanLocal;
import ejb.session.stateless.CartSessionBeanLocal;
import ejb.session.stateless.CustomerSessionBeanLocal;
import ejb.session.stateless.EventSessionBeanLocal;
import ejb.session.stateless.ListingSessionBeanLocal;
import ejb.session.stateless.SellerSessionBeanLocal;
import ejb.session.stateless.UserSessionBeanLocal;
import entity.Customer;
import entity.Seller;
import entity.User;
import entity.Verification;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import util.exception.CustomerNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.SellerNotFoundException;
import util.security.CryptographicHelper;

@Named(value = "userManagementManagedBean")
@ViewScoped
public class UserManagementManagedBean implements Serializable {

    @EJB(name = "CartSessionBeanLocal")
    private CartSessionBeanLocal cartSessionBeanLocal;

    @EJB(name = "EventSessionBeanLocal")
    private EventSessionBeanLocal eventSessionBeanLocal;

    @EJB(name = "AdvertisementSessionBeanLocal")
    private AdvertisementSessionBeanLocal advertisementSessionBeanLocal;

    @EJB(name = "ListingSessionBeanLocal")
    private ListingSessionBeanLocal listingSessionBeanLocal;

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

    //for changePassword
    private String currentPassword;
    private String newPassword;

    //for verification
    private Boolean verified;
    private UploadedFile file;
    private String filePath;

    //for chart 
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

    public void checkCurrentPassword() {
        String passwordHash = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(currentPassword + getCurrentUser().getSalt()));
        if (!currentUser.getPassword().equals(passwordHash)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wrong current password!", null));
        }
    }

    public void currentAndNewCannotBeSame() {
        String newPasswordNew = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(newPassword + getCurrentUser().getSalt()));
        String currentPasswordNew = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(selectedUserToUpdate.getPassword() + getCurrentUser().getSalt()));

        if (newPasswordNew.equals(currentPasswordNew)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "New Password cannot be the same as Current Password!", null));
        }
    }

    public void changePassword(ActionEvent event) {
        selectedUserToUpdate = currentUser;
        userSessionBeanLocal.updateEmail(selectedUserToUpdate, newPassword);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "User's password" + " updated successfully", null));
    }

    public void verifyYourself(FileUploadEvent event) throws SellerNotFoundException {
        User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
        try {
            String destination = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("alternatedocroot_1") + System.getProperty("file.separator");
            String secDest = "Seller"
                    + System.getProperty("file.separator")
                    + user.getUserId()
                    + System.getProperty("file.separator")
                    + "Verification"
                    + System.getProperty("file.separator");
            File newPath = new File(destination + secDest);
            newPath.mkdirs();
            System.err.println("********** FileUploadView.handleFileUpload(): File name: " + event.getFile().getFileName());
            System.err.println("********** FileUploadView.handleFileUpload(): newFilePath: " + newPath);

            File uploadEventImage = new File(newPath + "/" + event.getFile().getFileName());
            FileOutputStream fileOutputStream = new FileOutputStream(uploadEventImage);
            //Creates a FileOutputStream to write to the file represented by the specified file

            int a;
            int BUFFER_SIZE = 8192;
            byte[] buffer = new byte[BUFFER_SIZE];

            InputStream inputStream = event.getFile().getInputstream();
            //This getInputStream() method of the uploadedFile represents the file content

            filePath = "http://localhost:8080/shopPAWholic-war/uploadedFiles/" + secDest + event.getFile().getFileName();

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
            Verification verification = new Verification(filePath);
            currentSeller.setVerification(verification);
            sellerSessionBeanLocal.updateVerification(currentSeller);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "File uploaded successfully", ""));
        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "File upload error: " + ex.getMessage(), ""));
        } catch (InputDataValidationException ex) {
            Logger.getLogger(UserManagementManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateUser(ActionEvent event) {
        userSessionBeanLocal.updateUser(selectedUserToUpdate);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "User " + " updated successfully", null));
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

    public String getNewPassword() {
        return newPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
}
