package com.business.services;
import java.util.List;

import com.business.entities.ReservationDetails;
import com.business.entities.repositories.ReservationDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.business.entities.Room;
import com.business.entities.repositories.RoomRepository;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;


    public RoomService() {
        super();
    }



    public List<Room> findAll() {
        return this.roomRepository.findAll();
    }

    public void add(final Room room) {
        this.roomRepository.add(room);
    }

}
