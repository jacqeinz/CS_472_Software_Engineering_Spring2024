/**
 * SignUpAdminController.java
 */
package spring2024.cs472.hotelwebsite.controllers;

// Imports necessary for the class
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

/**
 * This class is responsible for managing admin sign-up.
 *
 * @author Team ABCFG
 */
@Controller
public class SignUpAdminController {

    // Attributes
    @Autowired
    private AccountRepository accountRepository;
    private AccountService accountService;

    /**
     * Constructs a SignUpAdminController with the provided AccountService.
     *
     * @param accountService The service for account-related operations.
     */
    public SignUpAdminController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Displays the admin sign-up form.
     *
     * @param model The model to which attributes are added.
     * @return The view for the admin sign-up form.
     */
    @GetMapping("SignUpAdmin")
    public String signUpAdmin(Model model, HttpSession session) {
        if(session.getAttribute("admin") == null){
            return "redirect:/login";
        }
        model.addAttribute("admin", new Admin());
        return "signUpAdmin";
    }

    /**
     * Processes the admin sign-up form submission.
     *
     * @param admin          The admin object containing sign-up information.
     * @param model          The model to which attributes are added.
     * @param bindingResult  The result of the validation.
     * @param session        The HTTP session.
     * @return               The redirection to the admin dashboard if sign-up is successful; otherwise, back to the sign-up form.
     */
    @PostMapping("/SignUpAdmin")
    public String signUpAdmin(@ModelAttribute("Admin") Admin admin, Model model, BindingResult bindingResult, HttpSession session) {
        if(session.getAttribute("admin") == null){
            return "redirect:/login";
        }
        if (bindingResult.hasErrors()) {
            return "signUpAdmin";
        }
        accountRepository.save(admin) ;
        admin.add(admin);
        return "redirect:/dashboardAdmin";
    }
}
