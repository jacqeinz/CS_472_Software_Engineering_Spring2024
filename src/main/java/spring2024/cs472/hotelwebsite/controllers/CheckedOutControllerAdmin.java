package spring2024.cs472.hotelwebsite.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import spring2024.cs472.hotelwebsite.repositories.ReservationDetailsRepository;

/**
 * This class handles requests related to checked-out reservations by the admin.
 * It provides a method for displaying information about checked-out reservations.
 *
 * @author Team ABCFG
 */
@Controller
public class CheckedOutControllerAdmin {

    // Attributes
    @Autowired
    private ReservationDetailsRepository reservationDetailsRepository;

    /**
     * Handles requests to display information about checked-out reservations.
     * This method is for admin use.
     * @param model The Model object to add attributes for the view.
     * @param session The HttpSession object to check the admin session.
     * @return The view name for displaying details of checked-out reservations.
     */
    @GetMapping("/checkedOutAdmin")
    public String checkedOut(Model model, HttpSession session) {
        if(session.getAttribute("admin") == null) {
            return "redirect:/login"; // Redirect to login page if not logged in as admin
        }
        model.addAttribute("details", reservationDetailsRepository.findAll());
        return "checkedOutAdmin"; // Display the checked-out reservation details page for admin
    }
}
