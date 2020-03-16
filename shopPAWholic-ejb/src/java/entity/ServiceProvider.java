package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class ServiceProvider extends User implements Serializable {
    
    @NotNull
    @Size(max = 32)
    private String companyName;
    
    @NotNull
    private boolean verified;
    
    @OneToMany(mappedBy = "serviceProvider")
    private List<Advertisement> advertisements; //sz
    
    @OneToMany(mappedBy = "serviceProvider")
    private List<Event> events; //sz

    public ServiceProvider() {
        advertisements = new ArrayList<>();
        events = new ArrayList<>();
    }

    public ServiceProvider(String companyName, boolean verified) {
        this();
        this.companyName = companyName;
        this.verified = verified;
    }

    

    public String getCompanyName() {
        return companyName;
    }

    public boolean isVerified() {
        return verified;
    }

    

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public List<Advertisement> getAdvertisements() {
        return advertisements;
    }

    public void setAdvertisements(List<Advertisement> advertisements) {
        this.advertisements = advertisements;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
