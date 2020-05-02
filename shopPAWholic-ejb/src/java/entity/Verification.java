package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Verification implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long verificationId;
    
    @NotNull
    private String filePath;
    
    public Verification() {
    }

    public Verification(String filePath) {
        this();
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public Long getVerificationId() {
        return verificationId;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setVerificationId(Long verificationId) {
        this.verificationId = verificationId;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (verificationId != null ? verificationId.hashCode() : 0);
        return hash;
    }
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Verification)) {
            return false;
        }
        Verification other = (Verification) object;
        if ((this.verificationId == null && other.verificationId != null) || (this.verificationId != null && !this.verificationId.equals(other.verificationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Verification[ id=" + verificationId + " ]";
    }
}
