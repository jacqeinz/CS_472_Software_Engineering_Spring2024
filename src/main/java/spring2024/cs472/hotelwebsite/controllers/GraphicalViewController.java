/**
 * GraphicalViewController.java
 */
package spring2024.cs472.hotelwebsite.controllers;

// Imports necessary for the class
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

/**
 * This class handles requests related to displaying the hotel floor plan.
 * It provides a method to fetch the floor plan based on the specified date range.
 * The floor plan includes information about available rooms.
 *
 * @author Team ABCFG
 */
@Controller
public class GraphicalViewController {

    // Attributes
    private final RoomService roomService; // Service for managing rooms
    private final GraphicalViewService graphicalViewService; // Service for graphical view operations

    /**
     * Constructor for the GraphicalViewController class.
     *
     * @param roomService The RoomService instance for managing rooms.
     * @param graphicalViewService The GraphicalViewService instance for graphical view operations.
     */
    @Autowired
    public GraphicalViewController(RoomService roomService, GraphicalViewService graphicalViewService) {
        this.roomService = roomService;
        this.graphicalViewService = graphicalViewService;
    }

    /**
     * Handles requests to fetch and display the hotel floor plan.
     *
     * @param model The Model object to add attributes for the view.
     * @param start The start date of the date range (optional).
     * @param end The end date of the date range (optional).
     * @return The view name for displaying the hotel floor plan.
     */
    @GetMapping("/floorplan")
    public String getHotelFloorPlan(Model model, @RequestParam(name = "start", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start, @RequestParam(name = "end", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end, HttpSession session) {
        if(session.getAttribute("admin") == null || session.getAttribute("guest") == null) {}
        if (start != null && end != null) {
            // Fetch all rooms
            List<Room> allRooms = roomService.getAllRooms();
            System.out.println("Number of rooms fetched: " + allRooms.size());

            // Fetch available rooms for the specified date range
            List<Room> availableRooms = roomService.getRoomsByAvailability(start, end);
            System.out.println("Number of available rooms: " + availableRooms.size());

            // Generate HTML for the hotel floor plan
            String floorPlanHTML = graphicalViewService.generateHotelFloorPlanHTML(allRooms, availableRooms);

            // Add the generated HTML to the model
            model.addAttribute("floorPlanHTML", floorPlanHTML);
        }

        // Return the Thymeleaf template for the hotel floor plan
        return "floorplan";
    }

    @GetMapping("/floorplanGuest")
    public String getHotelFloorPlanGuest(Model model, @RequestParam(name = "start", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start, @RequestParam(name = "end", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end, HttpSession session) {
        if(session.getAttribute("admin") == null || session.getAttribute("guest") == null) {}
        if (start != null && end != null) {
            // Fetch all rooms
            List<Room> allRooms = roomService.getAllRooms();
            System.out.println("Number of rooms fetched: " + allRooms.size());

            // Fetch available rooms for the specified date range
            List<Room> availableRooms = roomService.getRoomsByAvailability(start, end);
            System.out.println("Number of available rooms: " + availableRooms.size());

            // Generate HTML for the hotel floor plan
            String floorPlanHTML = graphicalViewService.generateHotelFloorPlanHTML(allRooms, availableRooms);

            // Add the generated HTML to the model
            model.addAttribute("floorPlanHTML", floorPlanHTML);
        }

        // Return the Thymeleaf template for the hotel floor plan
        return "floorplanGuest";
    }
}








