/**
 * AccountService.java
 */
package spring2024.cs472.hotelwebsite.services;

// Imports necessary for the class
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import spring2024.cs472.hotelwebsite.entities.*;
import spring2024.cs472.hotelwebsite.repositories.AccountRepository;
import spring2024.cs472.hotelwebsite.repositories.TokenRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service class for managing accounts.
 * This class provides methods for managing user accounts, such as retrieving all accounts,
 * validating login credentials, saving and deleting accounts, sending password reset emails,
 * generating password reset tokens, checking if a token has expired, and sending confirmation emails.
 * It also provides a method for getting an account by its ID.
 *
 * @author Team ABCFG
 */
@Service
public class AccountService {

    // Attributes
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    private TokenRepository tokenRepository;

    /**
     * Retrieves all accounts.
     *
     * @return A list of all accounts.
     */
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    /**
     * Validates login credentials.
     *
     * @param username The username.
     * @param password The password.
     * @return The account if credentials are valid, null otherwise.
     */
    public Account validateLogin(String username, String password) {
        for (Account account : getAllAccounts()) {
            if (account.getUserName().equals(username) && account.getUserPassword().equals(password)) {
                return account;
            }
        }
        return null;
    }

    /**
     * Retrieves all administrators.
     *
     * @return A list of all administrators.
     */
    public List<Admin> getAllAdmins() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().filter(a -> a instanceof Admin).map(a -> (Admin)a).collect(Collectors.toList());
    }

    /**
     * Saves an account.
     *
     * @param account The account to save.
     */
    public void save(Account account) {
        accountRepository.save(account);
    }

    /**
     * Deletes an account.
     *
     * @param account The account to delete.
     */
    public void delete(Account account) {
        accountRepository.delete(account);
    }

    /**
     * Sends a password reset email.
     *
     * @param account The account for which to send the reset email.
     * @return "Success" if email sent successfully, "error" otherwise.
     */
    public String sendEmail(Account account) {
        try {
            String resetLink = generateRestToken(account);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(account.getEmail());
            message.setSubject("Password Reset");
            message.setText("Click here to reset your password: " + resetLink);

            javaMailSender.send(message);
            return "Success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    /**
     * Generates a password reset token.
     *
     * @param account The account for which to generate the token.
     * @return The reset token.
     */
    public String generateRestToken(Account account) {
        UUID uuid = UUID.randomUUID();
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime expiryDateTime = currentDateTime.plusMinutes(20);
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setAccount(account);
        resetToken.setToken(uuid.toString());
        resetToken.setExpiryDateTime(expiryDateTime);
        resetToken.setAccount(account);
        PasswordResetToken token = tokenRepository.save(resetToken);
        if (token != null) {
            String endpointUrl = "http://localhost:8080/resetPassword";
            return endpointUrl + "/" + resetToken.getToken();
        }
        return "";
    }

    /**
     * Checks if a token has expired.
     *
     * @param expiryDateTime The expiry date and time of the token.
     * @return true if the token has not expired, false otherwise.
     */
    public boolean isNotExpired(LocalDateTime expiryDateTime) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return expiryDateTime.isAfter(currentDateTime);
    }

    /**
     * Sends a confirmation email for a reservation.
     *
     * @param account The account to which to send the email.
     * @param details The reservation details.
     * @return true if email sent successfully, false otherwise.
     */
    public boolean sendConfirmationEmail(Account account, ReservationDetails details) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(account.getEmail());
            message.setSubject("ABCFG Hotel Reservation Confirmation");
            StringBuilder builder = new StringBuilder();
            builder.append("Thank you for reserving rooms at our hotel.\n")
                    .append("Your purchase has been successfully confirmed.\n")
                    .append("Total Price: ").append(details.getTotal()).append( "\n");

            message.setText(builder.toString());

            javaMailSender.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Gets an account by its ID.
     *
     * @param id The ID of the account.
     * @return The account with the specified ID.
     */
    public int getById(int id) {
        return id;
    }
}
