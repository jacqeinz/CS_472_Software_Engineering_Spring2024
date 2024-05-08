/**
 * GuestDashboardController.java
 */
package spring2024.cs472.hotelwebsite.controllers;

// Imports necessary for the class
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import spring2024.cs472.hotelwebsite.entities.Guest;
import spring2024.cs472.hotelwebsite.repositories.AccountRepository;
import spring2024.cs472.hotelwebsite.services.AccountService;

/**
 * This class handles requests related to the guest dashboard.
 * It provides a method for displaying the guest dashboard.
 *
 * @author Team ABCFG
 */
@Controller
public class GuestDashboardController {

    // Attributes
    @Autowired
    AccountRepository accountRepo; // Repository for managing accounts

    /**
     * Displays the guest dashboard.
     *
     * @param model The Model object to add attributes for the view.
     * @param session The HttpSession object to retrieve guest information from the session.
     * @return The view name for displaying the guest dashboard.
     */
    @GetMapping("/dashboardGuest")
    public String guestDashboard(Model model, HttpSession session) {
        if(session.getAttribute("guest") == null) {
            return "redirect:/login"; // Redirect to login page if guest is not logged in
        }
        Guest oldGuest = (Guest) session.getAttribute("guest"); // Get guest information from the session
        Guest freshGuest = (Guest) accountRepo.findById(oldGuest.getId()).orElse(oldGuest); // Refresh guest information from the database
        session.setAttribute("guest", freshGuest); // Update guest information in the session
        System.out.println(session.getAttribute("guest")); // Log guest information
        return "dashboardGuest"; // Display the guest dashboard
    }
}
