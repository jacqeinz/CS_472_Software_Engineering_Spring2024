package org.eclipse.jakarta.model.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;
import java.util.*;
@Entity
public class ReservationDetails implements Serializable {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	private List<String> Bookedlist = new ArrayList<String>();
	private Guest guest;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public List<String> getBookedlist() {
		return Bookedlist;
	}
	public void setBookedlist(List<String> bookedlist) {
		Bookedlist = bookedlist;
	}
	public Guest getGuest() {
		return guest;
	}
	public void setGuest(Guest guest) {
		this.guest = guest;
	}
}