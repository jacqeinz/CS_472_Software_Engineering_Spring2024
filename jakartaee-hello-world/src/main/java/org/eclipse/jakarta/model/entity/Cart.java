package org.eclipse.jakarta.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
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

	@PersistenceContext
	private EntityManager em;
	private String name;
	private String price;
	private int cartSize;
	private ReservationDetails details;
	private List<LocalDate> list = new ArrayList<LocalDate>();

	public Cart(String p, String CheckIn, String CheckOut) {
		LocalDate in = LocalDate.parse(CheckIn);
		LocalDate out = LocalDate.parse(CheckOut);
		this.price = p;
		cartSize += 1;
		details = new ReservationDetails();
		list.add(in);
		list.add(out);

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getReservationDetails() {
		return details.getGuest();
	}

	public int getCartSize() {
		return cartSize;
	}

	public void setCartSize(int cartSize) {
		this.cartSize = cartSize;
	}

	public void setDetailsGuest(String guest) {
		details.setGuest(guest);
	}

	public String getDetailsGuest() {
		return this.details.getGuest();
	}

	public LocalDate getCheckIn() {
		LocalDate in = this.list.get(0);
		return in;

	}

	public LocalDate getCheckOut() {
		LocalDate out = this.list.get(1);
		return out;

	}

}
