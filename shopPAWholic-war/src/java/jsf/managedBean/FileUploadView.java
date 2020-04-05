package jsf.managedBean;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author yeeqinghew
 */
@Named(value = "fileUploadView")
@RequestScoped
public class FileUploadView {

    UploadedFile file;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void fileUploadListener(FileUploadEvent e) {
        System.out.println("********* fileUploadListener");
        // Get uploaded file from the FileUploadEvent
        this.file = e.getFile();
        // Print out the information of the file
        System.out.println("Uploaded File Name Is :: " + file.getFileName() + " :: Uploaded File Size :: " + file.getSize());
        // Add message
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("File Uploaded Successfully"));
    }
//    public void handleFileUpload(FileUploadEvent event) {
//        FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
//        FacesContext.getCurrentInstance().addMessage(null, message);
//    }

}
