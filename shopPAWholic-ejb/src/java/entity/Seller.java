/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Entity;
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
    @NotNull
    private double totalRating;

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
}
