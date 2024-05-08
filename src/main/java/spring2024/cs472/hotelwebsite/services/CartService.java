/**
 * CartService.java
 */
package spring2024.cs472.hotelwebsite.services;

// Imports necessary for the class
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring2024.cs472.hotelwebsite.entities.*;
import spring2024.cs472.hotelwebsite.repositories.ReservationDetailsRepository;
import spring2024.cs472.hotelwebsite.repositories.RoomReservationRepository;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Service class for managing the shopping cart.
 * This class provides methods for adding room reservations to the cart, checking out the cart, and setting up date lists.
 *
 * @author Team ABCFG
 */
@Service
public class CartService {

    // Attributes
    @Autowired
    private RoomReservationRepository roomReservationRepository;
    @Autowired
    private ReservationDetailsRepository reservationDetailsRepository;
    @Autowired
    private AccountService accountService;

    /**
     * Adds room reservations to the cart.
     *
     * @param cart The cart to which reservations are added.
     * @param selectedRooms The rooms selected for reservation.
     * @param start The start date of the reservation.
     * @param end The end date of the reservation.
     */
    public void addRoomReservations(Cart cart, List<Room> selectedRooms, LocalDate start, LocalDate end) {
        List<LocalDate> dates = setupDateList(start, end);
        List<RoomReservation> roomReservations = new ArrayList<>();
        for (Room room : selectedRooms) {
            RoomReservation roomReservation = new RoomReservation(cart.getGuest(), room, dates);
            roomReservationRepository.save(roomReservation);
            roomReservations.add(roomReservation);
        }
        cart.addRoomReservations(roomReservations);
    }

    /**
     * Checks out the cart and generates a reservation.
     *
     * @param cart The cart to check out.
     * @param guest The guest who owns the cart.
     * @return The reservation details.
     */
    public ReservationDetails checkoutCart(Cart cart, Guest guest) {
        ReservationDetails reservationDetails = new ReservationDetails(guest, cart.getRoomReservations(), guest.getPaymentInfo(), cart.getTotal());
        reservationDetailsRepository.save(reservationDetails);
        guest.addCurrentReservation(reservationDetails);
        cart.emptyCart();
        accountService.sendConfirmationEmail(guest, reservationDetails);
        return reservationDetails;
    }

    /**
     * Sets up a list of dates between start and end dates.
     *
     * @param start The start date.
     * @param end The end date.
     * @return A list of dates between start and end dates.
     */
    public List<LocalDate> setupDateList(LocalDate start, LocalDate end) {
        long numOfDaysBetween = ChronoUnit.DAYS.between(start.atStartOfDay(), end.atStartOfDay());
        return IntStream.iterate(0, i -> i + 1)
                .limit(numOfDaysBetween)
                .mapToObj(start::plusDays)
                .collect(Collectors.toList());
    }
}
