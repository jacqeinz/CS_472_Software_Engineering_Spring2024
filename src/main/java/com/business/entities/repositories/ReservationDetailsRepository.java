package com.business.entities.repositories;
import java.util.ArrayList;
import java.util.List;

import com.business.entities.Guest;
import org.springframework.stereotype.Repository;
import com.business.entities.ReservationDetails;


@Repository
public class ReservationDetailsRepository {

    private final List<ReservationDetails> reservationDetail = new ArrayList<ReservationDetails>();



    public ReservationDetailsRepository() {
        super();
    }



    public List<ReservationDetails> findAll() {
        return new ArrayList<ReservationDetails>(this.reservationDetail);
    }


    public void add(final ReservationDetails reservationDetail) {
        this.reservationDetail.add(reservationDetail);
    }


}
