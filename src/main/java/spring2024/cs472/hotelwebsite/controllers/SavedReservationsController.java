package spring2024.cs472.hotelwebsite.controllers;

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

@Controller
public class SavedReservationsController {

    private final ReservationDetailsRepository reservationDetailsRepository;

    public SavedReservationsController(ReservationDetailsRepository reservationDetailsRepository) {

        this.reservationDetailsRepository = reservationDetailsRepository;
    }

    @GetMapping("/SavedReservationReport")
    public String showReservationList(Model model, HttpSession session){
//        if(session.getAttribute("admin") == null) {
//            return "redirect:/login";
//        }
        if(session.getAttribute("admin") == null){
            return "redirect:/login";
        }
        model.addAttribute("SavedReservations", reservationDetailsRepository.findAll());
        return "SavedReservationReport";
    }

}
