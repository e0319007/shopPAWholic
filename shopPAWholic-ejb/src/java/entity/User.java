package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import util.security.CryptographicHelper;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)

public abstract class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotNull
    @Size(max = 32)
    private String name;

    @Email(message = "Email should be valid")
    @NotNull
    @Column(nullable = false, unique = true, length = 64)
    @Size(max = 64)
    private String email;

    private String contactNumber;

    @Column(columnDefinition = "CHAR(32) NOT NULL")
    @NotNull
    private String password;

    @Column(columnDefinition = "CHAR(32) NOT NULL")
    @NotNull
    private String salt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date accCreatedDate;

    @NotNull
    private boolean isFlag;

    public User() {
        this.salt = CryptographicHelper.getInstance().generateRandomString(32);
        isFlag = false;
    }

    public User(String name, String email, String contactNumber, String password, Date accCreatedDate) {
        this();
        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
        setPassword(password);
        this.accCreatedDate = accCreatedDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        if (password != null) {
            this.password = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(password + this.salt));
            System.out.println("password"+this.password);
        } else {
            this.password = null;
        }
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public boolean isIsFlag() {
        return isFlag;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the userId fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.User[ id=" + userId + " ]";
    }

    public Date getAccCreatedDate() {
        return accCreatedDate;
    }

    public void setAccCreatedDate(Date accCreatedDate) {
        this.accCreatedDate = accCreatedDate;
    }
}
