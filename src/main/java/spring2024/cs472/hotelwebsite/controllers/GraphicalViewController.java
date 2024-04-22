package spring2024.cs472.hotelwebsite.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import spring2024.cs472.hotelwebsite.entities.Room;
import spring2024.cs472.hotelwebsite.entities.RoomReservation;
import spring2024.cs472.hotelwebsite.services.GraphicalViewService;
import spring2024.cs472.hotelwebsite.services.RoomService;

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
    public String getHotelFloorPlan(Model model) {
        // Fetch all rooms
        List<Room> rooms = roomService.getAllRooms();
        System.out.println("Number of rooms fetched: " + rooms.size());

        // Assuming you also need room reservations for the floor plan, you can fetch them here if necessary
        // List<RoomReservation> reservations = roomReservationRepository.findAll();

        // For demonstration purposes, let's pass an empty list of reservations
        List<RoomReservation> reservations = List.of();
        System.out.println("Number of reservations fetched: " + reservations.size());

        // Generate HTML for the hotel floor plan
        String floorPlanHTML = graphicalViewService.generateHotelFloorPlanHTML(rooms, reservations);

        // Add the generated HTML to the model
        model.addAttribute("floorPlanHTML", floorPlanHTML);

        // Return the Thymeleaf template
        return "floorplan";
    }
}








