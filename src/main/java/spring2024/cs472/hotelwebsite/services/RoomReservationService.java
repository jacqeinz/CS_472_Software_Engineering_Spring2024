package spring2024.cs472.hotelwebsite.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring2024.cs472.hotelwebsite.entities.ReservationDetails;
import spring2024.cs472.hotelwebsite.entities.Room;
import spring2024.cs472.hotelwebsite.entities.RoomReservation;
import spring2024.cs472.hotelwebsite.repositories.ReservationDetailsRepository;
import spring2024.cs472.hotelwebsite.repositories.RoomReservationRepository;

import java.util.List;

@Service
public class RoomReservationService {

    @Autowired
    private ReservationDetailsRepository reservationDetailsRepository;

    @Autowired
    private RoomReservationRepository roomReservationRepository;

    public List<RoomReservation> getAllRoomReservations() {
        return roomReservationRepository.findAll();
    }



    public RoomReservation getRoomReservationById(int id) {
        return roomReservationRepository.findById(id);
    }



    public ReservationDetails saveReservationDetails(ReservationDetails reservationDetails) {
        return reservationDetailsRepository.save(reservationDetails);
    }

    public void deleteReservationDetails(ReservationDetails reservationDetails) {reservationDetailsRepository.delete(reservationDetails);}
}
