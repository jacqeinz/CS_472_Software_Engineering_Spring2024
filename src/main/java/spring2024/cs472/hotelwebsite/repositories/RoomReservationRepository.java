/**
 * RoomReservationRepository.java
 */
package spring2024.cs472.hotelwebsite.repositories;

// Imports necessary for the class
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring2024.cs472.hotelwebsite.entities.*;

/**
 * Repository interface for managing room reservations.
 * This interface defines methods for managing room reservations using JpaRepository.
 * It provides methods for finding room reservations by ID and by room.
 *
 * @author Team ABCFG
 */
@Repository
public interface RoomReservationRepository extends JpaRepository<RoomReservation, Long> {

    /**
     * Finds a room reservation by its ID.
     *
     * @param id The ID of the room reservation.
     * @return The room reservation with the specified ID, or null if not found.
     */
    RoomReservation findById(long id);

    /**
     * Finds all room reservations associated with a specific room.
     *
     * @param room The room for which to find reservations.
     * @return A list of room reservations associated with the specified room.
     */
    List<RoomReservation> findByRoom(Room room);
}
