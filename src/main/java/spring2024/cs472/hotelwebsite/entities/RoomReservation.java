/**
 * RoomReservation.java
 */
package spring2024.cs472.hotelwebsite.entities;

// Imports necessary for the class
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents a reservation for a room.
 * This class contains attributes such as guest, room, total price, and dates.
 * Goes in Cart to make individual reservations.
 *
 * @author Team ABCFG
 */
@Entity
public class RoomReservation implements Serializable {

	// Attributes
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Guest guest;
	@ManyToOne
	private Room room;
	private int total;
	@ElementCollection(fetch = FetchType.EAGER)
	private List<LocalDate> dates = new ArrayList<LocalDate >();

	/**
	 * Default constructor.
	 */
	public RoomReservation() {}

	/**
	 * Constructs a RoomReservation object with specified attributes.
	 *
	 * @param guest The guest associated with the reservation.
	 * @param room The room reserved.
	 * @param dates The dates of the reservation.
	 */
	public RoomReservation(Guest guest, Room room, List<LocalDate> dates) {
		super();
		this.guest = guest;
		this.room = room;
		this.dates.addAll(dates);
		this.total = dates.size() * room.getPrice();
	}

	/**
	 * Retrieves the total price of the reservation.
	 *
	 * @return The total price.
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * Sets the total price of the reservation.
	 *
	 * @param total The total price to set.
	 */
	public void setTotal(int total) {
		this.total = total;
	}

	/**
	 * Retrieves the ID of the reservation.
	 *
	 * @return The ID of the reservation.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Retrieves the guest associated with the reservation.
	 *
	 * @return The guest.
	 */
	public Guest getGuest() {
		return guest;
	}

	/**
	 * Sets the guest associated with the reservation.
	 *
	 * @param guest The guest to set.
	 */
	public void setGuest(Guest guest) {
		this.guest = guest;
	}

	/**
	 * Retrieves the room reserved.
	 *
	 * @return The room.
	 */
	public Room getRoom() {
		return room;
	}

	/**
	 * Sets the room reserved.
	 *
	 * @param room The room to set.
	 */
	public void setRoom(Room room) {
		this.room = room;
	}

	/**
	 * Retrieves the dates of the reservation.
	 *
	 * @return The dates of the reservation.
	 */
	public List<LocalDate> getDates() {
		return dates;
	}

	/**
	 * Sets the dates of the reservation.
	 *
	 * @param dates The dates to set.
	 */
	public void setDates(List<LocalDate> dates) {
		this.dates = dates;
		setTotal(dates.size() * room.getPrice());
	}

	/**
	 * Sets the ID of the reservation.
	 *
	 * @param id The ID to set.
	 */
	public void setId(Long id) {
		this.id = id;
	}
}
