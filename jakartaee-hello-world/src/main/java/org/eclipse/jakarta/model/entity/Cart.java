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
	private Guest guest;
	private String price;
	private int cartSize;
	private ReservationDetails details;
	private List<LocalDate> dateList = new ArrayList<LocalDate>();

	public Cart(String p, String CheckIn, String CheckOut) {
		LocalDate in = LocalDate.parse(CheckIn);
		LocalDate out = LocalDate.parse(CheckOut);
		this.price = p;
		cartSize += 1;
		details = new ReservationDetails();
		dateList.add(in);
		dateList.add(out);

	}

	public Guest getGuest() {
		return guest;
	}



	public void setGuest(Guest guest) {
		this.guest = guest;
	}



	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}


	public int getCartSize() {
		return cartSize;
	}

	public void setCartSize(int cartSize) {
		this.cartSize = cartSize;
	}


	public LocalDate getCheckIn() {
		LocalDate in = this.dateList.get(0);
		return in;

	}

	public LocalDate getCheckOut() {
		LocalDate out = this.dateList.get(1);
		return out;

	}

}
