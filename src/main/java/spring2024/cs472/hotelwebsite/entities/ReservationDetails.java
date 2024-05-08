/**
 * ReservationDetails.java
 */
package spring2024.cs472.hotelwebsite.entities;

// Imports necessary for the class
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents reservation details.
 * This class is stored for guests' viewing of reservations in their account.
 * It contains information about the guest, room reservations, payment information, and total cost.
 *
 * @author Team ABCFG
 */
@Entity
public class ReservationDetails implements Serializable {

	// Attributes
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Guest guest;
	@OneToMany(fetch = FetchType.EAGER)
	private List<RoomReservation> roomReservations = new ArrayList<RoomReservation>();
	private String paymentInformation;
	private double total = 0;

	/**
	 * Default constructor.
	 */
	protected ReservationDetails() {}

	/**
	 * Constructs a ReservationDetails object with specified attributes.
	 *
	 * @param guest The guest associated with the reservation details.
	 * @param reservations The room reservations.
	 * @param paymentInformation The payment information.
	 * @param total The total cost.
	 */
	public ReservationDetails(Guest guest, List<RoomReservation> reservations, String paymentInformation, double total) {
		super();
		this.guest = guest;
		this.roomReservations.addAll(reservations);
		this.paymentInformation = paymentInformation;
		this.total = total;
	}

	/**
	 * Retrieves the payment information.
	 *
	 * @return The payment information.
	 */
	public String getPaymentInformation() {
		return paymentInformation;
	}

	/**
	 * Sets the payment information.
	 *
	 * @param paymentInformation The payment information to set.
	 */
	public void setPaymentInformation(String paymentInformation) {
		this.paymentInformation = paymentInformation;
	}

	/**
	 * Retrieves the guest associated with the reservation details.
	 *
	 * @return The guest associated with the reservation details.
	 */
	public Guest getGuest() {
		return guest;
	}

	/**
	 * Retrieves the room reservations.
	 *
	 * @return The room reservations.
	 */
	public List<RoomReservation> getRoomReservations() {
		return roomReservations;
	}

	/**
	 * Adds a room reservation to the list.
	 *
	 * @param roomreservation The room reservation to add.
	 */
	public void addRoomReservation(RoomReservation roomreservation) {
		this.roomReservations.add(roomreservation);
	}

	/**
	 * Adds a list of room reservations to the existing list.
	 *
	 * @param reservations The list of room reservations to add.
	 */
	public void addRoomReservations(List<RoomReservation> reservations) {
		this.roomReservations.addAll(reservations);
	}

	/**
	 * Retrieves the ID of the reservation details.
	 *
	 * @return The ID of the reservation details.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the ID of the reservation details.
	 *
	 * @param id The ID to set.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Retrieves the total cost.
	 *
	 * @return The total cost.
	 */
	public double getTotal() {
		return total;
	}

	/**
	 * Sets the total cost.
	 *
	 * @param total The total cost to set.
	 */
	public void setTotal(double total) {
		this.total = total;
	}

	/**
	 * Retrieves the reservation dates from the room reservations.
	 *
	 * @return The list of reservation dates.
	 */
	public List<LocalDate> getReservationDates() {
		List<LocalDate> reservationDates = new ArrayList<>();
		for (RoomReservation roomReservation : roomReservations) {
			reservationDates = roomReservation.getDates();
		}
		return reservationDates;
	}
}