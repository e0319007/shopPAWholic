/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import entity.BilingDetail;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Joanna Ng
 */
@Named(value = "viewBilingDetailManagedBean")
@ViewScoped
public class ViewBilingDetailManagedBean implements Serializable {
    
    private BilingDetail bilingDetailToView;
    
    /**
     * Creates a new instance of ViewBilingDetailManagedBean
     */
    public ViewBilingDetailManagedBean() {
        bilingDetailToView = new BilingDetail();
    }
    
    
     @PostConstruct
    public void postConstruct(){
        
    }

    /**
     * @return the bilingDetailToView
     */
    public BilingDetail getBilingDetailToView() {
        return bilingDetailToView;
    }

    /**
     * @param bilingDetailToView the bilingDetailToView to set
     */
    public void setBilingDetailToView(BilingDetail bilingDetailToView) {
        this.bilingDetailToView = bilingDetailToView;
    }
    
    
}
