/**
 * RoomReservationService.java
 */
package spring2024.cs472.hotelwebsite.services;

// Imports necessary for the class
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring2024.cs472.hotelwebsite.entities.ReservationDetails;
import spring2024.cs472.hotelwebsite.entities.Room;
import spring2024.cs472.hotelwebsite.entities.RoomReservation;
import spring2024.cs472.hotelwebsite.repositories.ReservationDetailsRepository;
import spring2024.cs472.hotelwebsite.repositories.RoomReservationRepository;
import java.util.List;

/**
 * Service class for managing room reservations.
 * This class is responsible for managing room reservations, including retrieving, saving, and deleting reservations.
 * It also provides methods to retrieve reservation details associated with a room reservation.
 *
 * @author Team ABCFG
 */
@Service
public class RoomReservationService {

    // Attributes
    @Autowired
    private ReservationDetailsRepository reservationDetailsRepository;
    @Autowired
    private RoomReservationRepository roomReservationRepository;

    /**
     * Retrieves all room reservations.
     *
     * @return A list of all room reservations.
     */
    public List<RoomReservation> getAllRoomReservations() {
        return roomReservationRepository.findAll();
    }

    /**
     * Retrieves a room reservation by its ID.
     *
     * @param id The ID of the room reservation to retrieve.
     * @return The room reservation with the specified ID, or null if not found.
     */
    public RoomReservation getRoomReservationById(int id) {
        return roomReservationRepository.findById(id);
    }

    /**
     * Retrieves reservation details associated with a room reservation by its ID.
     *
     * @param id The ID of the room reservation.
     * @return The reservation details associated with the room reservation, or null if not found.
     */
    public ReservationDetails getDetailsByRoomReservationId(int id) {
        List<ReservationDetails> details = reservationDetailsRepository.findAll();
        for (ReservationDetails detail : details) {
            for (RoomReservation roomReservation : detail.getRoomReservations()) {
                if (roomReservation.getId() == id) {
                    return detail;
                }
            }
        }
        return null;
    }

    /**
     * Saves reservation details.
     *
     * @param reservationDetails The reservation details to save.
     * @return The saved reservation details.
     */
    public ReservationDetails saveReservationDetails(ReservationDetails reservationDetails) {
        return reservationDetailsRepository.save(reservationDetails);
    }

    /**
     * Deletes reservation details.
     *
     * @param reservationDetails The reservation details to delete.
     */
    public void deleteReservationDetails(ReservationDetails reservationDetails) {
        reservationDetailsRepository.delete(reservationDetails);
    }
}
