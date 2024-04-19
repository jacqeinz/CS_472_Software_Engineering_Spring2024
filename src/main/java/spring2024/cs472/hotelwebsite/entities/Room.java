package spring2024.cs472.hotelwebsite.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;


@Entity
public class Room implements Serializable {
    /**
	 * 
	 */

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomNumber;
    private String roomType;
    private int  price;
    private int floorNumber;

	public Room(){

	}

	public Room(String roomNumber, String roomType, int price, int floorNumber) {
		this.roomNumber = roomNumber;
		this.roomType = roomType;
		this.price = price;
		this.floorNumber = floorNumber;
	}
  
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public String getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}
	public int getFloorNumber() {
		return floorNumber;
	}
	public void setFloorNumber(int floorNumber) {
		this.floorNumber = floorNumber;
	}

	public Long getId() {
		return id;
	}

	
	public void setName(String name) {
		this.roomNumber = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}



 }