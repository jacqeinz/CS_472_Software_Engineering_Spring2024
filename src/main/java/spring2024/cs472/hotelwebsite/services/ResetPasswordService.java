package spring2024.cs472.hotelwebsite.services;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring2024.cs472.hotelwebsite.entities.Account;
import spring2024.cs472.hotelwebsite.entities.PasswordResetToken;
import spring2024.cs472.hotelwebsite.repositories.AccountRepository;
import spring2024.cs472.hotelwebsite.repositories.TokenRepository;

@Service
public class ResetPasswordService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TokenRepository tokenRepository;

    public String resetPassword(String token, Model model, HttpSession session) {
        PasswordResetToken reset = tokenRepository.findByToken(token);
        if (reset != null && accountService.isNotExpired(reset.getExpiryDateTime())) {
            model.addAttribute("email", reset.getAccount().getEmail());
            return "resetPassword";
        }
        return "redirect:/forgotPassword?error";
    }

    public String resetPasswordProcess(String token,  String password,  String email) {
        Account account = accountRepository.findByEmail(email);
        System.out.println(account);
        if (account != null) {
            account.setUserPassword(password);
            accountRepository.save(account);
        }
        return "redirect:/login";
    }
}
