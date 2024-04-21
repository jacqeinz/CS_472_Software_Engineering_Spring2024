package spring2024.cs472.hotelwebsite.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import spring2024.cs472.hotelwebsite.entities.Admin;
import spring2024.cs472.hotelwebsite.repositories.AccountRepository;
import spring2024.cs472.hotelwebsite.entities.Guest;
import jakarta.servlet.http.HttpSession;
@Controller
public class SignUpGuestController {
    @Autowired
    private AccountRepository accountRepository;

    @PostMapping("/SignUpGuest")
    public String signUpGuest(Model model, HttpSession session) {
        Guest guest = new Guest();
        accountRepository.save(guest);
        guest.add(guest);

        return "redirect:/index";

    }
    @PostMapping("/SignUpAdmihn")
    public Admin signUpAdmin(Model model, HttpSession session) {
        Admin admin = new Admin();
        accountRepository.save(admin) ;
        admin.add(admin);

        return admin;

    }
}
