package com.business.entities.repositories;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import com.business.entities.RoomReservation;


@Repository
public class RoomReservationRepository {

	   private final List<RoomReservation> roomReservations = new ArrayList<RoomReservation>();
	    
	    
	    
	    public RoomReservationRepository() {
	        super();
	    }
	    
	    
	    
	    public List<RoomReservation> findAll() {
	        return new ArrayList<RoomReservation>(this.roomReservations);
	    }

	    
	    public void add(final RoomReservation roomReservations) {
	        this.roomReservations.add(roomReservations);
	    }
}
