package spring2024.cs472.hotelwebsite.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import spring2024.cs472.hotelwebsite.entities.Guest;
import spring2024.cs472.hotelwebsite.repositories.AccountRepository;
import spring2024.cs472.hotelwebsite.services.AccountService;

@Controller
public class SignUpGuestController {
    @Autowired
    private AccountRepository accountRepository;
    private AccountService accountService;


    public SignUpGuestController(AccountService accountService) {this.accountService = accountService;}
    @GetMapping("/SignUpGuest")
    public String signUp(Guest guest, Model model) {
        model.addAttribute("Guest", new Guest());
        return "signUpGuest";
    }

    @PostMapping("/SignUpGuest")
    public String signUpGuest(@ModelAttribute("Guest") Guest guest, Model model, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "signUpGuest";
        }
        accountRepository.save(guest);
        guest.add(guest);
        return "redirect:/login";

    }
}
