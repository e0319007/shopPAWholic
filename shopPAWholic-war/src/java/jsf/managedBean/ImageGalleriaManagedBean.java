/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

@Named (value="imageGalleriaManagedBean")
@RequestScoped
public class ImageGalleriaManagedBean {
     
    private List<String> images;
     
    @PostConstruct
    public void init() {
        images = new ArrayList<String>();
        for (int i = 1; i <= 3; i++) {
            images.add("../Web Pages/resources/image/advertismentDummy/nature" + i + ".jpg");
        }
    }
 
    public List<String> getImages() {
        return images;
    }
}
