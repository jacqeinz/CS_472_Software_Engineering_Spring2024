package spring2024.cs472.hotelwebsite.controllers;

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

@Controller
public class ReservationsGuestController {


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

    @GetMapping("/reservationsGuest")
    public String checkedOut(Model model, HttpSession session, @RequestParam(value = "id") int id) {
        if(session.getAttribute("guest") == null) {
            return "redirect:/login";
        }
        Guest guest = (Guest) session.getAttribute("guest");
        model.addAttribute("details", reservationDetailsRepository.findById(id));
        return "updateReservationsGuest";
    }

    @GetMapping("/roomReservation/edit/{id}")
    public String
    showUpdateForm(@PathVariable int id, Model model, HttpSession session){
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

        return "redirect:/guestDashboard";
    }

    @GetMapping("/roomReservation/delete/{id}")
    public String
    deleteReservationGuest(@PathVariable int id, Model model, HttpSession session){
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


        return "redirect:/guestDashboard";
    }
}
