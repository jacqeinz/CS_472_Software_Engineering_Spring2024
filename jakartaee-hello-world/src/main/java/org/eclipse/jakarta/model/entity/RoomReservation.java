package org.eclipse.jakarta.model.entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import org.eclipse.jakarta.model.entity.Reservation;
import org.eclipse.jakarta.model.entity.Room;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Entity
public class RoomReservation implements Serializable {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String guest;
	private Room room;
	private String roomNumber;
	private List<Date> dates = new ArrayList<Date>();
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
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
	public String getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}
	public List<Date> getDates() {
		return dates;
	}
	public void setDates(List<Date> dates) {
		this.dates = dates;
	}


}
