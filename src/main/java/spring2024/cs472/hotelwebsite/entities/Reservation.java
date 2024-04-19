package spring2024.cs472.hotelwebsite.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//Will be used for Graphical Display
@Entity
public class Reservation implements Serializable {
	

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Guest guest;

	@ElementCollection
	private List<String> dates = new ArrayList<String>();

	@ManyToOne
	private Room room;
	

	public Reservation(Long id, Guest guest, List<String> dates, Room room) {
	
		this.id = id;
		this.guest = guest;
		this.dates = dates;
		this.room = room;
	}

	protected Reservation() {

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




}
