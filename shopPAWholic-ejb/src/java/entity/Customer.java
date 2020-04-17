package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity (name="Customer")
public class Customer extends User implements Serializable {

    @OneToMany(mappedBy = "customer")
    private List<BillingDetail> billingDetails;
    
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

    public Customer(String name, String email, String contactNumber, String password, Date accCreatedDate) {
        super(name, email, contactNumber, password, accCreatedDate);
    }

    public List<BillingDetail> getBillingDetails() {


        return billingDetails;
    }

    public void setBillingDetails(List<BillingDetail> billingDetails) {
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
