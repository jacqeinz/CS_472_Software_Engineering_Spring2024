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
import spring2024.cs472.hotelwebsite.repositories.ReservationDetailsRepository;
import spring2024.cs472.hotelwebsite.services.CartService;
import spring2024.cs472.hotelwebsite.services.RoomService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Controller
public class AvailableReservationsAdminController {
    @Autowired
    private RoomService roomService;
    @Autowired
    private CartService cartService;
    @Autowired
    private ReservationDetailsRepository reservationDetailsRepository;

    @GetMapping("/availableReservationsAdmin")
    public String availableReservations(Model model, HttpSession session) {


        if(session.getAttribute("admin") == null) {
            return "redirect:/login";
        }
        model.addAttribute("rooms", new ArrayList<Room>());
        return "availableReservationsAdmin";
    }

    @PostMapping("/availableReservationsAdmin/filter")
    public String filterAvailableReservations(Model model, @RequestParam LocalDate startDate,
                                              @RequestParam LocalDate endDate, HttpSession session) {
        if(session.getAttribute("admin") == null) {
            return "redirect:/login";
        }

        model.addAttribute("reservationDetails", roomService.getRoomsByAvailability(startDate, endDate));
        model.addAttribute("start", startDate);
        model.addAttribute("end", endDate);
        return "availableReservationsAdmin";
    }

    @PostMapping("/availableReservationsAdmin/addToReservations")
    public String addToCart(Model model, HttpSession session, @RequestParam("selected") List<Integer> selectedRooms,
                            @RequestParam LocalDate start, @RequestParam LocalDate end, @RequestParam Guest guest) {
        if(session.getAttribute("admin") == null) {
            return "redirect:/login";
        }

        List<Room> rooms = new ArrayList<>();
        for (Integer selectedRoom : selectedRooms) {
            rooms.add(roomService.getRoomById(selectedRoom));
        }
        cartService.addRoomReservations(guest.getCart(), rooms, start, end);
        return "redirect:/cart";
    }
}
