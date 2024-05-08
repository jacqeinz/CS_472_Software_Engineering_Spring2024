/**
 * ReservationsGuestController.java
 */
package spring2024.cs472.hotelwebsite.controllers;

// Imports necessary for the class
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import spring2024.cs472.hotelwebsite.entities.Guest;
import spring2024.cs472.hotelwebsite.entities.ReservationDetails;
import spring2024.cs472.hotelwebsite.entities.RoomReservation;
import spring2024.cs472.hotelwebsite.entities.RoomReservationDates;
import spring2024.cs472.hotelwebsite.repositories.AccountRepository;
import spring2024.cs472.hotelwebsite.repositories.ReservationDetailsRepository;
import spring2024.cs472.hotelwebsite.repositories.RoomReservationRepository;
import spring2024.cs472.hotelwebsite.services.CartService;
import spring2024.cs472.hotelwebsite.services.RoomReservationService;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * This class is responsible for managing reservations on the guest side of the hotel website.
 * It provides functionality to view, update, and delete reservations made by guests.
 * ReservationsGuestController handles operations related to managing guest reservations,
 * such as updating reservation details and canceling reservations.
 * It interacts with services and repositories to perform CRUD operations on reservation entities.
 * The class ensures that only authenticated guests can access their reservation management functionality.
 * ReservationsGuestController is crucial for allowing guests to manage their reservations effectively.
 *
 * @author Team ABCFG
 */
@Controller
public class ReservationsGuestController {

    // Attributes
    @Autowired
    private ReservationDetailsRepository reservationDetailsRepository;
    @Autowired
    private RoomReservationService roomReservationService;
    @Autowired
    private CartService cartService;
    @Autowired
    private RoomReservationRepository roomReservationRepository;
    @Autowired
    private AccountRepository accountRepository;

    /**
     * Displays the details of a specific reservation for the guest.
     * If the guest is not authenticated, redirects to the login page.
     * Retrieves the reservation details based on the provided ID and adds them to the model.
     * The view renders the reservation details for the guest to review.
     *
     * @param model   The model to which reservation details will be added.
     * @param session The HttpSession to check if the guest is authenticated.
     * @param id      The ID of the reservation to display.
     * @return The view name for displaying the reservation details.
     */
    @GetMapping("/reservationsGuest")
    public String checkedOut(Model model, HttpSession session, @RequestParam(value = "id") int id) {
        if(session.getAttribute("guest") == null) {
            return "redirect:/login";
        }
        Guest guest = (Guest) session.getAttribute("guest");
        model.addAttribute("details", reservationDetailsRepository.findById(id));
        return "updateReservationsGuest";
    }

    /**
     * Displays the form to update a reservation for the guest.
     * If the guest is not authenticated, redirects to the login page.
     * Retrieves the reservation details based on the provided ID and adds them to the model.
     * The view renders a form pre-filled with reservation details for the guest to update.
     *
     * @param id      The ID of the reservation to update.
     * @param model   The model to which reservation details will be added.
     * @param session The HttpSession to check if the guest is authenticated.
     * @return The view name for displaying the reservation update form.
     */
    @GetMapping("/roomReservation/edit/{id}")
    public String showUpdateForm(@PathVariable int id, Model model, HttpSession session){
        if(session.getAttribute("guest") == null) {
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
        return "updateReservationsGuest";
    }

    /**
     * Updates the reservation details for the guest.
     * If the guest is not authenticated, redirects to the login page.
     * Validates the submitted form data, updates the reservation dates,
     * calculates the total cost difference, and saves the changes to the database.
     * Redirects the guest to the dashboard after updating the reservation.
     *
     * @param id      The ID of the reservation to update.
     * @param dates   The updated reservation dates submitted by the guest.
     * @param result  The BindingResult to check for form validation errors.
     * @param model   The model to which reservation details will be added.
     * @param session The HttpSession to check if the guest is authenticated.
     * @return The redirect URL to the guest dashboard.
     */
    @PostMapping("/roomReservation/update/{id}")
    public String updateReservationsGuest(@PathVariable int id, @ModelAttribute("dates") RoomReservationDates dates, BindingResult result, Model model, HttpSession session){
        if(session.getAttribute("guest") == null) {
            return "redirect:/login";
        }
        if(result.hasErrors()) {
            return "updateReservationsGuest";
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

        return "redirect:/dashboardGuest";
    }

    /**
     * Cancels a reservation for the guest.
     * If the guest is not authenticated, redirects to the login page.
     * Deletes the specified reservation and updates the total cost for the associated reservation details.
     * If the deleted reservation was the only one for the guest, also removes the details.
     * Redirects the guest to the dashboard after canceling the reservation.
     *
     * @param id      The ID of the reservation to delete.
     * @param model   The model to which reservation details will be added.
     * @param session The HttpSession to check if the guest is authenticated.
     * @return The redirect URL to the guest dashboard.
     */
    @GetMapping("/roomReservation/delete/{id}")
    public String deleteReservationGuest(@PathVariable int id, Model model, HttpSession session){
        if(session.getAttribute("guest") == null) {
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
        return "redirect:/dashboardGuest";
    }
}
