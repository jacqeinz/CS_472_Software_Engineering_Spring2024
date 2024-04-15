package spring2024.cs472.hotelwebsite.entities;

import jakarta.persistence.*;

import java.util.*;

@Entity
@DiscriminatorValue("Guest")
public class Guest extends Account {
    /**
     *
     */


    @Column(nullable = true)
    private String paymentInfo;

    @OneToOne( cascade = CascadeType.ALL)
    @JoinColumn
    private Cart cart = new Cart();

    @OneToMany(fetch = FetchType.EAGER)
    private List<ReservationDetails> currentReservations = new ArrayList<ReservationDetails>();

    @OneToMany(fetch = FetchType.EAGER)
    private List<ReservationDetails> pastReservations = new ArrayList<ReservationDetails>();



    public Guest(String name, String homeAddress, String dateOfBirth, String email, String phoneNumber,
                 String userName, String userPassword, String paymentInfo) {
        super(name, homeAddress, dateOfBirth, email, phoneNumber, userName, userPassword);
        this.paymentInfo = paymentInfo;
        //this.cart = cart;
        //this.currentReservations = currentReservations;
        //this.pastReservations = pastReservations;
    }

    public Guest() {}

    public List<ReservationDetails> getCurrentReservations() {
        return currentReservations;
    }
    public void setCurrentReservations(List<ReservationDetails> currentReservations) {
        this.currentReservations = currentReservations;
    }
    public void addCurrentReservation(ReservationDetails currentReservation) {
        this.currentReservations.add(currentReservation);
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





}
