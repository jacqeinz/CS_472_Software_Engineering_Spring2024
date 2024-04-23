package spring2024.cs472.hotelwebsite.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import spring2024.cs472.hotelwebsite.entities.Account;
import spring2024.cs472.hotelwebsite.entities.Admin;
import spring2024.cs472.hotelwebsite.entities.Room;
import spring2024.cs472.hotelwebsite.repositories.AccountRepository;
import spring2024.cs472.hotelwebsite.entities.Guest;
import jakarta.servlet.http.HttpSession;
import spring2024.cs472.hotelwebsite.services.AccountService;

@Controller
public class SignUpController {
    @Autowired
    private AccountRepository accountRepository;
    private AccountService accountService;


    public SignUpController(AccountService accountService) {this.accountService = accountService;}
    @GetMapping("/SignUpGuest")
    public String signUp(Guest guest, Model model) {
        model.addAttribute("Guest", new Guest());
        return "SignUpGuest";
    }

    @PostMapping("/SignUpGuest")
    public String signUpGuest(@ModelAttribute("Guest") Guest guest, Model model, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "SignUpGuest";
        }
        accountRepository.save(guest);
        guest.add(guest);
        return "redirect:/";

    }

    @GetMapping("SignUpAdmin")
    public String signUpAdmin(Model model) {
        model.addAttribute("admin", new Admin());
        return "SignUpAdmin";
    }

    @PostMapping("/SignUpAdmin")
    public String signUpAdmin(@ModelAttribute("Admin") Admin admin, Model model, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "SignUpAdmin";
        }
        accountRepository.save(admin) ;
        admin.add(admin);
        return "redirect:/";

    }
    @GetMapping("/AccountIndex")
    public String showUserList(Model model) {
        model.addAttribute("Admins", accountService.getAllAdmins());
        return "AccountIndex";
    }
    @GetMapping("/admin/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Admin admin = (Admin) accountRepository.findById(id);
//                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        model.addAttribute("admin", admin);
        return "updateAdmin";
    }
    @PostMapping("/admin/edit/{id}")
    public String updateAdmin(@PathVariable("id") long id, Admin admin,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "updateAdmin";
        }

        accountRepository.save(admin);
        return "redirect:/AccountIndex";
    }
    @GetMapping("/admin/delete/{id}")
    public String deleteAdmin(@PathVariable("id") long id, Model model) {
        Admin admin = (Admin) accountRepository.findById(id);
//                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        accountRepository.delete(admin);
        return "redirect:/AccountIndex"
                ;
    }

    @GetMapping("/guest/edit")
    public String showUpdateFormGuest( Model model, HttpSession session) {
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
