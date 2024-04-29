package spring2024.cs472.hotelwebsite.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import spring2024.cs472.hotelwebsite.entities.Guest;
import spring2024.cs472.hotelwebsite.repositories.AccountRepository;
import spring2024.cs472.hotelwebsite.services.AccountService;

@Controller
public class GuestDashboardController {
    @Autowired
    AccountRepository accountRepo;

    @GetMapping("/guestDashboard")
    public String guestDashboard(Model model, HttpSession session) {
        if(session.getAttribute("guest") == null) {
            return "redirect:/login";
        }
        Guest oldGuest = (Guest) session.getAttribute("guest");
        Guest freshGuest = (Guest) accountRepo.findById(oldGuest.getId()).orElse(null);
        session.setAttribute("guest", freshGuest);
        System.out.println(session.getAttribute("guest"));
        return "guestDashboard";
    }



}
