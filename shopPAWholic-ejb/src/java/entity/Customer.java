
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Customer extends User implements Serializable {
    
    @NotNull
    @Size(max = 32)
    private String firstName;
    @Column(nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    private String lastName;
    
    @OneToMany
    private List<BilingDetail> billingDetails;
    @OneToMany
    private List<Order> orders;
    @OneToOne
    private Cart cart;
    @OneToMany
    private List<Review> reviews;
    @OneToMany
    private List<ForumPost> forumPosts;
    @OneToMany
    private List<Comment> comments;

    public Customer() {
        billingDetails = new ArrayList<>();
        orders = new ArrayList<>();
        reviews = new ArrayList<>();
        forumPosts = new ArrayList<>();
        comments = new ArrayList<>();
    }

    public Customer(String firstName, String lastName) {
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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }    
}
