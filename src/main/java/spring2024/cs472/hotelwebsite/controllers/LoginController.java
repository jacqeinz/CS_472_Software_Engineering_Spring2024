package spring2024.cs472.hotelwebsite.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring2024.cs472.hotelwebsite.entities.*;
import spring2024.cs472.hotelwebsite.services.*;

@Controller
public class LoginController {

    @Autowired
    private InitService initService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/login")
    public String loginPage(Model model, HttpSession session) {
        if(session.getAttribute("Guest") != null )
            return "redirect:/guestDashboard";
        else if (session.getAttribute("Admin") != null )
            return "redirect:/adminDashboard";

        initService.init();
        model.addAttribute("error", false);
        model.addAttribute("logout", false);
        return "login";
    }

    @PostMapping("/login")
    public String loginPage(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
        Account account = accountService.validateLogin(username, password);
        if (account == null) {
            model.addAttribute("error", true);
            return "login";
        } else if (account instanceof Guest) {
            session.setAttribute("guest", (Guest) account);
            return "redirect:/guestDashboard";
        } else if (account instanceof Admin) {
            session.setAttribute("admin", (Admin) account);
            return "redirect:/adminDashboard";
        }
        model.addAttribute("error", true);
        return "login";
    }

}
