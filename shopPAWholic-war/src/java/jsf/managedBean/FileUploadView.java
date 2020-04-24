package jsf.managedBean;

import ejb.session.stateless.UserSessionBeanLocal;
import entity.User;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javax.ejb.EJB;
import javax.inject.Named;
//import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@Named(value = "fileUploadView")
@SessionScoped
public class FileUploadView implements Serializable {

    @EJB(name = "UserSessionBeanLocal")
    private UserSessionBeanLocal userSessionBeanLocal;

    private UploadedFile file;
    private final List<String> imagePaths;

    private final AtomicInteger atomicImportFileNumber = new AtomicInteger();
    private Integer importFileNumber;

    public FileUploadView() {
        imagePaths = new ArrayList<>();
    }

    public Integer getImportFileNumber() {
        return importFileNumber;
    }

    public void setImportFileNumber(Integer importFileNumber) {
        this.importFileNumber = importFileNumber;
        atomicImportFileNumber.set(importFileNumber);
    }

    public void handleFileUpload(FileUploadEvent event) {
        User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
        try {
            String destination = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("alternatedocroot_1") + System.getProperty("file.separator");
            String secDest = "Seller"
                    + System.getProperty("file.separator") 
                    + user.getUserId()
                    + System.getProperty("file.separator")
                    + "Listing"
                    + System.getProperty("file.separator");
            File newPath = new File(destination + secDest);
            // advertisementID
            newPath.mkdirs();
            System.err.println("********** FileUploadView.handleFileUpload(): File name: " + event.getFile().getFileName());
            System.err.println("********** FileUploadView.handleFileUpload(): newFilePath: " + newPath);

            File file = new File(newPath + "/" + event.getFile().getFileName());
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            //Creates a FileOutputStream to write to the file represented by the specified file

            int a;
            int BUFFER_SIZE = 8192;
            byte[] buffer = new byte[BUFFER_SIZE];

            InputStream inputStream = event.getFile().getInputstream();
            //This getInputStream() method of the uploadedFile represents the file content
            imagePaths.add(secDest + event.getFile().getFileName());

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
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "File uploaded successfully", ""));
        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "File upload error: " + ex.getMessage(), ""));
        }
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

}
