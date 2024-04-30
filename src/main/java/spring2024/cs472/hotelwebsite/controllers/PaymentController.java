package spring2024.cs472.hotelwebsite.controllers;


import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring2024.cs472.hotelwebsite.entities.Guest;
import spring2024.cs472.hotelwebsite.repositories.AccountRepository;
import spring2024.cs472.hotelwebsite.services.AccountService;
import spring2024.cs472.hotelwebsite.services.CartService;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Controller
public class PaymentController {

    @Autowired
    private CartService cartService;
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/checkout")
    public String payment(Model model, HttpSession session) {
        model.addAttribute("guest", (Guest) session.getAttribute("guest"));
        return "checkout";
    }

    @PostMapping("/checkout")
    public String submitPayment(@ModelAttribute String creditCardNum, Model model, HttpSession session){
        Guest guest = (Guest) session.getAttribute("guest");
        Long id = cartService.checkoutCart(guest.getCart(), guest).getId();
        accountRepository.save(guest);
        return "redirect:/checkedOut?id="+id;
    }
}
