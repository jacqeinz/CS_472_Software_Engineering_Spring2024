package spring2024.cs472.hotelwebsite.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Entity
public class Cart implements Serializable{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@OneToOne
	private Guest guest;

	protected Cart() {}

	public Long getId() {
		return id;
	}

	@ManyToMany
	@Column(nullable = true)
	private List<RoomReservation> roomReservations  = new ArrayList<RoomReservation>();
	private double total = 0;


	public Cart(Guest guest) {
		super();
	
		this.guest = guest;
	}

	public Guest getGuest() {
		return guest;
	}



	public List<RoomReservation> getRoomReservations() {
		return roomReservations;
	}

	public void setRoomReservations(List<RoomReservation> roomReservations) {
		this.roomReservations = roomReservations;
		total = 0;
		for(RoomReservation roomReservation : roomReservations) {
			total += roomReservation.getTotal();
		}
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public void setGuest(Guest guest) {
		this.guest = guest;
	}

	public int getCartSize() {
		return roomReservations.size();
	}

	public void addRoomReservations(List<RoomReservation> roomReservations) {
		this.roomReservations.addAll(roomReservations);
		for(RoomReservation roomReservation : roomReservations) {
			total += roomReservation.getTotal();
		}
	}

	public void emptyCart(){
		this.roomReservations.clear();
		this.total = 0;
	}

}
