package spring2024.cs472.hotelwebsite.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminDashboardController {
    @GetMapping("/adminDashboard")
    public String adminDashboard(Model model, HttpSession session) {
        if(session.getAttribute("admin") == null) {
            return "redirect:/login";
        }
        System.out.println(session.getAttribute("admin"));
        return "adminDashboard";
    }

    @GetMapping("/logoutAdmin")
    public String logoutAdmin(Model model, HttpSession session) {
        if(session.getAttribute("admin") == null){
            return "redirect:/login";
        }
        session.invalidate();
        return "logoutAdmin";
    }

}
