/**
 * Room.java
 */
package spring2024.cs472.hotelwebsite.entities;

// Imports necessary for the class
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;

/**
 * Represents a room in the hotel.
 * This class contains attributes such as room number, room type, price, floor number, and occupancy status.
 *
 * @author Team ABCFG
 */
@Entity
public class Room implements Serializable {

	// Attributes
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String roomNumber;
	private String roomType;
	private int price;
	private int floorNumber;

	public Room() {}

	/**
	 * Constructs a Room object with specified attributes.
	 *
	 * @param roomNumber The room number.
	 * @param roomType The room type.
	 * @param price The price.
	 * @param floorNumber The floor number.
	 */
	public Room(String roomNumber, String roomType, int price, int floorNumber) {
		this.roomNumber = roomNumber;
		this.roomType = roomType;
		this.price = price;
		this.floorNumber = floorNumber;
	}

	/**
	 * Retrieves the room type.
	 *
	 * @return The room type.
	 */
	public String getRoomType() {
		return roomType;
	}

	/**
	 * Sets the room type.
	 *
	 * @param roomType The room type to set.
	 */
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	/**
	 * Retrieves the room number.
	 *
	 * @return The room number.
	 */
	public String getRoomNumber() {
		return roomNumber;
	}

	/**
	 * Sets the room number.
	 *
	 * @param roomNumber The room number to set.
	 */
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	/**
	 * Retrieves the floor number.
	 *
	 * @return The floor number.
	 */
	public int getFloorNumber() {
		return floorNumber;
	}

	/**
	 * Sets the floor number.
	 *
	 * @param floorNumber The floor number to set.
	 */
	public void setFloorNumber(int floorNumber) {
		this.floorNumber = floorNumber;
	}

	/**
	 * Retrieves the ID of the room.
	 *
	 * @return The ID of the room.
	 */
	public Long getId() {
		return id;
	}
  
	/**
	 * Sets the ID of the room.
	 *
	 * @param id The ID to set.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Retrieves the name of the room (room number).
	 *
	 * @return The name of the room.
	 */
	public String getName() {
		return roomNumber;
	}

	/**
	 * Sets the name of the room (room number).
	 *
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.roomNumber = name;
	}

	/**
	 * Retrieves the price of the room.
	 *
	 * @return The price of the room.
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * Sets the price of the room.
	 *
	 * @param price The price to set.
	 */
	public void setPrice(int price) {
		this.price = price;
	}
  
//	/**
//	 * Checks if the room is occupied.
//	 *
//	 * @return true if the room is occupied, false otherwise.
//	 */
//	public boolean isOccupied() {
//		return occupied;
//	}
//
//	/**
//	 * Sets the occupancy status of the room.
//	 *
//	 * @param occupied The occupancy status to set.
//	 */
//	public void setOccupied(boolean occupied) {
//		this.occupied = occupied;
//	}
}
