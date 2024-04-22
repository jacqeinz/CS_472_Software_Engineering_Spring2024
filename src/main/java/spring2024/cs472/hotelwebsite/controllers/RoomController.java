package spring2024.cs472.hotelwebsite.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import spring2024.cs472.hotelwebsite.entities.Room;
import spring2024.cs472.hotelwebsite.repositories.RoomRepository;
import spring2024.cs472.hotelwebsite.services.RoomService;

import java.util.Optional;

@Controller
public class RoomController {

    private final RoomService roomService;
    private RoomRepository roomRepository;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

//    @GetMapping("/signup")
//    public String addRoom(Room room, Model model){
//        model.addAttribute("Room", room);
//        return "addRoom1";
//    }

    @RequestMapping(value="/addRoom", method=RequestMethod.POST)
    public String addRoom(@ModelAttribute("Room") Room room, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "addRoom";
        }
        roomService.saveRoom(room);
        return "redirect:/RoomIndex";
    }

    @ModelAttribute(value="Room")
    public Room getRoom(){
        return new Room();
    }

    @GetMapping("/RoomIndex")
    public String showRoomList(Model model){
        model.addAttribute("Rooms", roomService.getAllRooms());
        return "RoomIndex";
    }

//    @GetMapping("/getOne/{id}")
//    public Room getOneRoom(@PathVariable int id, Model model){
//        Room room = roomService.getRoomById(id);
//        model.addAttribute("Room", room);
//        return roomService.getRoomById(id);
//    }

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
