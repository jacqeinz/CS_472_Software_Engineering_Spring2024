/**
 * RoomRepository.java
 */
package spring2024.cs472.hotelwebsite.repositories;

// Imports necessary for the class
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring2024.cs472.hotelwebsite.entities.*;

/**
 * Repository interface for managing rooms.
 * This interface defines methods for managing rooms using JpaRepository.
 * It provides methods for finding rooms by ID and room number.
 *
 * @author Team ABCFG
 */
@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    /**
     * Finds a room by its ID.
     *
     * @param id The ID of the room.
     * @return The room with the specified ID, or null if not found.
     */
    Room findById(long id);

    /**
     * Finds a room by its room number.
     *
     * @param roomNumber The room number of the room.
     * @return The room with the specified room number, or null if not found.
     */
    Room findByRoomNumber(String roomNumber);
}
