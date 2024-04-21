package spring2024.cs472.hotelwebsite.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import spring2024.cs472.hotelwebsite.entities.Room;
import spring2024.cs472.hotelwebsite.entities.RoomReservation;
import spring2024.cs472.hotelwebsite.repositories.RoomReservationRepository;
import spring2024.cs472.hotelwebsite.services.GraphicalViewService;
import spring2024.cs472.hotelwebsite.services.RoomService;

import java.util.List;

@Controller
public class GraphicalViewController {

    private final RoomService roomService;
    private final RoomReservationRepository roomReservationRepository;
    private final GraphicalViewService graphicalViewService;

    public GraphicalViewController(RoomService roomService, RoomReservationRepository roomReservationRepository,
                                   GraphicalViewService graphicalViewService) {
        this.roomService = roomService;
        this.roomReservationRepository = roomReservationRepository;
        this.graphicalViewService = graphicalViewService;
    }

    @GetMapping("/floorplan")
    public String getHotelFloorPlan(Model model) {
        // Fetch all rooms
        List<Room> rooms = roomService.getAllRooms();

        // Fetch all room reservations
        List<RoomReservation> reservations = roomReservationRepository.findAll();

        // Generate HTML for the hotel floor plan
        String floorPlanHTML = graphicalViewService.generateHotelFloorPlanHTML(rooms, reservations);

        // Add the generated HTML to the model
        model.addAttribute("floorPlanHTML", floorPlanHTML);

        // Return the Thymeleaf template
        return "floorplan";
    }
}








