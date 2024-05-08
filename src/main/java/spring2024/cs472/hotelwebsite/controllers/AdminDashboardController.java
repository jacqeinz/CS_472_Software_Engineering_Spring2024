/**
 * AdminDashboardController.java
 */
package spring2024.cs472.hotelwebsite.controllers;

// Imports necessary for the class
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * The AdminDashboardController class manages requests related to the admin dashboard and logout functionality.
 *
 * @author Team ABCFG
 */
@Controller
public class AdminDashboardController {

    /**
     * Handles requests to display the admin dashboard.
     *
     * @param model The Model object to add attributes for the view.
     * @param session The HttpSession object to check the admin session.
     * @return The view name for the admin dashboard or a redirection to the login page if not logged in.
     */
    @GetMapping("/dashboardAdmin")
    public String adminDashboard(Model model, HttpSession session) {
        if(session.getAttribute("admin") == null) {
            return "redirect:/login";
        }
        System.out.println(session.getAttribute("admin"));
        return "dashboardAdmin";
    }

    /**
     * Handles requests to log out the admin.
     *
     * @param model The Model object to add attributes for the view.
     * @param session The HttpSession object to invalidate the admin session.
     * @return The view name for logging out the admin.
     */
    @GetMapping("/logoutAdmin")
    public String logoutAdmin(Model model, HttpSession session) {
        if(session.getAttribute("admin") == null){
            return "redirect:/login";
        }
        session.invalidate();
        return "logoutAdmin";
    }
}
