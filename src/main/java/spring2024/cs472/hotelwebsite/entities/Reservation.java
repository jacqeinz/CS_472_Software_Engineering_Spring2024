/**
 * Reservation.java
 */
package spring2024.cs472.hotelwebsite.entities;

// Imports necessary for the class
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a reservation.
 * This class is used for graphical display.
 * It contains information about the guest, dates, and room associated with the reservation.
 *
 * @author Team ABCFG
 */
@Entity
public class Reservation implements Serializable {

	// Attributes
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Guest guest;

	@ElementCollection
	private List<String> dates = new ArrayList<String>();

	@ManyToOne
	private Room room;

	/**
	 * Constructs a Reservation object with specified attributes.
	 *
	 * @param id The ID of the reservation.
	 * @param guest The guest associated with the reservation.
	 * @param dates The dates of the reservation.
	 * @param room The room reserved in the reservation.
	 */
	public Reservation(Long id, Guest guest, List<String> dates, Room room) {
		this.id = id;
		this.guest = guest;
		this.dates = dates;
		this.room = room;
	}

	/**
	 * Default constructor.
	 */
	protected Reservation() {}

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
	 * @return The guest associated with the reservation.
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
	 * Retrieves the dates of the reservation.
	 *
	 * @return The dates of the reservation.
	 */
	public List<String> getDates() {
		return dates;
	}

	/**
	 * Sets the dates of the reservation.
	 *
	 * @param dates The dates to set.
	 */
	public void setDates(List<String> dates) {
		this.dates = dates;
	}

	/**
	 * Retrieves the room reserved in the reservation.
	 *
	 * @return The room reserved in the reservation.
	 */
	public Room getRoom() {
		return room;
	}

	/**
	 * Sets the room reserved in the reservation.
	 *
	 * @param room The room to set.
	 */
	public void setRoom(Room room) {
		this.room = room;
	}
}
