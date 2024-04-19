package spring2024.cs472.hotelwebsite.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GuestDashboardController {

    @GetMapping("/guestDashboard")
    public String guestDashboard(Model model, HttpSession session) {
        if(session.getAttribute("guest") == null) {
            return "redirect:/login";
        }
        System.out.println(session.getAttribute("guest"));
        return "guestDashboard";
    }

}
