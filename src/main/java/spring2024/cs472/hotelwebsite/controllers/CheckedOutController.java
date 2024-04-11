package spring2024.cs472.hotelwebsite.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring2024.cs472.hotelwebsite.entities.Guest;
import spring2024.cs472.hotelwebsite.repositories.ReservationDetailsRepository;

@Controller
public class CheckedOutController {

    @Autowired
    private ReservationDetailsRepository reservationDetailsRepository;

    @GetMapping("/checkedOut")
    public String checkedOut(Model model, HttpSession session, @RequestParam(value = "id") int id) {
        if(session.getAttribute("guest") == null) {
            return "redirect:/login";
        }
        Guest guest = (Guest) session.getAttribute("guest");
        model.addAttribute("details", reservationDetailsRepository.findById(id));
        return "checkedOut";
    }

//    @PostMapping("/cart/checkout")
//    public String checkoutCart(Model model, HttpSession session) {
//        Guest guest = (Guest) session.getAttribute("guest");
//        return "availableReservations";
//    }
//
//    @PostMapping("/availableReservations/addToCart")
//    public String addToCart(Model model, HttpSession session, @RequestParam List<Room> selectedRooms) {
//        Guest guest = (Guest) session.getAttribute("guest");
//        cartService.addRoomReservations(guest.getCart(), selectedRooms, (LocalDate) model.getAttribute("start"),
//                (LocalDate)  model.getAttribute("end"));
//        return "redirect:/cart";
//    }

}
