package org.eclipse.jakarta.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PersistenceContext;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.jakarta.model.ReservationDetailsRepository;

public class Cart implements Serializable{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@PersistenceContext
	@Column(nullable = true)

	private Guest guest;
	
	private int cartSize;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToMany
	@Column(nullable = true)
	private List<RoomReservation> roomReservations  = new ArrayList<RoomReservation>();
	private double total;


	public Cart(EntityManager em, Guest guest, String price, int cartSize, List<RoomReservation> roomReservations,
			double total) {
		super();
	
		this.guest = guest;
	
		this.cartSize = cartSize;
		this.roomReservations = roomReservations;
		this.total = total;
		cartSize += 1;
	}

	public Guest getGuest() {
		return guest;
	}



	public List<RoomReservation> getRoomReservations() {
		return roomReservations;
	}

	public void setRoomReservations(List<RoomReservation> roomReservations) {
		this.roomReservations = roomReservations;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public void setGuest(Guest guest) {
		this.guest = guest;
	}

	public int getCartSize() {
		return cartSize;
	}

	public void setCartSize(int cartSize) {
		this.cartSize = cartSize;
	}




}
