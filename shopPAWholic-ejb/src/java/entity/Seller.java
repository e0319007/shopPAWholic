package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity (name="Seller")
public class Seller extends User implements Serializable {
    
    @NotNull
    private boolean verified;

    private double totalRating;

    @OneToMany(mappedBy = "seller")
    private List<Listing> listings;

    @OneToMany(mappedBy = "seller")
    private List<OrderEntity> orders;
    
    @OneToMany(mappedBy = "seller")
    private List<Advertisement> advertisements; 

    @OneToMany(mappedBy = "seller")
    private List<Event> events; 
    
    @OneToMany(mappedBy="seller") 
    private List<BillingDetail> billingDetails;

    public Seller() {
        listings = new ArrayList<>();
        orders = new ArrayList<>();
        advertisements = new ArrayList<>();
        events = new ArrayList<>();
    }

    public Seller(String name, String email, String contactNumber, String password, Date accCreatedDate, boolean verified, double totalRating ) {
        super(name, email, contactNumber, password, accCreatedDate);
        this.verified = verified;
        this.totalRating = totalRating;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public double getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(double totalRating) {
        this.totalRating = totalRating;
    }

    public List<Listing> getListings() {
        return listings;
    }

    public void setListings(List<Listing> listings) {
        this.listings = listings;
    }

    public List<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderEntity> orders) {
        this.orders = orders;
    }

    public List<Advertisement> getAdvertisements() {
        return advertisements;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setAdvertisements(List<Advertisement> advertisements) {
        this.advertisements = advertisements;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<BillingDetail> getBillingDetails() {
        return billingDetails;
    }

    public void setBillingDetails(List<BillingDetail> billingDetails) {
        this.billingDetails = billingDetails;
    }
}
