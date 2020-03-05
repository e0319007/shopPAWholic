package entity;

import java.io.Serializable;
import javax.persistence.Entity;
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
}
