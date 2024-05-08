/**
 * PaymentController.java
 */
package spring2024.cs472.hotelwebsite.controllers;

// Imports necessary for the class
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

/**
 * Controller responsible for handling payment and checkout processes.
 * Manages displaying the payment/checkout page and submitting payment information.
 * Retrieves guest information from the session and interacts with the cart service and account repository.
 * Redirects to the checked-out page after processing payment.
 *
 * @author Team ABCFG
 */
@Controller
public class PaymentController {

    // Attributes
    @Autowired
    private CartService cartService;
    @Autowired
    private AccountRepository accountRepository;

    /**
     * Displays the payment/checkout page.
     *
     * @param model The Model object to add attributes for the view.
     * @param session The HttpSession object to retrieve guest information.
     * @return The view name for displaying the payment/checkout page.
     */
    @GetMapping("/checkout")
    public String payment(Model model, HttpSession session) {
        if(session.getAttribute("guest") == null){
            return "redirect:/login";
        }
        model.addAttribute("guest", (Guest) session.getAttribute("guest")); // Add guest attribute to model
        return "checkout"; // Display the payment/checkout page
    }

    /**
     * Submits the payment information and processes the checkout.
     *
     * @param creditCardNum The credit card number entered by the user.
     * @param model The Model object to add attributes for the view.
     * @param session The HttpSession object to retrieve guest information.
     * @return The view name for redirecting to the checked-out page.
     */
    @PostMapping("/checkout")
    public String submitPayment(@ModelAttribute String creditCardNum, Model model, HttpSession session){
        if(session.getAttribute("guest") == null){
            return "redirect:/login";
        }
        Guest guest = (Guest) session.getAttribute("guest"); // Retrieve guest from session
        Long id = cartService.checkoutCart(guest.getCart(), guest).getId(); // Checkout the cart and retrieve the ID
        accountRepository.save(guest); // Save guest information
        return "redirect:/checkedOut?id="+id; // Redirect to the checked-out page with ID parameter
    }
}
