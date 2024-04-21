package spring2024.cs472.hotelwebsite.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import spring2024.cs472.hotelwebsite.entities.Room;
import spring2024.cs472.hotelwebsite.repositories.RoomRepository;
import spring2024.cs472.hotelwebsite.services.RoomService;

@Controller
public class RoomController {

    private final RoomService roomService;
    private RoomRepository roomRepository;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/signup")
    public String showSignUpForm(Room room, Model model){
        model.addAttribute("Room", new Room());
        return "addRoom";
    }

    @PostMapping("/addRoom")
    public String addRoom(@ModelAttribute("Room") Room room, BindingResult result, Model model){
        if (result.hasErrors()) {
            return "addRoom";
        }
        roomService.saveRoom(room);
        return "redirect:/RoomIndex";
    }

    @GetMapping("/RoomIndex")
    public String showRoomList(Model model){
        model.addAttribute("Rooms", roomService.getAllRooms());
        return "RoomIndex";
    }

    @GetMapping("/edit/{id}")
    public String
    showUpdateForm(@PathVariable int id, Model model){
        Room room = roomService.getRoomById(id);
        model.addAttribute("Room", room);
        return "updateRoom";
    }

    @PostMapping("/update/{id}")
    public String updateRoom(@PathVariable int id, Room room, BindingResult result, Model model){
        roomService.saveRoom(room);
        return "redirect:/RoomIndex";
    }

    @GetMapping("/delete/{id}")
    public String
    deleteRoom(@PathVariable int id, Model model){
        Room room=roomService.getRoomById(id);
        roomService.deleteRoom(room);
        return "redirect:/RoomIndex";
    }

}
