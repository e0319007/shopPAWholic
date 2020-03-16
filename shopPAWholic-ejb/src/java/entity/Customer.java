
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
    
    @OneToMany(mappedBy = "customer")
    private List<BilingDetail> billingDetails;
    @OneToMany(mappedBy = "customer")
    private List<OrderEntity> orders;
    @OneToOne
    private Cart cart;
    @OneToMany(mappedBy = "customer")
    private List<Review> reviews;
    @OneToMany(mappedBy = "customer")
    private List<ForumPost> forumPosts;
    @OneToMany(mappedBy = "customer")
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

    public List<BilingDetail> getBillingDetails() {
        return billingDetails;
    }

    public void setBillingDetails(List<BilingDetail> billingDetails) {
        this.billingDetails = billingDetails;
    }

    public List<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderEntity> orders) {
        this.orders = orders;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<ForumPost> getForumPosts() {
        return forumPosts;
    }

    public void setForumPosts(List<ForumPost> forumPosts) {
        this.forumPosts = forumPosts;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
