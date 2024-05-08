/**
 * GraphicalView.java
 */
package spring2024.cs472.hotelwebsite.entities;

// Imports necessary for the class
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * This class represents a GraphicalView component responsible for generating HTML for the hotel floor plan.
 * It generates an HTML table displaying each room with a color indicating its availability.
 *
 * @author Team ABCFG
 */
@Component
public class GraphicalView {

    /**
     * Generates HTML for the hotel floor plan based on the provided lists of all rooms and available rooms.
     *
     * @param allRooms       The list of all rooms in the hotel.
     * @param availableRooms The list of available rooms.
     * @return The generated HTML representing the hotel floor plan.
     */
    public String generateHotelFloorPlanHTML(List<Room> allRooms, List<Room> availableRooms) {
        if (allRooms == null) {
            // Handle the case where rooms list is null
            return "No rooms available";
        }

        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE html>");
        html.append("<html>");
        html.append("<head>");
        html.append("<title>Hotel Floor Plan</title>");
        html.append("</head>");
        html.append("<body>");
        html.append("<h2>Hotel Floor Plan</h2>");
        html.append("<table border='1'>");

        // Loop through each room
        for (Room room : allRooms) {
            html.append("<tr><td>Room ").append(room.getRoomNumber()).append("</td><td>");

            // Check if the room is reserved
            boolean isReserved = !availableRooms.contains(room);

            // If the room is reserved, mark it as red, otherwise mark it as green
            String color = isReserved ? "red" : "green";
            html.append("<div style='width: 50px; height: 50px; background-color: ").append(color).append("'></div>");

            html.append("</td></tr>");
        }

        html.append("</table>");
        html.append("</body>");
        html.append("</html>");

        return html.toString();
    }
}
