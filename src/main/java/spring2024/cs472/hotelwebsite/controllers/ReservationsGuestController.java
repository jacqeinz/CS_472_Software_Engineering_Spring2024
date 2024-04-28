package spring2024.cs472.hotelwebsite.controllers;

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
import spring2024.cs472.hotelwebsite.repositories.ReservationDetailsRepository;

@Controller
public class ReservationsGuestController {


    @Autowired
    private ReservationDetailsRepository reservationDetailsRepository;

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
    showUpdateForm(@PathVariable int id, Model model){
        ReservationDetails reservationDetails = reservationDetailsRepository.findById(id);
        model.addAttribute("ReservationDetails", reservationDetailsRepository);
        return "updateReservationsGuest";
    }

    @PostMapping("/updateReservationsGuest/update/{id}")
    public String updateReservationsGuest(@PathVariable int id, ReservationDetails reservationDetails, BindingResult result, Model model){
        if(result.hasErrors()) {
            return "updateReservationsGuest";
        }
        model.addAttribute("details", reservationDetailsRepository.findById(id));
        return "redirect:/reservationsGuest";
    }

    @GetMapping("/roomReservation/delete/{id}")
    public String
    deleteReservationGuest(@PathVariable int id, Model model){
        ReservationDetails reservationDetails = reservationDetailsRepository.findById(id);
        reservationDetailsRepository.delete(reservationDetails);
        return "redirect:/reservationsGuest";
    }
}
