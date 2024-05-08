/**
 * SavedReservationsController.java
 */
package spring2024.cs472.hotelwebsite.controllers;

// Imports necessary for the class
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import spring2024.cs472.hotelwebsite.entities.ReservationDetails;
import spring2024.cs472.hotelwebsite.entities.Room;
import spring2024.cs472.hotelwebsite.repositories.ReservationDetailsRepository;
import spring2024.cs472.hotelwebsite.repositories.RoomRepository;
import spring2024.cs472.hotelwebsite.repositories.RoomReservationRepository;
import spring2024.cs472.hotelwebsite.services.RoomService;

/**
 * This class is responsible for managing saved reservations.
 *
 * @author Team ABCFG
 */
@Controller
public class SavedReservationsController {

    // Attributes
    private final ReservationDetailsRepository reservationDetailsRepository;

    /**
     * Constructs a SavedReservationsController with the provided ReservationDetailsRepository.
     *
     * @param reservationDetailsRepository The repository for reservation details.
     */
    public SavedReservationsController(ReservationDetailsRepository reservationDetailsRepository) {
        this.reservationDetailsRepository = reservationDetailsRepository;
    }

    /**
     * Displays a list of saved reservations.
     *
     * @param model   The model to which attributes are added.
     * @param session The HTTP session.
     * @return The view for displaying saved reservations.
     */
    @GetMapping("/SavedReservationReport")
    public String showReservationList(Model model, HttpSession session){
        if(session.getAttribute("admin") == null) {
            return "redirect:/login";
        }
        model.addAttribute("SavedReservations", reservationDetailsRepository.findAll());
        return "SavedReservationReport";
    }
}
