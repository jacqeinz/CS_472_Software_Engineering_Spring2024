/**
 * AccountRepository.java
 */
package spring2024.cs472.hotelwebsite.repositories;

// Imports necessary for the class
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring2024.cs472.hotelwebsite.entities.*;

/**
 * Repository interface for managing accounts.
 * This interface defines methods for managing accounts using JpaRepository.
 * It provides methods for finding an account by ID and by email.
 *
 * @author Team ABCFG
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * Finds an account by its ID.
     *
     * @param id The ID of the account.
     * @return The account with the specified ID, or null if not found.
     */
    Account findById(long id);

    /**
     * Finds an account by its email address.
     *
     * @param email The email address of the account.
     * @return The account with the specified email address, or null if not found.
     */
    Account findByEmail(String email);
}
