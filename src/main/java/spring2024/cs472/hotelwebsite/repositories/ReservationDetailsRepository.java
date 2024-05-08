/**
 * ReservationDetailsRepository.java
 */
package spring2024.cs472.hotelwebsite.repositories;

// Imports necessary for the class
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring2024.cs472.hotelwebsite.entities.*;

/**
 * Repository interface for managing reservation details.
 * This interface defines methods for managing reservation details using JpaRepository.
 * It provides a method for finding reservation details by ID.
 *
 * @author Team ABCFG
 */
@Repository
public interface ReservationDetailsRepository extends JpaRepository<ReservationDetails, Long> {

    /**
     * Finds reservation details by their ID.
     *
     * @param id The ID of the reservation details.
     * @return The reservation details with the specified ID, or null if not found.
     */
    ReservationDetails findById(long id);
}
