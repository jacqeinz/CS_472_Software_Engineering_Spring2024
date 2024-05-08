/**
 * Guest.java
 */
package spring2024.cs472.hotelwebsite.entities;

// Imports necessary for the class
import jakarta.persistence.*;
import java.util.*;

/**
 * This class represents a Guest entity in the hotel system, extending the Account class.
 * It contains attributes and methods specific to a guest user.
 *
 * @author Team ABCFG
 */
@Entity
@DiscriminatorValue("Guest")
public class Guest extends Account {

    // Attributes
    @Column(nullable = true)
    private String paymentInfo;

    @OneToOne( cascade = CascadeType.ALL)
    @JoinColumn
    private Cart cart = new Cart();

    @OneToMany(fetch = FetchType.EAGER)
    private List<ReservationDetails> currentReservations = new ArrayList<ReservationDetails>();

    @OneToMany(fetch = FetchType.EAGER)
    private List<ReservationDetails> pastReservations = new ArrayList<ReservationDetails>();

    /**
     * Default constructor.
     */
    public Guest() {}

    /**
     * Parameterized constructor.
     *
     * @param name          The name of the guest.
     * @param homeAddress   The home address of the guest.
     * @param dateOfBirth   The date of birth of the guest.
     * @param email         The email address of the guest.
     * @param phoneNumber   The phone number of the guest.
     * @param userName      The username of the guest.
     * @param userPassword  The password of the guest.
     * @param paymentInfo   The payment information of the guest.
     */
    public Guest(String name, String homeAddress, String dateOfBirth, String email, String phoneNumber,
                 String userName, String userPassword, String paymentInfo) {
        super(name, homeAddress, dateOfBirth, email, phoneNumber, userName, userPassword);
        this.paymentInfo = paymentInfo;
        //this.cart = cart;
        //this.currentReservations = currentReservations;
        //this.pastReservations = pastReservations;
    }

    /**
     * Adds a new reservation details to the list of current reservations for this guest.
     *
     * @param currentReservation The reservation details to add.
     */
    public void addCurrentReservation(ReservationDetails currentReservation) {
        this.currentReservations.add(currentReservation);
    }

    // Getters and setters

    public List<ReservationDetails> getCurrentReservations() {
        return currentReservations;
    }
    public void setCurrentReservations(List<ReservationDetails> currentReservations) {
        this.currentReservations = currentReservations;
    }

    public List<ReservationDetails> getPastReservations() {
        return pastReservations;
    }
    public void setPastReservations(List<ReservationDetails> pastReservations) {
        this.pastReservations = pastReservations;
    }

    public String getPaymentInfo() {
        return paymentInfo;
    }
    public void setPaymentInfo(String paymentInfo) {
        this.paymentInfo = paymentInfo;
    }
    public Cart getCart() {
        return cart;
    }
    public void setCart(Cart cart) {
        this.cart = cart;
    }
    public String getName(){
        return super.getName();
    }
}
