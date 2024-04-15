package org.eclipse.jakarta.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.*;
@Entity


public class Guest extends Account {
    /**
	 * 
	 */


	@Column(nullable = true)
	private String paymentInfo;
	@Column(nullable = true)
	private Cart cart;
	@ManyToMany
	@Column(nullable = true)
	private List<ReservationDetails> currentReservations = new ArrayList<ReservationDetails>();
	@Column(nullable = true)
	@ManyToMany
	private List<ReservationDetails> pastReservations = new ArrayList<ReservationDetails>();
	


	public Guest(String paymentInfo, Cart cart, List<ReservationDetails> currentReservations,
			List<ReservationDetails> pastReservations) {
		super();
		this.paymentInfo = paymentInfo;
		this.cart = cart;
		this.currentReservations = currentReservations;
		this.pastReservations = pastReservations;
	}

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

	
	


 }