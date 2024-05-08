package spring2024.cs472.hotelwebsite.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring2024.cs472.hotelwebsite.entities.*;
import spring2024.cs472.hotelwebsite.repositories.AccountRepository;
import spring2024.cs472.hotelwebsite.repositories.TokenRepository;
import spring2024.cs472.hotelwebsite.services.*;

@Controller
public class LoginController {

    @Autowired
    private InitService initService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @GetMapping("/login")
    public String loginPage(Model model, HttpSession session) {
        if (session.getAttribute("guest") != null)
            return "redirect:/guestDashboard";
        else if (session.getAttribute("admin") != null)
            return "redirect:/adminDashboard";

        initService.init();
        model.addAttribute("error", false);
        model.addAttribute("logout", false);
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("guest");
        session.removeAttribute("admin");
        return "redirect:/";
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

    @GetMapping("/forgotPassword")
    public String forgotPassword() {
        return "forgotPassword";
    }

    @PostMapping("/forgotPassword")
    public String forgotPasswordProcess(@RequestParam String email, Model model, HttpSession session) {
        Account account = accountRepository.findByEmail(email);
        String result = "";
        if (account != null) {
            result = accountService.sendEmail(account);
        }
        if (result.equals("Success")) {
            return "redirect:/forgotPassword?success";
        }
        return "redirect:/login";
    }

    @GetMapping("/resetPassword/{token}")
    public String resetPassword(@PathVariable String token, Model model, HttpSession session) {
        PasswordResetToken reset = tokenRepository.findByToken(token);
        if (reset != null && accountService.isNotExpired(reset.getExpiryDateTime())) {
            model.addAttribute("email", reset.getAccount().getEmail());
            return "resetPassword";
        }
        return "redirect:/forgotPassword?error";
    }

    @PostMapping("/resetPassword/{token}")
    public String resetPasswordProcess(@PathVariable String token, @RequestParam String password, @RequestParam String email) {
        Account account = accountRepository.findByEmail(email);
        if (account != null) {
            account.setUserPassword(password);
            accountRepository.save(account);
        }
        return "redirect:/login";
    }
}

