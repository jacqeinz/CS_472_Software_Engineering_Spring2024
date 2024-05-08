/**
 * Account.java
 */
package spring2024.cs472.hotelwebsite.entities;

// Imports necessary for the class
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

/**
 * This class represents an account entity.
 * It serves as a base class for different types of accounts.
 *
 * @author Team ABCFG
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="account_type", discriminatorType = DiscriminatorType.STRING)
public class Account {

    // Attributes
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

    /**
     * Constructs an account with the given information.
     *
     * @param name          The name of the account holder.
     * @param homeAddress   The home address of the account holder.
     * @param dateOfBirth   The date of birth of the account holder.
     * @param email         The email address of the account holder.
     * @param phoneNumber   The phone number of the account holder.
     * @param userName      The username of the account holder.
     * @param userPassword  The password of the account holder.
     */
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

    /**
     * Default constructor.
     */
    public Account(){}

    /**
     * Constructs an account with the given information.
     *
     * @param name          The name of the account holder.
     * @param homeAddress   The home address of the account holder.
     * @param dateOfBirth   The date of birth of the account holder.
     * @param email         The email address of the account holder.
     * @param phoneNumber   The phone number of the account holder.
     * @param userName      The username of the account holder.
     * @param userPassword  The password of the account holder.
     * @param paymentInfo   The payment information of the account holder.
     */
    public Account(String name, String homeAddress, String dateOfBirth, String email, String phoneNumber, String userName, String userPassword, String paymentInfo) {
    }

    // Getters and setters for attributes
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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

    /**
     * Adds the information from the given account to this account.
     *
     * @param account The account whose information will be added.
     */
    public void add(Account account) {
        this.id += account.getId();
        this.Name += account.getName();
        this.homeAddress += account.getHomeAddress();
        this.dateOfBirth += account.getDateOfBirth();
        this.email += account.getEmail();
        this.phoneNumber += account.getPhoneNumber();
        this.userName += account.getUserName();
        this.userPassword += account.getUserPassword();

    }
}
