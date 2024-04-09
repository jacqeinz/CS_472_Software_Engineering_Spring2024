package com.business.entities;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.io.Serializable;
import java.util.*;
//Will be stored for Guests' viewing of reservations in their account
@Entity
public class ReservationDetails implements Serializable {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	@Column(nullable = true)
	private RoomReservation roomreservation;
	private String paymentInformation;
	//Generated by Cart

	public String getPaymentInformation() {
		return paymentInformation;
	}
	public ReservationDetails(Long id, RoomReservation roomreservation, String paymentInformation) {
		super();
		this.id = id;
		this.roomreservation = roomreservation;
		this.paymentInformation = paymentInformation;
	}
	public void setPaymentInformation(String paymentInformation) {
		this.paymentInformation = paymentInformation;
	}



	public RoomReservation getRoomreservation() {
		return roomreservation;
	}
	public void setRoomreservation(RoomReservation roomreservation) {
		this.roomreservation = roomreservation;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}


}