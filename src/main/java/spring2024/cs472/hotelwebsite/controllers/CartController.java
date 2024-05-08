/**
 * CartController.java
 */
package spring2024.cs472.hotelwebsite.controllers;

// Imports necessary for the class
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring2024.cs472.hotelwebsite.entities.Guest;
import spring2024.cs472.hotelwebsite.entities.Room;
import spring2024.cs472.hotelwebsite.services.CartService;
import spring2024.cs472.hotelwebsite.services.RoomService;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * This class handles requests related to the guest's cart and checkout functionality.
 * It provides methods for displaying the guest's cart and initiating checkout.
 *
 * @author Team ABCFG
 */
@Controller
public class CartController {

    // Attributes
    @Autowired
    private RoomService roomService;
    @Autowired
    private CartService cartService;

    /**
     * Handles requests to display the guest's cart.
     *
     * @param model The Model object to add attributes for the view.
     * @param session The HttpSession object to check the guest session.
     * @return The view name for displaying the guest's cart.
     */
    @GetMapping("/cart")
    public String availableReservations(Model model, HttpSession session) {
        if(session.getAttribute("guest") == null) {
            return "redirect:/login"; // Redirect to login page if not logged in as guest
        }
        Guest guest = (Guest) session.getAttribute("guest");
        model.addAttribute("cart", guest.getCart());
        return "cart";
    }

    /**
     * Handles requests to initiate checkout of the guest's cart.
     *
     * @param model The Model object to add attributes for the view.
     * @param session The HttpSession object to check the guest session.
     * @return The view name for initiating the checkout process.
     */
    @PostMapping("/cart/checkout")
    public String checkoutCart(Model model, HttpSession session) {
        if(session.getAttribute("guest") == null) {
            return "redirect:/login"; // Redirect to login page if not logged in as guest
        }
        return "checkout"; // Display the checkout page
    }

//    /**
//     * Handles requests to add selected rooms to the guest's cart for reservation.
//     *
//     * @param model The Model object to add attributes for the view.
//     * @param session The HttpSession object to check the guest session.
//     * @param selectedRooms The list of selected rooms to add to the cart.
//     * @return The redirect view name for the cart page.
//     */
//    @PostMapping("/availableReservations/addToCart")
//    public String addToCart(Model model, HttpSession session, @RequestParam List<Room> selectedRooms) {
//        Guest guest = (Guest) session.getAttribute("guest"); // Get guest from session
          // Add selected rooms to the guest's cart with specified start and end dates
//        cartService.addRoomReservations(guest.getCart(), selectedRooms, (LocalDate) model.getAttribute("start"),
//                (LocalDate)  model.getAttribute("end"));
//        return "redirect:/cart"; // Redirect to cart page after adding rooms to cart
//    }
}
