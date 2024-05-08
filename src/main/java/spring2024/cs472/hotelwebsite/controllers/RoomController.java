package spring2024.cs472.hotelwebsite.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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

//    @GetMapping("/signup")
//    public String addRoom(Room room, Model model){
//        model.addAttribute("Room", room);
//        return "addRoom1";
//    }

    @RequestMapping(value="/addRoom", method=RequestMethod.POST)
    public String addRoom(@ModelAttribute("Room") Room room, BindingResult result, Model model, HttpSession session) {
        if(session.getAttribute("admin") == null){
            return "redirect:/login";
        }
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
    public String showRoomList(Model model, HttpSession session){
        if(session.getAttribute("admin") == null){
            return "redirect:/login";
        }
        model.addAttribute("Rooms", roomService.getAllRooms());
        return "roomIndex";
    }

//    @GetMapping("/getOne/{id}")
//    public Room getOneRoom(@PathVariable int id, Model model){
//        Room room = roomService.getRoomById(id);
//        model.addAttribute("Room", room);
//        return roomService.getRoomById(id);
//    }

    @GetMapping("/room/edit/{id}")
    public String
    showUpdateForm(@PathVariable int id, Model model, HttpSession session){
        if(session.getAttribute("admin") == null){
            return "redirect:/login";
        }
        Room room = roomService.getRoomById(id);
        model.addAttribute("Room", room);
        return "updateRoom";
    }

    @PostMapping("/room/update/{id}")
    public String updateRoom(@PathVariable int id, Room room, BindingResult result, Model model, HttpSession session){
        if(session.getAttribute("admin") == null){
            return "redirect:/login";
        }
        if(result.hasErrors()) {
            return "updateRoom";
        }
        roomService.saveRoom(room);
        return "redirect:/RoomIndex";
    }

    @GetMapping("/room/delete/{id}")
    public String
    deleteRoom(@PathVariable int id, Model model, HttpSession session){
        if(session.getAttribute("admin") == null){
            return "redirect:/login";
        }
        Room room=roomService.getRoomById(id);
        roomService.deleteRoom(room);
        return "redirect:/RoomIndex";
    }

}
