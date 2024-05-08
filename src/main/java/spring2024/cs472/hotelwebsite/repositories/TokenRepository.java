/**
 * TokenRepository.java
 */
package spring2024.cs472.hotelwebsite.repositories;

// Imports necessary for the class
import org.springframework.data.jpa.repository.JpaRepository;
import spring2024.cs472.hotelwebsite.entities.PasswordResetToken;
import org.springframework.stereotype.Repository;
import spring2024.cs472.hotelwebsite.entities.*;

/**
 * Repository interface for managing password reset tokens.
 * This interface defines methods for managing password reset tokens using JpaRepository.
 * It provides a method for finding a password reset token by its token string.
 *
 * @author Team ABCFG
 */
@Repository
public interface TokenRepository extends JpaRepository<PasswordResetToken, Integer>{

    /**
     * Finds a password reset token by its token string.
     *
     * @param token The token string.
     * @return The password reset token with the specified token string, or null if not found.
     */
    PasswordResetToken findByToken(String token);
}
