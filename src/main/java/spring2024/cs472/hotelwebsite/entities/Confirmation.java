package spring2024.cs472.hotelwebsite.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Confirmation implements Serializable {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private Cart conCart;
	private String conID;
	@ManyToMany
	private List<ReservationDetails> reservationDetails  = new ArrayList<ReservationDetails>();
	public Confirmation(Cart cart) {
		this.conCart=cart;
		conID=randomGenerator();
		
	}
	
	public List<ReservationDetails> getReservationDetails() {
		return reservationDetails;
	}

	public void setReservationDetails(List<ReservationDetails> reservationDetails) {
		this.reservationDetails = reservationDetails;
	}

	public String randomGenerator() {
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
		         + "0123456789"
		         + "abcdefghijklmnopqrstuvxyz";
		StringBuilder sb = new StringBuilder(8);
		for (int i = 0; i < 9; i++) {  
			   int index = (int)(AlphaNumericString.length()* Math.random());
			   sb.append(AlphaNumericString.charAt(index)); 
			  } 
			 
			  return sb.toString(); 
		
		
	}

	public Long getId() {
		return id;
	}

	public Cart getConCart() {
		return conCart;
	}

	public void setConCart(Cart conCart) {
		this.conCart = conCart;
	}

	public String getConID() {
		return conID;
	}

	public void setConID(String conID) {
		this.conID = conID;
	}

}
