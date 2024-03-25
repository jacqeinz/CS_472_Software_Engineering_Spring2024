package org.eclipse.jakarta.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.util.*;
@Entity
public class Guest extends User {
    /**
	 * 
	 */

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long customerId;
	private String paymentInfo;
	private checkOutCart checkoutCart;
	@ManyToMany
	private List<Reservation> currentReservations = new ArrayList<Reservation>();;
	private List<Date> pastReservations = new ArrayList<Date>();
	
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public List<Reservation> getCurrentReservations() {
		return currentReservations;
	}
	public void setCurrentReservations(List<Reservation> currentReservations) {
		this.currentReservations = currentReservations;
	}
	public List<Date> getPastReservations() {
		return pastReservations;
	}
	public void setPastReservations(List<Date> pastReservations) {
		this.pastReservations = pastReservations;
	}
	public String getPaymentInfo() {
		return paymentInfo;
	}
	public void setPaymentInfo(String paymentInfo) {
		this.paymentInfo = paymentInfo;
	}
	public checkOutCart getCheckoutCart() {
		return checkoutCart;
	}
	public void setCheckoutCart(checkOutCart checkoutCart) {
		this.checkoutCart = checkoutCart;
	}
	
	
	
	


 }