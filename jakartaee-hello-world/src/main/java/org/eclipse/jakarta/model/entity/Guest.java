package org.eclipse.jakarta.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.*;
@Entity
@Table(name="guest")
public class Guest extends Account {
    /**
	 * 
	 */



	private String paymentInfo;
	private Cart cart;
	@ManyToMany
	private List<Reservation> currentReservations = new ArrayList<Reservation>();;
	private List<Reservation> pastReservations = new ArrayList<Reservation>();
	

	public List<Reservation> getCurrentReservations() {
		return currentReservations;
	}
	public void setCurrentReservations(List<Reservation> currentReservations) {
		this.currentReservations = currentReservations;
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
	public List<Reservation> getPastReservations() {
		return pastReservations;
	}
	public void setPastReservations(List<Reservation> pastReservations) {
		this.pastReservations = pastReservations;
	}

	
	
	


 }