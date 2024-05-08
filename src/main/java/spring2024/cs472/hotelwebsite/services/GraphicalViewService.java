/**
 * GraphicalViewService.java
 */
package spring2024.cs472.hotelwebsite.services;

// Imports necessary for the class
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring2024.cs472.hotelwebsite.entities.GraphicalView;
import spring2024.cs472.hotelwebsite.entities.Room;
import java.util.List;

/**
 * Service class for managing graphical views.
 * This class provides a method to generate HTML for displaying the hotel floor plan in the graphical view.
 *
 * @author Team ABCFG
 */
@Service
public class GraphicalViewService {

    // Attribute
    private final GraphicalView graphicalView;

    /**
     * Constructor for GraphicalViewService.
     *
     * @param graphicalView The graphical view instance to be used.
     */
    @Autowired
    public GraphicalViewService(GraphicalView graphicalView) {
        this.graphicalView = graphicalView;
    }

    /**
     * Generates HTML for displaying the hotel floor plan.
     *
     * @param allRooms The list of all rooms in the hotel.
     * @param availableRooms The list of available rooms.
     * @return The HTML string for displaying the hotel floor plan.
     */
    public String generateHotelFloorPlanHTML(List<Room> allRooms, List<Room> availableRooms) {
        // Call the generateHotelFloorPlanHTML method of GraphicalView with the provided arguments
        return graphicalView.generateHotelFloorPlanHTML(allRooms, availableRooms);
    }
}
