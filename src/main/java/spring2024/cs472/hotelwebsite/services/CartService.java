package spring2024.cs472.hotelwebsite.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring2024.cs472.hotelwebsite.entities.*;
import spring2024.cs472.hotelwebsite.repositories.ReservationDetailsRepository;
import spring2024.cs472.hotelwebsite.repositories.RoomReservationRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class CartService {

    @Autowired
    private RoomReservationRepository roomReservationRepository;
    @Autowired
    private ReservationDetailsRepository reservationDetailsRepository;

    public void addRoomReservations(Cart cart, List<Room> selectedRooms, LocalDate start, LocalDate end) {
        long numOfDaysBetween = ChronoUnit.DAYS.between(start.atStartOfDay(), end.atStartOfDay());
        List<LocalDate> dates = IntStream.iterate(0, i -> i + 1)
                .limit(numOfDaysBetween)
                .mapToObj(start::plusDays)
                .toList();
        List<RoomReservation> roomReservations = new ArrayList<>();
        for (Room room : selectedRooms) {
            RoomReservation roomReservation = new RoomReservation(cart.getGuest(), room, dates);
            roomReservationRepository.save(roomReservation);
            roomReservations.add(roomReservation);
        }
        cart.setRoomReservations(roomReservations);
    }

    public ReservationDetails checkoutCart(Cart cart, Guest guest) {
        ReservationDetails reservationDetails = new ReservationDetails(guest, cart.getRoomReservations(), guest.getPaymentInfo(), cart.getTotal());
        reservationDetailsRepository.save(reservationDetails);
        guest.addCurrentReservation(reservationDetails);
        cart.emptyCart();
        return reservationDetails;
    }


}
