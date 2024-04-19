package org.eclipse.jakarta.model.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Entity;
import java.util.*;
import java.io.Serializable;
//Will be used for Graphical Display
@Entity
public class Reservation implements Serializable {
	

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Guest guest;
	private List<String> dates = new ArrayList<String>();
	private Room room;
	

	public Reservation(Long id, Guest guest, List<String> dates, Room room) {
		super();
		this.id = id;
		this.guest = guest;
		this.dates = dates;
		this.room = room;
	}
	public Long getId() {
		return id;
	}
	public Guest getGuest() {
		return guest;
	}
	public void setGuest(Guest guest) {
		this.guest = guest;
	}

	public List<String> getDates() {
		return dates;
	}
	public void setDates(List<String> dates) {
		this.dates = dates;
	}
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
	public void setId(Long id) {
		this.id = id;
	}




}
