/**
 * AvailableReservationsAdminController.java
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
import spring2024.cs472.hotelwebsite.repositories.ReservationDetailsRepository;
import spring2024.cs472.hotelwebsite.services.CartService;
import spring2024.cs472.hotelwebsite.services.RoomService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles requests related to available reservations for administrators.
 * It provides methods for filtering available reservations, adding them to the cart, etc.
 *
 * @author Team ABCFG
 */
@Controller
public class AvailableReservationsAdminController {

    // Attributes
    @Autowired
    private RoomService roomService;
    @Autowired
    private CartService cartService;
    @Autowired
    private ReservationDetailsRepository reservationDetailsRepository;

    /**
     * Handles requests to display available reservations for administrators.
     *
     * @param model The Model object to add attributes for the view.
     * @param session The HttpSession object to check the admin session.
     * @return The view name for displaying available reservations.
     */
    @GetMapping("/availableReservationsAdmin")
    public String availableReservations(Model model, HttpSession session) {
        if(session.getAttribute("admin") == null) {
            return "redirect:/login";
        }
        model.addAttribute("rooms", new ArrayList<Room>());
        return "availableReservationsAdmin";
    }

    /**
     * Handles requests to filter available reservations based on start and end dates.
     *
     * @param model The Model object to add attributes for the view.
     * @param startDate The start date for filtering reservations.
     * @param endDate The end date for filtering reservations.
     * @param session The HttpSession object to check the admin session.
     * @return The view name for displaying filtered available reservations.
     */
    @PostMapping("/availableReservationsAdmin/filter")
    public String filterAvailableReservations(Model model, @RequestParam LocalDate startDate,
                                              @RequestParam LocalDate endDate, HttpSession session) {
        if(session.getAttribute("admin") == null) {
            return "redirect:/login";
        }

        model.addAttribute("reservationDetails", roomService.getRoomsByAvailability(startDate, endDate));
        model.addAttribute("start", startDate);
        model.addAttribute("end", endDate);
        return "availableReservationsAdmin";
    }

    /**
     * Handles requests to add selected rooms to the cart for reservation.
     *
     * @param model The Model object to add attributes for the view.
     * @param session The HttpSession object to check the admin session.
     * @param selectedRooms The list of IDs of selected rooms.
     * @param start The start date for the reservation.
     * @param end The end date for the reservation.
     * @param guest The guest object associated with the reservation.
     * @return The redirect view name for the cart page.
     */
    @PostMapping("/availableReservationsAdmin/addToReservations")
    public String addToCart(Model model, HttpSession session, @RequestParam("selected") List<Integer> selectedRooms,
                            @RequestParam LocalDate start, @RequestParam LocalDate end, @RequestParam Guest guest) {
        if(session.getAttribute("admin") == null) {
            return "redirect:/login";
        }

        List<Room> rooms = new ArrayList<>();
        for (Integer selectedRoom : selectedRooms) {
            rooms.add(roomService.getRoomById(selectedRoom));
        }
        cartService.addRoomReservations(guest.getCart(), rooms, start, end);
        return "redirect:/cart";
    }
}
