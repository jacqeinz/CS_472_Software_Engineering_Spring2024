package spring2024.cs472.hotelwebsite.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//Goes in Cart to make individual reservations
@Entity
public class RoomReservation implements Serializable {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Guest guest;
	@ManyToOne
	private Room room;
	private int total;

	@ElementCollection
	private List<LocalDate> dates = new ArrayList<LocalDate >();
	
	
	public RoomReservation(Guest guest, Room room, List<LocalDate> dates) {
		super();
		this.guest = guest;
		this.room = room;
		this.dates.addAll(dates);
		this.total = dates.size() * room.getPrice();
	}

	public RoomReservation() {}


	public int getTotal() {
		return total;
	}


	public void setTotal(int total) {
		this.total = total;
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
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
	public List<LocalDate> getDates() {
		return dates;
	}
	public void setDates(List<LocalDate> dates) {
		this.dates = dates;
	}


}
