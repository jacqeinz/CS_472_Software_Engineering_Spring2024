package com.business.services;
import java.util.List;

import com.business.entities.Guest;
import com.business.entities.ReservationDetails;
import com.business.entities.repositories.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.business.entities.ReservationDetails;
import com.business.entities.repositories.ReservationDetailsRepository;

@Service
public class ReservationDetailsService {

    @Autowired
    private ReservationDetailsRepository reservationDetailsRepository;


    public ReservationDetailsService() {
        super();
    }



    public List<ReservationDetails> findAll() {
        return this.reservationDetailsRepository.findAll();
    }

    public void add(final ReservationDetails reservationDetail) {
        this.reservationDetailsRepository.add(reservationDetail);
    }
}
