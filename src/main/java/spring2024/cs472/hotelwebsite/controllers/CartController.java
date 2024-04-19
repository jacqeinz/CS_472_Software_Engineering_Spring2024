package spring2024.cs472.hotelwebsite.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring2024.cs472.hotelwebsite.entities.Guest;
import spring2024.cs472.hotelwebsite.entities.Room;
import spring2024.cs472.hotelwebsite.services.CartService;
import spring2024.cs472.hotelwebsite.services.RoomService;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Controller
public class CartController {

    @Autowired
    private RoomService roomService;
    @Autowired
    private CartService cartService;

    @GetMapping("/cart")
    public String availableReservations(Model model, HttpSession session) {
        if(session.getAttribute("guest") == null) {
            return "redirect:/login";
        }
        Guest guest = (Guest) session.getAttribute("guest");
        model.addAttribute("cart", guest.getCart());
        return "cart";
    }

    @PostMapping("/cart/checkout")
    public String checkoutCart(Model model, HttpSession session) {
        if(session.getAttribute("guest") == null) {
            return "redirect:/login";
        }
        Guest guest = (Guest) session.getAttribute("guest");
        Long id = cartService.checkoutCart(guest.getCart(), guest).getId();
        return "redirect:/checkedOut?id="+id;
    }
//
//    @PostMapping("/availableReservations/addToCart")
//    public String addToCart(Model model, HttpSession session, @RequestParam List<Room> selectedRooms) {
//        Guest guest = (Guest) session.getAttribute("guest");
//        cartService.addRoomReservations(guest.getCart(), selectedRooms, (LocalDate) model.getAttribute("start"),
//                (LocalDate)  model.getAttribute("end"));
//        return "redirect:/cart";
//    }

}
