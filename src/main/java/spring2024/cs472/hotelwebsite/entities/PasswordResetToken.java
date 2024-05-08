/**
 * PasswordResetToken.java
 */
package spring2024.cs472.hotelwebsite.entities;

// Imports necessary for the class
import java.time.LocalDateTime;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * Represents a password reset token.
 * The token is associated with an account and has an expiry date and time.
 *
 * @author Team ABCFG
 */
@Entity
@Table(name = "passwordresettoken")
public class PasswordResetToken {

    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String token;
    private LocalDateTime expiryDateTime;

    @OneToOne(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    @JoinColumn(name = "accountId", referencedColumnName = "id", nullable = false)
    private Account account;

    /**
     * Retrieves the ID of the password reset token.
     *
     * @return The ID of the password reset token.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the password reset token.
     *
     * @param id The ID of the password reset token.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the token string of the password reset token.
     *
     * @return The token string.
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the token string of the password reset token.
     *
     * @param token The token string to set.
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Retrieves the expiry date and time of the password reset token.
     *
     * @return The expiry date and time.
     */
    public LocalDateTime getExpiryDateTime() {
        return expiryDateTime;
    }

    /**
     * Sets the expiry date and time of the password reset token.
     *
     * @param expiryDateTime The expiry date and time to set.
     */
    public void setExpiryDateTime(LocalDateTime expiryDateTime) {
        this.expiryDateTime = expiryDateTime;
    }

    /**
     * Retrieves the account associated with the password reset token.
     *
     * @return The account associated with the token.
     */
    public Account getAccount() {
        return account;
    }

    /**
     * Sets the account associated with the password reset token.
     *
     * @param account The account to set.
     */
    public void setAccount(Account account) {
        this.account = account;
    }
}
