package jsf.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

@Named(value = "searchEngineFilter")
@ViewScoped
public class SearchEngineFilter implements Serializable {

    private List<String> items = new ArrayList<>();
    private String selectedItem;

    public SearchEngineFilter() {
    }

    @PostConstruct
    public void postInit() {
        items.add("Items");
        items.add("Users");
    }

    public String getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(String selectedItem) {
        this.selectedItem = selectedItem;
    }

    public List<String> getItems() {
        return items;
    }

}