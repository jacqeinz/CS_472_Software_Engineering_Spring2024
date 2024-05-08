/**
 * Cart.java
 */
package spring2024.cs472.hotelwebsite.entities;

// Imports necessary for the class
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Represents a shopping cart entity associated with a guest.
 * Allows guests to add room reservations to their cart.
 *
 * @author Team ABCFG
 */
@Entity
public class Cart implements Serializable {

	// Attributes
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@OneToOne
	private Guest guest;

	@ManyToMany(fetch = FetchType.EAGER)
	@Column(nullable = true)
	private List<RoomReservation> roomReservations  = new ArrayList<RoomReservation>();

	private double total = 0;

	/**
	 * Constructs an empty cart.
	 */
	protected Cart() {}

	/**
	 * Constructs a cart associated with a guest.
	 *
	 * @param guest The guest associated with the cart.
	 */
	public Cart(Guest guest) {
		super();
		this.guest = guest;
	}

	/**
	 * Retrieves the ID of the cart.
	 *
	 * @return The ID of the cart.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Retrieves the guest associated with the cart.
	 *
	 * @return The guest associated with the cart.
	 */
	public Guest getGuest() {
		return guest;
	}

	/**
	 * Retrieves the list of room reservations in the cart.
	 *
	 * @return The list of room reservations in the cart.
	 */
	public List<RoomReservation> getRoomReservations() {
		return roomReservations;
	}

	/**
	 * Sets the list of room reservations in the cart and updates the total price.
	 *
	 * @param roomReservations The list of room reservations to be set in the cart.
	 */
	public void setRoomReservations(List<RoomReservation> roomReservations) {
		this.roomReservations = roomReservations;
		total = 0;
		for(RoomReservation roomReservation : roomReservations) {
			total += roomReservation.getTotal();
		}
	}

	/**
	 * Retrieves the total price of all room reservations in the cart.
	 *
	 * @return The total price of all room reservations in the cart.
	 */
	public double getTotal() {
		return total;
	}

	/**
	 * Sets the total price of all room reservations in the cart.
	 *
	 * @param total The total price of all room reservations in the cart.
	 */
	public void setTotal(double total) {
		this.total = total;
	}

	/**
	 * Sets the guest associated with the cart.
	 *
	 * @param guest The guest to be associated with the cart.
	 */
	public void setGuest(Guest guest) {
		this.guest = guest;
	}

	/**
	 * Retrieves the number of room reservations in the cart.
	 *
	 * @return The number of room reservations in the cart.
	 */
	public int getCartSize() {
		return roomReservations.size();
	}

	/**
	 * Adds room reservations to the cart and updates the total price.
	 *
	 * @param roomReservations The room reservations to be added to the cart.
	 */
	public void addRoomReservations(List<RoomReservation> roomReservations) {
		this.roomReservations.addAll(roomReservations);
		for(RoomReservation roomReservation : roomReservations) {
			total += roomReservation.getTotal();
		}
	}

	/**
	 * Empties the cart by removing all room reservations and resetting the total price to 0.
	 */
	public void emptyCart(){
		this.roomReservations.clear();
		this.total = 0;
	}

	/**
	 * Calculates the total price of all room reservations in the cart.
	 */
	private void calculateTotal() {
		total = roomReservations.stream().mapToDouble(RoomReservation::getTotal).sum();
	}
}
