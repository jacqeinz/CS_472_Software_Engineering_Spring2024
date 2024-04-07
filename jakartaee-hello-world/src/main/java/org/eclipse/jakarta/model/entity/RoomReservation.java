package org.eclipse.jakarta.model.entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import org.eclipse.jakarta.model.entity.Reservation;
import org.eclipse.jakarta.model.entity.Room;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//Goes in Cart to make individual reservations
@Entity
public class RoomReservation implements Serializable {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Guest guest;
	private Room room;
	private int total;
	@Column(nullable = true)
	private List<String> dates = new ArrayList<String>();
	
	
	public RoomReservation(Long id, Guest guest, Room room, int price, List<String> dates) {
		super();
		this.id = id;
		this.guest = guest;
		this.room = room;
		this.dates = dates;
	}


	public int getTotal() {
		return total;
	}


	public void setTotal(int total) {
		this.total = total;
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public List<String> getDates() {
		return dates;
	}
	public void setDates(List<String> dates) {
		this.dates = dates;
	}


}
