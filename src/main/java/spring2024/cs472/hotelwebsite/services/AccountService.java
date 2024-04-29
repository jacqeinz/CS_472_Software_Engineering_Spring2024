package spring2024.cs472.hotelwebsite.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import spring2024.cs472.hotelwebsite.entities.*;
import spring2024.cs472.hotelwebsite.repositories.AccountRepository;
import spring2024.cs472.hotelwebsite.repositories.TokenRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    private TokenRepository tokenRepository;

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account validateLogin(String username, String password) {
        for (Account account : getAllAccounts()) {
            if (account.getUserName().equals(username) && account.getUserPassword().equals(password)) {
                return account;
            }
        }
        return null;
    }

    public List<Admin> getAllAdmins() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().filter(a -> a instanceof Admin).map(a -> (Admin)a).collect(Collectors.toList());
    }


    public void save(Account account) {
        accountRepository.save(account);
    }

     public String sendEmail(Account account) {
        try {
            String resetLink = generateRestToken(account);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(account.getEmail());
            message.setSubject("Password Reset");
            message.setText("Click here to reset your password: " + resetLink);

            javaMailSender.send(message);
            return "Success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
     }

     private String generateRestToken(Account account) {
     UUID uuid = UUID.randomUUID();
     LocalDateTime currentDateTime = LocalDateTime.now();
     LocalDateTime expiryDateTime = currentDateTime.plusMinutes(20);
     PasswordResetToken resetToken = new PasswordResetToken();
     resetToken.setAccount(account);
     resetToken.setToken(uuid.toString());
     resetToken.setExpiryDateTime(expiryDateTime);
     resetToken.setAccount(account);
     PasswordResetToken token = tokenRepository.save(resetToken);
     if (token != null) {
     String endpointUrl = "http://localhost:8080/resetPassword";
     return endpointUrl + "/" + resetToken.getToken();
     }
     return "";
     }

     public boolean hasExpired(LocalDateTime expiryDateTime) {
     LocalDateTime currentDateTime = LocalDateTime.now();
     return expiryDateTime.isAfter(currentDateTime);
     }

     public boolean sendConfirmationEmail(Account account, ReservationDetails details) {
         try {
             SimpleMailMessage message = new SimpleMailMessage();
             message.setTo(account.getEmail());
             message.setSubject("ABCFG Hotel Reservation Confirmation");
             StringBuilder builder = new StringBuilder();
             builder.append("Thank you for reserving rooms at our hotel.\n")
                     .append("Your purchase has been successfully confirmed.\n")
                     .append("Total Price: ").append(details.getTotal()).append( "\n");

             message.setText(builder.toString());

             javaMailSender.send(message);
             return true;
         } catch (Exception e) {
             e.printStackTrace();
             return false;
         }
     }
}
