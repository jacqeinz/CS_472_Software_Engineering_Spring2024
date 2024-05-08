/**
 * Confirmation.java
 */
package spring2024.cs472.hotelwebsite.entities;

// Imports necessary for the class
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a Confirmation entity.
 * It contains information about a confirmation including its ID, associated cart, and reservation details.
 * The confirmation ID is generated randomly.
 *
 * @author Team ABCFG
 */
public class Confirmation implements Serializable {

	// Attributes
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private Cart conCart;
	private String conID;
	@ManyToMany
	private List<ReservationDetails> reservationDetails  = new ArrayList<ReservationDetails>();

	/**
	 * Constructs a Confirmation object with the given cart.
	 * Generates a random confirmation ID.
	 *
	 * @param cart The cart associated with the confirmation.
	 */
	public Confirmation(Cart cart) {
		this.conCart=cart;
		conID=randomGenerator();
		
	}

	/**
	 * Returns the list of reservation details associated with the confirmation.
	 *
	 * @return The list of reservation details.
	 */
	public List<ReservationDetails> getReservationDetails() {
		return reservationDetails;
	}

	/**
	 * Sets the list of reservation details associated with the confirmation.
	 *
	 * @param reservationDetails The list of reservation details.
	 */
	public void setReservationDetails(List<ReservationDetails> reservationDetails) {
		this.reservationDetails = reservationDetails;
	}

	/**
	 * Generates a random alphanumeric string of length 9.
	 *
	 * @return The randomly generated string.
	 */
	public String randomGenerator() {
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
		         + "0123456789"
		         + "abcdefghijklmnopqrstuvxyz";
		StringBuilder sb = new StringBuilder(8);
		for (int i = 0; i < 9; i++) {  
			   int index = (int)(AlphaNumericString.length()* Math.random());
			   sb.append(AlphaNumericString.charAt(index)); 
		}
		return sb.toString();
	}

	/**
	 * Returns the ID of the confirmation.
	 *
	 * @return The confirmation ID.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Returns the cart associated with the confirmation.
	 *
	 * @return The cart.
	 */
	public Cart getConCart() {
		return conCart;
	}

	/**
	 * Sets the cart associated with the confirmation.
	 *
	 * @param conCart The cart to set.
	 */
	public void setConCart(Cart conCart) {
		this.conCart = conCart;
	}

	/**
	 * Returns the confirmation ID.
	 *
	 * @return The confirmation ID.
	 */
	public String getConID() {
		return conID;
	}

	/**
	 * Sets the confirmation ID.
	 *
	 * @param conID The confirmation ID to set.
	 */
	public void setConID(String conID) {
		this.conID = conID;
	}
}
