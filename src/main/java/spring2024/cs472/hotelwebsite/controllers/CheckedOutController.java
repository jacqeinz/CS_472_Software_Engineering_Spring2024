/**
 * CheckedOutController.java
 */
package spring2024.cs472.hotelwebsite.controllers;

// Imports necessary for the class
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring2024.cs472.hotelwebsite.entities.Guest;
import spring2024.cs472.hotelwebsite.entities.ReservationDetails;
import spring2024.cs472.hotelwebsite.entities.Room;
import spring2024.cs472.hotelwebsite.entities.RoomReservation;
import spring2024.cs472.hotelwebsite.repositories.ReservationDetailsRepository;

/**
 * This class handles requests related to checked-out reservations.
 * It provides a method for displaying information about checked-out reservations.
 *
 * @author Team ABCFG
 */
@Controller
public class CheckedOutController {

    // Attributes
    @Autowired
    private ReservationDetailsRepository reservationDetailsRepository;

    /**
     * Handles requests to display information about a checked-out reservation.
     *
     * @param model The Model object to add attributes for the view.
     * @param session The HttpSession object to check the guest session.
     * @param id The ID of the reservation to display details for.
     * @return The view name for displaying details of the checked-out reservation.
     */
    @GetMapping("/checkedOut")
    public String checkedOut(Model model, HttpSession session, @RequestParam(value = "id") int id) {
        if(session.getAttribute("guest") == null) {
            return "redirect:/login"; // Redirect to login page if not logged in as guest
        }
        // Retrieve reservation details by ID and add them to the model
        model.addAttribute("details", reservationDetailsRepository.findById(id));
        return "checkedOut"; // Display the checked-out reservation details page
    }

    // These methods are commented out. Uncomment and document them if needed.

//    /**
//     * Handles requests to initiate checkout of the guest's cart.
//     *
//     * @param model The Model object to add attributes for the view.
//     * @param session The HttpSession object to check the guest session.
//     * @return The view name for initiating the checkout process.
//     */
//    @PostMapping("/cart/checkout")
//    public String checkoutCart(Model model, HttpSession session) {
//        Guest guest = (Guest) session.getAttribute("guest");
//        return "availableReservations";
//    }
//
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
//        Guest guest = (Guest) session.getAttribute("guest");
//        cartService.addRoomReservations(guest.getCart(), selectedRooms, (LocalDate) model.getAttribute("start"),
//                (LocalDate)  model.getAttribute("end"));
//        return "redirect:/cart";
//    }
}
