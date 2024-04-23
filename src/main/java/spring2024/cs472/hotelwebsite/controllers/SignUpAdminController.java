package spring2024.cs472.hotelwebsite.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import spring2024.cs472.hotelwebsite.entities.Admin;
import spring2024.cs472.hotelwebsite.repositories.AccountRepository;
import jakarta.servlet.http.HttpSession;
import spring2024.cs472.hotelwebsite.services.AccountService;

@Controller
public class SignUpAdminController {
    @Autowired
    private AccountRepository accountRepository;
    private AccountService accountService;


    public SignUpAdminController(AccountService accountService) {this.accountService = accountService;}


    @GetMapping("SignUpAdmin")
    public String signUpAdmin(Model model) {
        model.addAttribute("admin", new Admin());
        return "signUpAdmin";
    }

    @PostMapping("/SignUpAdmin")
    public String signUpAdmin(@ModelAttribute("Admin") Admin admin, Model model, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "signUpAdmin";
        }
        accountRepository.save(admin) ;
        admin.add(admin);
        return "redirect:/adminDashboard";

    }





}
