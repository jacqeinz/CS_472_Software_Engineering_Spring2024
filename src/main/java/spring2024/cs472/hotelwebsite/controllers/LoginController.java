/**
 * LoginController.java
 */
package spring2024.cs472.hotelwebsite.controllers;

// Imports necessary for the class
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring2024.cs472.hotelwebsite.entities.*;
import spring2024.cs472.hotelwebsite.repositories.AccountRepository;
import spring2024.cs472.hotelwebsite.repositories.TokenRepository;
import spring2024.cs472.hotelwebsite.services.*;

/**
 * This class handles user authentication, login, logout, and password reset functionality.
 * It provides methods for displaying login page, processing login/logout, and handling forgot/reset password.
 *
 * @author Team ABCFG
 */
@Controller
public class LoginController {

    // Attributes
    @Autowired
    private InitService initService; // Service for initializing data
    @Autowired
    private AccountService accountService; // Service for account-related operations
    @Autowired
    private AccountRepository accountRepository; // Repository for managing accounts
    @Autowired
    private TokenRepository tokenRepository; // Repository for managing password reset tokens

    /**
     * Displays the login page.
     *
     * @param model The Model object to add attributes for the view.
     * @param session The HttpSession object to check for existing guest/admin sessions.
     * @return The view name for displaying the login page.
     */
    @GetMapping("/login")
    public String loginPage(Model model, HttpSession session) {
        if (session.getAttribute("guest") != null)
            return "redirect:/dashboardGuest"; // Redirect to guest dashboard if guest is logged in
        else if (session.getAttribute("admin") != null)
            return "redirect:/dashboardAdmin"; // Redirect to admin dashboard if admin is logged in

        initService.init(); // Initialize data
        model.addAttribute("error", false); // Add error attribute to model
        model.addAttribute("logout", false); // Add logout attribute to model
        return "login"; // Display the login page
    }

    /**
     * Logs out the user.
     *
     * @param session The HttpSession object to remove guest/admin attributes.
     * @return The view name for redirecting to the home page.
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("guest"); // Remove guest attribute from session
        session.removeAttribute("admin"); // Remove admin attribute from session
        return "redirect:/"; // Redirect to the home page
    }

    /**
     * Processes the login request.
     *
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     * @param model The Model object to add attributes for the view.
     * @param session The HttpSession object to set guest/admin attributes.
     * @return The view name for redirecting to the respective dashboard.
     */
    @PostMapping("/login")
    public String loginPage(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
        Account account = accountService.validateLogin(username, password); // Validate login credentials
        if (account == null) {
            model.addAttribute("error", true); // Add error attribute to model
            return "login"; // Display the login page with error message
        } else if (account instanceof Guest) {
            session.setAttribute("guest", (Guest) account); // Set guest attribute in session
            return "redirect:/dashboardGuest"; // Redirect to guest dashboard
        } else if (account instanceof Admin) {
            session.setAttribute("admin", (Admin) account); // Set admin attribute in session
            return "redirect:/dashboardAdmin"; // Redirect to admin dashboard
        }
        model.addAttribute("error", true); // Add error attribute to model
        return "login"; // Display the login page with error message
    }

    /**
     * Displays the forgot password page.
     *
     * @return The view name for displaying the forgot password page.
     */
    @GetMapping("/forgotPassword")
    public String forgotPassword() {
        return "forgotPassword"; // Display the forgot password page
    }

    /**
     * Processes the forgot password request.
     *
     * @param email The email entered by the user.
     * @param model The Model object to add attributes for the view.
     * @param session The HttpSession object to retrieve account information.
     * @return The view name for redirecting based on the result of the operation.
     */
    @PostMapping("/forgotPassword")
    public String forgotPassword(@RequestParam String email, Model model, HttpSession session) {
        Account account = accountRepository.findByEmail(email); // Find account by email
        String result = "";
        if (account != null) {
            result = accountService.sendEmail(account); // Send reset password email
        }
        if (result.equals("Success")) {
            return "redirect:/forgotPassword?success"; // Redirect to success page
        }
        return "redirect:/login"; // Redirect to login page
    }

    /**
     * Displays the reset password page.
     *
     * @param token The reset token.
     * @param model The Model object to add attributes for the view.
     * @param session The HttpSession object to retrieve token information.
     * @return The view name for displaying the reset password page or redirecting to error page.
     */
    @GetMapping("/resetPassword/{token}")
    public String resetPassword(@PathVariable String token, Model model, HttpSession session) {

        PasswordResetToken reset = tokenRepository.findByToken(token); // Find token by token value
        if (reset != null && accountService.isNotExpired(reset.getExpiryDateTime())) {
            model.addAttribute("email", reset.getAccount().getEmail()); // Add email attribute to model
            return "resetPassword"; // Display the reset password page
        }
        return "redirect:/forgotPassword?error"; // Redirect to forgot password page with error message
    }

    /**
     * Processes the reset password request.
     *
     * @param token The reset token.
     * @param password The new password entered by the user.
     * @param email The email associated with the account.
     * @return The view name for redirecting to the login page.
     */
    @PostMapping("/resetPassword/{token}")
    public String resetPassword(@PathVariable String token, @RequestParam String password, @RequestParam String email) {
        Account account = accountRepository.findByEmail(email); // Find account by email
        if (account != null) {
            account.setUserPassword(password); // Set new password
            accountRepository.save(account); // Save account
        }
        return "redirect:/login"; // Redirect to login page
    }
}

