package com.business.entities.repositories;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import com.business.entities.Guest;


@Repository
public class GuestRepository {

	 private final List<Guest> guests = new ArrayList<Guest>();
	    
	    
	    
	    public GuestRepository() {
	        super();
	    }
	    
	    
	    
	    public List<Guest> findAll() {
	        return new ArrayList<Guest>(this.guests);
	    }

	    
	    public void add(final Guest guest) {
	        this.guests.add(guest);
	    }
}
