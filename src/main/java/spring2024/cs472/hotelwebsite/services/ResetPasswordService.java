/**
 * ResetPasswordService.java
 */
package spring2024.cs472.hotelwebsite.services;

// Imports necessary for the class
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring2024.cs472.hotelwebsite.entities.Account;
import spring2024.cs472.hotelwebsite.entities.PasswordResetToken;
import spring2024.cs472.hotelwebsite.repositories.AccountRepository;
import spring2024.cs472.hotelwebsite.repositories.TokenRepository;

/**
 * Service class responsible for handling password reset functionality.
 *
 * @author Team ABCFG
 */
@Service
public class ResetPasswordService {

    // Attributes
    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TokenRepository tokenRepository;

    /**
     * Handles the reset password request.
     * @param token The token used for password reset.
     * @param model The model to add attributes for the view.
     * @param session The HttpSession object.
     * @return The view name for resetting password or redirect to forgotPassword page with error.
     */
    public String resetPassword(String token, Model model, HttpSession session) {
        PasswordResetToken reset = tokenRepository.findByToken(token);
        if (reset != null && accountService.isNotExpired(reset.getExpiryDateTime())) {
            model.addAttribute("email", reset.getAccount().getEmail());
            return "resetPassword";
        }
        return "redirect:/forgotPassword?error";
    }

    /**
     * Processes the password reset request.
     * @param token The token used for password reset.
     * @param password The new password.
     * @param email The email associated with the account.
     * @return Redirect to the login page.
     */
    public String resetPasswordProcess(String token, String password, String email) {
        Account account = accountRepository.findByEmail(email);
        System.out.println(account);
        if (account != null) {
            account.setUserPassword(password);
            accountRepository.save(account);
        }
        return "redirect:/login";
    }
}
