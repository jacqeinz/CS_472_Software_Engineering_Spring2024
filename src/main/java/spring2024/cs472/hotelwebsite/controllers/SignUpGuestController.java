/**
 * SignUpGuestController.java
 */
package spring2024.cs472.hotelwebsite.controllers;

// Imports necessary for the class
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import spring2024.cs472.hotelwebsite.entities.Guest;
import spring2024.cs472.hotelwebsite.repositories.AccountRepository;
import spring2024.cs472.hotelwebsite.services.AccountService;

/**
 * This class is responsible for managing guest sign-up.
 *
 * @author Team ABCFG
 */
@Controller
public class SignUpGuestController {

    // Attributes
    @Autowired
    private AccountRepository accountRepository;
    private AccountService accountService;

    /**
     * Constructs a SignUpGuestController with the provided AccountService.
     *
     * @param accountService The service for account-related operations.
     */
    public SignUpGuestController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Displays the guest sign-up form.
     *
     * @param guest  The guest object to be populated with sign-up information.
     * @param model  The model to which attributes are added.
     * @return       The view for the guest sign-up form.
     */
    @GetMapping("/SignUpGuest")
    public String signUp(Guest guest, Model model) {
        model.addAttribute("Guest", new Guest());
        return "signUpGuest";
    }

    /**
     * Processes the guest sign-up form submission.
     *
     * @param guest          The guest object containing sign-up information.
     * @param model          The model to which attributes are added.
     * @param bindingResult  The result of the validation.
     * @param session        The HTTP session.
     * @return               The redirection to the login page if sign-up is successful; otherwise, back to the sign-up form.
     */
    @PostMapping("/SignUpGuest")
    public String signUpGuest(@ModelAttribute("Guest") Guest guest, Model model, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "signUpGuest";
        }
        accountRepository.save(guest);
        guest.add(guest);
        return "redirect:/login";

    }
}
