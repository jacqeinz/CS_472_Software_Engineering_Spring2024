package spring2024.cs472.hotelwebsite.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
