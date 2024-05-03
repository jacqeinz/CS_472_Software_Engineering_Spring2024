package spring2024.cs472.hotelwebsite.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import spring2024.cs472.hotelwebsite.entities.Guest;
import spring2024.cs472.hotelwebsite.repositories.AccountRepository;
import spring2024.cs472.hotelwebsite.services.AccountService;
@Controller
@SessionAttributes("guest")
public class GuestAccountCrudController {
    @Autowired
    private AccountRepository accountRepository;
    private AccountService accountService;


    public GuestAccountCrudController(AccountService accountService) {this.accountService = accountService;}

    @GetMapping("/guest/edit")
    public String showUpdateFormGuest(Model model, HttpSession session) {
        model.addAttribute("guest", (Guest) session.getAttribute("guest"));
        return "updateGuest";

    }


    @PostMapping("/guest/update")
    public String updateGuest(@ModelAttribute("guest") Guest guest,
                              BindingResult result, Model model, HttpSession session, SessionStatus status) {
        if (result.hasErrors()) {

            return "redirect:/updateGuest";
        }

        accountRepository.save(guest);
        session.setAttribute("guest", guest);
        status.setComplete();
        return "redirect:/guestDashboard";
    }
}