/**
 * AdminAccountCrudController.java
 */
package spring2024.cs472.hotelwebsite.controllers;

// Imports necessary for the class
import jakarta.servlet.http.HttpSession;
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

/**
 * This class named AdminAccountCrudController is responsible for handling CRUD operations
 * related to Admin accounts.
 *
 * @author Team ABCFG
 */
@Controller
public class AdminAccountCrudController {

    // Attributes
    @Autowired
    private AccountRepository accountRepository;
    private AccountService accountService;

    /**
     * Constructor for AdminAccountCrudController class.
     *
     * @param accountService The AccountService to be used.
     */
    public AdminAccountCrudController(AccountService accountService) {this.accountService = accountService;}

    /**
     * This method retrieves all Admin accounts and displays them on the accountIndex page.
     *
     * @param model The Model object to add attributes for the view.
     * @return The view name for displaying Admin accounts.
     */
    @GetMapping("/AccountIndex")
    public String showUserList(Model model, HttpSession session) {
        if(session.getAttribute("admin") == null){
            return "redirect:/login";
        }
        model.addAttribute("Admins", accountService.getAllAdmins());
        return "accountIndex";
    }

    /**
     * This method retrieves an Admin account for editing and displays the updateAdmin form.
     *
     * @param id The ID of the Admin account to be edited.
     * @param model The Model object to add attributes for the view.
     * @return The view name for updating an Admin account.
     */
    @GetMapping("/admin/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model, HttpSession session) {
        if(session.getAttribute("admin") == null){
            return "redirect:/login";
        }
        Admin admin = (Admin) accountRepository.findById(id);
//                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        model.addAttribute("admin", admin);
        return "updateAdmin";
    }

    /**
     * This method updates an Admin account.
     *
     * @param id The ID of the Admin account to be updated.
     * @param admin The updated Admin object.
     * @param result The BindingResult object for validation.
     * @return The redirect view name after updating the Admin account.
     */
    @PostMapping("/admin/edit/{id}")
    public String updateAdmin(@PathVariable("id") long id, Admin admin,
                              BindingResult result, HttpSession session) {
        if(session.getAttribute("admin") == null){
            return "redirect:/login";
        }
        if (result.hasErrors()) {
            return "updateAdmin";
        }

        accountRepository.save(admin);
        return "redirect:/AccountIndex";
    }

    /**
     * This method deletes an Admin account.
     *
     * @param id The ID of the Admin account to be deleted.
     * @param model The Model object to add attributes for the view.
     * @return The redirect view name after deleting the Admin account.
     */
    @GetMapping("/admin/delete/{id}")
    public String deleteAdmin(@PathVariable("id") long id, Model model, HttpSession session) {
        if(session.getAttribute("admin") == null){
            return "redirect:/login";
        }
        Admin admin = (Admin) accountRepository.findById(id);
//                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        accountRepository.delete(admin);
        return "redirect:/AccountIndex"
                ;
    }
}
