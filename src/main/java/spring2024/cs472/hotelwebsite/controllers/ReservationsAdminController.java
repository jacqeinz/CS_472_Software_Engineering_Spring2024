package spring2024.cs472.hotelwebsite.controllers;

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

//View of all reservations admin side
@Controller
public class ReservationsAdminController {

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


    @GetMapping("/reservationsAdmin/list")
    public String checkedOut(Model model, HttpSession session) {
        if(session.getAttribute("admin") == null) {
            return "redirect:/login";
        }

        model.addAttribute("allDetails", reservationDetailsRepository.findAll());

        return "updateReservationsAdminList";
    }

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
