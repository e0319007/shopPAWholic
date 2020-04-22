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

/**
 *
 * @author EileenLeong
 */
@Named(value = "eventPicturesManagedBean")
@RequestScoped
public class EventPicturesManagedBean {

    private List<String> pictures;

    @PostConstruct
    public void init() {
        pictures = new ArrayList<String>();
        for (int i = 1; i <= 3; i++) {
            pictures.add("petEvent" + i + ".jpg");
        }
    }

    public List<String> getPictures() {
        return pictures;
    }
    
}
