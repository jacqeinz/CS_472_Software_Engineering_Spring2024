package spring2024.cs472.hotelwebsite.entities;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="account_type",
        discriminatorType = DiscriminatorType.STRING)
public abstract class Account {


    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    protected Long id;
    private String Name;

    private String homeAddress;
    private String dateOfBirth;
    private String email;
    private String phoneNumber;
    private String userName;
    private String userPassword;

    public Account(String name, String homeAddress, String dateOfBirth, String email, String phoneNumber,
                   String userName, String userPassword) {
        this.Name = name;
        this.homeAddress = homeAddress;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.userName = userName;
        this.userPassword = userPassword;
    }
    public Account(){}

    public Long getId() {
        return id;
    }

    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public String getHomeAddress() {
        return homeAddress;
    }
    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }
    public String getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserPassword() {
        return userPassword;
    }
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }




}
