package com.business.entities.repositories;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import com.business.entities.Room;

public class RoomRepository {

	 private final List<Room> rooms = new ArrayList<Room>();
	    
	    
	    
	    public RoomRepository() {
	        super();
	    }
	    
	    
	    
	    public List<Room> findAll() {
	        return new ArrayList<Room>(this.rooms);
	    }

	    
	    public void add(final Room room) {
	        this.rooms.add(room);
	    }
}
