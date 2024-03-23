package org.eclipse.jakarta.model.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Entity;
import java.util.*;
import java.io.Serializable;

@Entity
public class Reservation implements Serializable {


	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String guest;
	@ManyToMany
	private List<RoomReservation> roomReservations  = new ArrayList<RoomReservation>();
	private List<String> paymentInformation = new ArrayList<String>();
	private List<String> occupants = new ArrayList<String>();
	public List<RoomReservation> getRoomReservation() {
		return roomReservations;
	}
	public void setRoomReservation(List<RoomReservation> roomReservations) {
		this.roomReservations = roomReservations;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}


	
	public String getGuest() {
		return guest;
	}
	public void setGuest(String guest) {
		this.guest = guest;
	}
	public List<String> getPaymentInformation() {
		return paymentInformation;
	}
	public void setPaymentInformation(List<String> paymentInformation) {
		this.paymentInformation = paymentInformation;
	}
	public List<String> getOccupants() {
		return occupants;
	}
	public void setOccupants(List<String> occupants) {
		this.occupants = occupants;
	} 
	

}
