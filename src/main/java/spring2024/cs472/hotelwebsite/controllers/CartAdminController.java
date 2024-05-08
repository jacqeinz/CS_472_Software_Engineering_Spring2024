package spring2024.cs472.hotelwebsite.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import spring2024.cs472.hotelwebsite.entities.Admin;
import spring2024.cs472.hotelwebsite.services.CartService;
import spring2024.cs472.hotelwebsite.services.RoomService;

/**
 * This class handles requests related to the admin's cart and checkout functionality.
 * It provides methods for displaying the admin's cart and initiating checkout.
 *
 * @author Team ABCFG
 */
@Controller
public class CartAdminController {

    // Attributes
    @Autowired
    private RoomService roomService;
    @Autowired
    private CartService cartService;

    /**
     * Handles requests to display the admin's cart.
     * @param model The Model object to add attributes for the view.
     * @param session The HttpSession object to check the admin session.
     * @return The view name for displaying the admin's cart.
     */
    @GetMapping("/cartAdmin")
    public String showAdminCart(Model model, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/login"; // Redirect to login page if not logged in as admin
        }
        Admin admin = (Admin) session.getAttribute("admin");
        model.addAttribute("cart", admin.getCart());
        return "cartAdmin";
    }

    /**
     * Handles requests to initiate checkout of the admin's cart.
     * @param model The Model object to add attributes for the view.
     * @param session The HttpSession object to check the admin session.
     * @return The view name for initiating the checkout process.
     */
    @PostMapping("/cartAdmin/checkoutAdmin")
    public String initiateAdminCheckout(Model model, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/login"; // Redirect to login page if not logged in as admin
        }
        Admin admin = (Admin) session.getAttribute("admin");
        model.addAttribute("admin", admin);
        return "checkoutAdmin";
    }
}
