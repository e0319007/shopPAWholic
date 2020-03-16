package entity;

import java.io.Serializable;
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
    private double commissionRate;
    @NotNull
    private boolean verified;
    
    @OneToMany(mappedBy = "serviceProvider")
    private List<Advertisement> advertisements; //sz
    
    @OneToMany(mappedBy = "serviceProvider")
    private List<Event> events; //sz

    public double getCommissionRate() {
        return commissionRate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setCommissionRate(double commissionRate) {
        this.commissionRate = commissionRate;
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
