package spring2024.cs472.hotelwebsite.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import spring2024.cs472.hotelwebsite.entities.Admin;
import spring2024.cs472.hotelwebsite.repositories.AccountRepository;
import spring2024.cs472.hotelwebsite.services.AccountService;
@Controller
public class AdminAccountCrudController {
    @Autowired
    private AccountRepository accountRepository;
    private AccountService accountService;


    public AdminAccountCrudController(AccountService accountService) {this.accountService = accountService;}

    @GetMapping("/AccountIndex")
    public String showUserList(Model model) {
        model.addAttribute("Admins", accountService.getAllAdmins());
        return "accountIndex";
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
                              BindingResult result) {
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
}
