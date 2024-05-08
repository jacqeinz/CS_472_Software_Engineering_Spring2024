/**
 * RoomController.java
 */
package spring2024.cs472.hotelwebsite.controllers;

// Imports necessary for the class
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import spring2024.cs472.hotelwebsite.entities.Room;
import spring2024.cs472.hotelwebsite.repositories.RoomRepository;
import spring2024.cs472.hotelwebsite.services.RoomService;

/**
 * Manages room-related operations such as adding, updating, and deleting rooms.
 * Handles HTTP requests related to room management.
 * Interacts with the RoomService and RoomRepository to perform CRUD operations on rooms.
 * Renders views for room management operations.
 * Acts as a controller in the MVC architecture.
 *
 * @author Team ABCFG
 */
@Controller
public class RoomController {

    // Attributes
    private final RoomService roomService;
    private RoomRepository roomRepository;

    /**
     * Constructs a RoomController with a RoomService dependency.
     *
     * @param roomService The RoomService to be injected.
     */
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

//    /**
//     * Displays the form for adding a new room.
//     * Adds a room object to the model and renders the add room form view.
//     * This method is currently commented out.
//     *
//     * @param room  The room object to be added.
//     * @param model The model to which room attributes will be added.
//     * @return The view name for rendering the add room form.
//     */
//    @GetMapping("/signup")
//    public String addRoom(Room room, Model model){
//        model.addAttribute("Room", room);
//        return "addRoom1";
//    }

    /**
     * Adds a new room to the system.
     * If there are validation errors, redirects to the add room form.
     * Saves the new room to the database and redirects to the room index page.
     *
     * @param room   The room object to be added.
     * @param result The BindingResult to check for validation errors.
     * @param model  The model to which attributes will be added.
     * @return The redirect URL to the room index page.
     */
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

    /**
     * Adds a default room object as a model attribute.
     *
     * @return A new Room object.
     */
    @ModelAttribute(value="Room")
    public Room getRoom(){
        return new Room();
    }

    /**
     * Displays a list of all rooms in the system.
     * Adds the list of rooms to the model and renders the room index view.
     *
     * @param model The model to which room attributes will be added.
     * @return The view name for rendering the room index.
     */
    @GetMapping("/RoomIndex")
    public String showRoomList(Model model, HttpSession session){
        if(session.getAttribute("admin") == null) {
            return "redirect:/login";
        }
        model.addAttribute("Rooms", roomService.getAllRooms());
        return "roomIndex";
    }

//    /**
//     * Retrieves a single room by its ID.
//     * Adds the room object to the model and returns the room.
//     * This method is currently commented out.
//     *
//     * @param id    The ID of the room to retrieve.
//     * @param model The model to which the room will be added.
//     * @return The room object retrieved by its ID.
//     */
//    @GetMapping("/getOne/{id}")
//    public Room getOneRoom(@PathVariable int id, Model model){
//        Room room = roomService.getRoomById(id);
//        model.addAttribute("Room", room);
//        return roomService.getRoomById(id);
//    }

    /**
     * Displays the update form for a specific room.
     * Retrieves the room details based on the provided ID and adds them to the model.
     * Renders the update room view with pre-filled form fields.
     *
     * @param id    The ID of the room to update.
     * @param model The model to which room details will be added.
     * @return The view name for rendering the update room form.
     */
    @GetMapping("/room/edit/{id}")
    public String
    showUpdateForm(@PathVariable int id, Model model, HttpSession session){
        if(session.getAttribute("admin") == null) {
            return "redirect:/login";
        }
        Room room = roomService.getRoomById(id);
        model.addAttribute("Room", room);
        return "updateRoom";
    }

    /**
     * Updates the details of a specific room.
     * If there are validation errors, redirects to the update room form.
     * Saves the updated room details to the database and redirects to the room index page.
     *
     * @param id     The ID of the room to update.
     * @param room   The updated room object.
     * @param result The BindingResult to check for validation errors.
     * @param model  The model to which attributes will be added.
     * @return The redirect URL to the room index page.
     */
    @PostMapping("/room/update/{id}")
    public String updateRoom(@PathVariable int id, Room room, BindingResult result, Model model, HttpSession session){
        if(session.getAttribute("admin") == null) {
            return "redirect:/login";
        }
        if(result.hasErrors()) {
            return "updateRoom";
        }
        roomService.saveRoom(room);
        return "redirect:/RoomIndex";
    }

    /**
     * Deletes a specific room from the system.
     * Retrieves the room details based on the provided ID and deletes it from the database.
     * Redirects to the room index page after deleting the room.
     *
     * @param id    The ID of the room to delete.
     * @param model The model to which room details will be added.
     * @return The redirect URL to the room index page.
     */
    @GetMapping("/room/delete/{id}")
    public String
    deleteRoom(@PathVariable int id, Model model, HttpSession session){
        if(session.getAttribute("admin") == null) {
            return "redirect:/login";
        }
        Room room=roomService.getRoomById(id);
        roomService.deleteRoom(room);
        return "redirect:/RoomIndex";
    }
}
