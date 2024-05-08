/**
 * GuestAccountCrudController.java
 */
package spring2024.cs472.hotelwebsite.controllers;

// Imports necessary for the class
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import spring2024.cs472.hotelwebsite.entities.Guest;
import spring2024.cs472.hotelwebsite.repositories.AccountRepository;
import spring2024.cs472.hotelwebsite.services.AccountService;

/**
 * This class handles CRUD operations related to guest accounts.
 * It provides methods for updating guest information.
 *
 * @author Team ABCFG
 */
@Controller
@SessionAttributes("guest") // Specifies session attributes for this controller
public class GuestAccountCrudController {

    // Attributes
    @Autowired
    private AccountRepository accountRepository; // Repository for managing accounts
    private AccountService accountService; // Service for account-related operations

    /**
     * Constructor for the GuestAccountCrudController class.
     *
     * @param accountService The AccountService instance for managing accounts.
     */
    public GuestAccountCrudController(AccountService accountService) {this.accountService = accountService;}

    /**
     * Displays the form for updating guest information.
     *
     * @param model The Model object to add attributes for the view.
     * @param session The HttpSession object to retrieve guest information from the session.
     * @return The view name for updating guest information.
     */
    @GetMapping("/guest/edit")
    public String showUpdateFormGuest(Model model, HttpSession session) {
        if(session.getAttribute("guest") == null){
            return "redirect:/login";
        }
        model.addAttribute("guest", (Guest) session.getAttribute("guest")); // Add guest information to the model
        return "updateGuest"; // Display the form for updating guest information
    }

    /**
     * Handles the submission of updated guest information.
     *
     * @param guest The Guest object containing the updated guest information.
     * @param result The BindingResult object for validation.
     * @param model The Model object to add attributes for the view.
     * @param session The HttpSession object to update guest information in the session.
     * @param status The SessionStatus object to complete the session status.
     * @return The view name for redirecting to the guest dashboard.
     */
    @PostMapping("/guest/update")
    public String updateGuest(@ModelAttribute("guest") Guest guest,
                              BindingResult result, Model model, HttpSession session, SessionStatus status) {
//        if(session.getAttribute("guest") == null){
//            return "redirect:/login";
//        }
//        if (result.hasErrors()) {
//            return "redirect:/updateGuest"; // Redirect to the update guest form if there are validation errors
//        }

        accountRepository.save(guest); // Save the updated guest information
        session.setAttribute("guest", guest); // Update guest information in the session
//        status.setComplete(); // Complete the session status
        return "redirect:/dashboardGuest"; // Redirect to the guest dashboard
    }
}