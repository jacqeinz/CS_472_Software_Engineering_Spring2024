/**
 * ReservationsAdminController.java
 */
package spring2024.cs472.hotelwebsite.controllers;

// Imports necessary for the class
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import spring2024.cs472.hotelwebsite.entities.*;
import spring2024.cs472.hotelwebsite.repositories.AccountRepository;
import spring2024.cs472.hotelwebsite.repositories.ReservationDetailsRepository;
import spring2024.cs472.hotelwebsite.repositories.RoomReservationRepository;
import spring2024.cs472.hotelwebsite.services.AccountService;
import spring2024.cs472.hotelwebsite.services.CartService;
import spring2024.cs472.hotelwebsite.services.RoomReservationService;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * This class is responsible for managing reservations on the admin side of the hotel website.
 * It provides functionality to view, update, and delete reservations.
 * ReservationsAdminController handles operations related to managing reservations, such as listing all reservations,
 * updating reservation details, and deleting reservations.
 * It interacts with services and repositories to perform CRUD operations on reservation entities.
 * The class ensures that only authenticated administrators can access the reservation management functionality.
 * ReservationsAdminController is essential for maintaining the integrity and accuracy of reservation data in the system.
 *
 * @author Team ABCFG
 */
@Controller
public class ReservationsAdminController {

    // Attributes
    @Autowired
    ReservationDetailsRepository reservationDetailsRepository;
    @Autowired
    RoomReservationService roomReservationService;
    @Autowired
    private CartService cartService;
    @Autowired
    private RoomReservationRepository roomReservationRepository;
    @Autowired
    private AccountRepository accountRepository;

    /**
     * Retrieves all reservations for display on the admin side.
     * Redirects to the login page if the session does not contain an admin.
     *
     * @param model the model to which reservation details will be added
     * @param session the HTTP session containing the admin user
     * @return the view name for displaying the list of reservations
     */
    @GetMapping("/reservationsAdmin/list")
    public String checkedOut(Model model, HttpSession session) {
        if(session.getAttribute("admin") == null) {
            return "redirect:/login";
        }
        model.addAttribute("allDetails", reservationDetailsRepository.findAll());
        return "updateReservationsAdminList";
    }

    /**
     * Displays the form for updating a specific reservation.
     * Redirects to the login page if the session does not contain an admin.
     *
     * @param id the ID of the reservation to update
     * @param model the model to which reservation details will be added
     * @param session the HTTP session containing the admin user
     * @return the view name for updating the reservation
     */
    @GetMapping("/reservationsAdmin/edit/{id}")
    public String
    showUpdateForm(@PathVariable int id, Model model, HttpSession session){
        if(session.getAttribute("admin") == null) {
            return "redirect:/login";
        }
        RoomReservation roomReservation = roomReservationService.getRoomReservationById(id);
        model.addAttribute("roomReservation", roomReservation);
        Optional<LocalDate> startDate = roomReservation.getDates().stream().findFirst();
        Optional<LocalDate> endDate = roomReservation.getDates().stream()
                .skip(roomReservation.getDates().size() - 1).findFirst();
        RoomReservationDates dates = new RoomReservationDates(startDate.orElse(LocalDate.now()),
                endDate.orElse(LocalDate.now()).plusDays(1));
        model.addAttribute("dates", dates);
        return "updateReservationsAdmin";
    }

    /**
     * Updates the reservation with the specified ID.
     * Redirects to the login page if the session does not contain an admin.
     *
     * @param id the ID of the reservation to update
     * @param dates the updated reservation dates
     * @param result the binding result for form validation
     * @param model the model to which updated reservation details will be added
     * @param session the HTTP session containing the admin user
     * @return the view name for redirecting to the list of reservations after updating
     */
    @PostMapping("/updateReservationsAdmin/update/{id}")
    public String updateReservationsAdmin(@PathVariable int id, @ModelAttribute("dates") RoomReservationDates dates,  BindingResult result, Model model, HttpSession session){
        if(session.getAttribute("admin") == null) {
            return "redirect:/login";
        }
        if(result.hasErrors()) {
            return "updateReservationsAdmin";
        }
        LocalDate startDate = dates.getStartDate();
        LocalDate endDate = dates.getEndDate();

        RoomReservation roomReservation = roomReservationService.getRoomReservationById(id);
        int previousTotal = roomReservation.getTotal();

        roomReservation.setDates(cartService.setupDateList(startDate, endDate));
        int totalDelta = previousTotal - roomReservation.getTotal();

        roomReservationRepository.save(roomReservation);

        ReservationDetails details = roomReservationService.getDetailsByRoomReservationId(id);
        details.setTotal(details.getTotal() + totalDelta);

        reservationDetailsRepository.save(details);
        return "redirect:/reservationsAdmin/list";
    }

    /**
     * Deletes the room reservation with the specified ID.
     * Redirects to the login page if the session does not contain an admin.
     *
     * @param id the ID of the reservation to delete
     * @param model the model to which reservation details will be added
     * @param session the HTTP session containing the admin user
     * @return the view name for redirecting to the list of reservations after deletion
     */
    @GetMapping("/updateReservationsAdmin/delete/{id}")
    public String
    deleteRoom(@PathVariable int id, Model model, HttpSession session){
        if(session.getAttribute("admin") == null) {
            return "redirect:/login";
        }
        RoomReservation roomReservation = roomReservationService.getRoomReservationById(id);
        int total = roomReservation.getTotal();
        ReservationDetails details = roomReservationService.getDetailsByRoomReservationId(id);
        details.getRoomReservations().remove(roomReservation);
        roomReservationRepository.delete(roomReservation);
        if(details.getRoomReservations().isEmpty()){
            Guest guest = details.getGuest();
            List<ReservationDetails> detailsList = guest.getCurrentReservations();
            detailsList.remove(details);
            guest.setCurrentReservations(detailsList);
            accountRepository.save(guest);

            reservationDetailsRepository.delete(details);
        } else {
            details.setTotal(details.getTotal() - total);
            reservationDetailsRepository.save(details);
        }
        return "redirect:/reservationsAdmin/list";
    }
}
