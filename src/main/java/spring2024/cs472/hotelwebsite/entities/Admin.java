/**
 * Admin.java
 */
package spring2024.cs472.hotelwebsite.entities;

// Imports necessary for the class
import jakarta.persistence.*;
import jakarta.persistence.Table;
import java.util.*;

/**
 * Represents an admin account in the system.
 * Extends the Account class.
 *
 * @author Team ABCFG
 */
@Entity
@DiscriminatorValue("Admin")
public class Admin extends Account {

    // Attributes
    private int employeeId;

    @OneToOne( cascade = CascadeType.ALL)
    @JoinColumn
    private Cart cart = new Cart(); // Add this line

    /**
     * Constructs an admin account.
     */
    public Admin() {}

    /**
     * Retrieves the employee ID of the admin.
     *
     * @return The employee ID of the admin.
     */
    public int getEmployeeId() {
        return employeeId;
    }

    /**
     * Sets the employee ID of the admin.
     *
     * @param employeeId The employee ID of the admin.
     */
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * Constructs an admin account with the given parameters.
     *
     * @param isAdmin     The boolean indicating if the account is an admin account.
     * @param employeeId  The employee ID of the admin.
     */
    public Admin(boolean isAdmin, int employeeId) {
        super();
        this.employeeId = employeeId;
    }

    // Add these getter and setter methods for the cart
    public Cart getCart() {
        return cart;
    }
    public void setCart(Cart cart) {
        this.cart = cart;
    }
}

