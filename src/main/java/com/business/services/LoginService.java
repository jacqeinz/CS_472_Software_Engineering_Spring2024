package com.business.services;
import java.util.List;

import com.business.entities.repositories.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.business.entities.Guest;
import com.business.entities.repositories.GuestRepository;

@Service
public class LoginService {

	   @Autowired
	    private GuestRepository guestRepository;
	    
	    
	    public LoginService() {
	        super();
	    }
	    
	    
	    
	    public List<Guest> findAll() {
	        return this.guestRepository.findAll();
	    }

	    public void add(final Guest guest) {
	        this.guestRepository.add(guest);
	    }

}
