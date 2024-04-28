package spring2024.cs472.hotelwebsite.controllers;

import jakarta.servlet.http.HttpSession;
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
import spring2024.cs472.hotelwebsite.repositories.ReservationDetailsRepository;

//View of all reservations admin side
@Controller
public class ReservationsAdminController {

    ReservationDetailsRepository reservationDetailsRepository;


    @GetMapping("/reservationsAdmin")
    public String checkedOut(Model model, HttpSession session, @RequestParam(value = "id") int id) {
        if(session.getAttribute("admin") == null) {
            return "redirect:/login";
        }
        model.addAttribute("details", reservationDetailsRepository.findById(id));
        return "updateReservationsAdmin";
    }

    @GetMapping("/reservationsAdmin/edit/{id}")
    public String
    showUpdateForm(@PathVariable int id, Model model){
        ReservationDetails reservationDetails = reservationDetailsRepository.findById(id);
        model.addAttribute("ReservationDetails", reservationDetailsRepository);
        return "updateReservationsAdmin";
    }

    @PostMapping("/updateReservationsAdmin/update/{id}")
    public String updateReservationsAdmin(@PathVariable int id, ReservationDetails reservationDetails, BindingResult result, Model model){
        if(result.hasErrors()) {
            return "reservationsAdmin";
        }
        model.addAttribute("details", reservationDetailsRepository.findById(id));
        return "redirect:/reservationsAdmin";
    }

    @GetMapping("/updateReservationsAdmin/delete/{id}")
    public String
    deleteRoom(@PathVariable int id, Model model){
        ReservationDetails reservationDetails = reservationDetailsRepository.findById(id);
        reservationDetailsRepository.delete(reservationDetails);
        return "redirect:/reservationsAdmin";
    }
}
