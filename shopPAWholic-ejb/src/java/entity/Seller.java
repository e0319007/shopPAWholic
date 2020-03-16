
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Seller extends User implements Serializable {
    @NotNull
    @Size(max = 32)
    private String firstName;
    @NotNull
    @Size(max = 32)
    private String lastName;
    private double totalRating;
    
    @OneToMany(mappedBy = "seller")
    private List<Listing> listings;
    
    @OneToMany(mappedBy="seller")
    private List<OrderEntity> orders; 

    public Seller() {
        listings = new ArrayList<>();
        orders = new ArrayList<>();
    }

    public Seller(String firstName, String lastName) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public double getTotalRating() {
        return totalRating;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
}
