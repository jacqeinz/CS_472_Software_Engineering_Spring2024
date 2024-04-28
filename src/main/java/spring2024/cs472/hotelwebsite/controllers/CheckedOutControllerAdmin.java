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
public class CheckedOutControllerAdmin {
    @Autowired
    private ReservationDetailsRepository reservationDetailsRepository;

    @GetMapping("/checkedOutAdmin")
    public String checkedOut(Model model, HttpSession session) {
        if(session.getAttribute("admin") == null) {
            return "redirect:/login";
        }
        model.addAttribute("details", reservationDetailsRepository.findAll());
        return "checkedOutAdmin";
    }

}
