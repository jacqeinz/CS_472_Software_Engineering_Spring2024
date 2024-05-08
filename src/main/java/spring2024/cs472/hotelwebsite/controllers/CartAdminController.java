/**
 * CartAdminController.java
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
import spring2024.cs472.hotelwebsite.services.CartService;
import spring2024.cs472.hotelwebsite.services.RoomService;

/**
 * This class handles requests related to the admin's cart and checkout functionality.
 * It provides methods for displaying the admin's cart and checking out.
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
     *
     * @param model The Model object to add attributes for the view.
     * @param session The HttpSession object to check the admin session.
     * @return The view name for displaying the admin's cart.
     */
    @GetMapping("/cartAdmin")
    public String availableReservations(Model model, HttpSession session) {
        if(session.getAttribute("admin") == null) {
            return "redirect:/login";
        }
        Guest guest = (Guest) session.getAttribute("guest");
        model.addAttribute("cart", guest.getCart());
        return "cartAdmin";
    }

    /**
     * Handles requests to checkout the admin's cart.
     *
     * @param model The Model object to add attributes for the view.
     * @param session The HttpSession object to check the admin session.
     * @param guest The Guest object associated with the cart.
     * @return The redirect view name for displaying the confirmation of checkout.
     */
    @PostMapping("/cartAdmin/checkout")
    public String checkoutCart(Model model, HttpSession session, Guest guest) {
        if(session.getAttribute("admin") == null) {
            return "redirect:/login";
        }
        Long id = cartService.checkoutCart(guest.getCart(), guest).getId();
        return "redirect:/checkedOutAdmin";
    }
}
