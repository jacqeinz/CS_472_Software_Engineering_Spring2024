package spring2024.cs472.hotelwebsite.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import spring2024.cs472.hotelwebsite.entities.Guest;
import spring2024.cs472.hotelwebsite.services.CartService;
import spring2024.cs472.hotelwebsite.services.RoomService;
@Controller
public class CartAdminController {

    @Autowired
    private RoomService roomService;
    @Autowired
    private CartService cartService;

    @GetMapping("/cartAdmin")
    public String availableReservations(Model model, HttpSession session) {
        if(session.getAttribute("admin") == null) {
            return "redirect:/login";
        }
        Guest guest = (Guest) session.getAttribute("guest");
        model.addAttribute("cart", guest.getCart());
        return "cartAdmin";
    }

    @PostMapping("/cartAdmin/checkout")
    public String checkoutCart(Model model, HttpSession session, Guest guest) {
        if(session.getAttribute("admin") == null) {
            return "redirect:/login";
        }
        Long id = cartService.checkoutCart(guest.getCart(), guest).getId();
        return "redirect:/checkedOutAdmin";
    }
}
