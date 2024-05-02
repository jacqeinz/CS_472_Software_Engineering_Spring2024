package spring2024.cs472.hotelwebsite.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import spring2024.cs472.hotelwebsite.entities.Guest;
import spring2024.cs472.hotelwebsite.repositories.AccountRepository;
import spring2024.cs472.hotelwebsite.services.AccountService;
@Controller
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
                              BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {

            return "redirect:/updateGuest";
        }

        accountRepository.save(guest);
        session.setAttribute("guest", guest);
        return "redirect:/guestDashboard";
    }
}