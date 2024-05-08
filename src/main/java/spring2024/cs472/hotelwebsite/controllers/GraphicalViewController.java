package spring2024.cs472.hotelwebsite.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;
import spring2024.cs472.hotelwebsite.entities.Room;
import spring2024.cs472.hotelwebsite.services.GraphicalViewService;
import spring2024.cs472.hotelwebsite.services.RoomService;

import java.time.LocalDate;
import java.util.List;

@Controller
public class GraphicalViewController {

    private final RoomService roomService;
    private final GraphicalViewService graphicalViewService;

    @Autowired
    public GraphicalViewController(RoomService roomService, GraphicalViewService graphicalViewService) {
        this.roomService = roomService;
        this.graphicalViewService = graphicalViewService;
    }

    @GetMapping("/floorplan")
    public String getHotelFloorPlan(Model model, @RequestParam(name = "start", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start, @RequestParam(name = "end", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end, HttpSession session) {
        if(session.getAttribute("admin") == null || session.getAttribute("guest") == null) {}
        if (start != null && end != null) {
            // Fetch all rooms
            List<Room> allRooms = roomService.getAllRooms();
            System.out.println("Number of rooms fetched: " + allRooms.size());

            // Fetch available rooms
            List<Room> availableRooms = roomService.getRoomsByAvailability(start, end);
            System.out.println("Number of available rooms: " + availableRooms.size());

            // Generate HTML for the hotel floor plan
            String floorPlanHTML = graphicalViewService.generateHotelFloorPlanHTML(allRooms, availableRooms);

            // Add the generated HTML to the model
            model.addAttribute("floorPlanHTML", floorPlanHTML);
        }

        // Return the Thymeleaf template
        return "floorplan";
    }
}








