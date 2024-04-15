package org.eclipse.jakarta.model.entity;

import java.util.List;

public class GraphicalView {

    public String generateHotelFloorPlanHTML(List<Room> rooms, List<RoomReservation> reservations) {
        if (rooms == null) {
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
        for (Room room : rooms) {
            html.append("<tr><td>Room ").append(room.getRoomNumber()).append("</td><td>");

            // Check if the room is booked
            boolean isBooked = isRoomBooked(room, reservations);

            // If the room is booked, mark it as red, otherwise mark it as green
            String color = isBooked ? "red" : "green";
            html.append("<div style='width: 50px; height: 50px; background-color: ").append(color).append("'></div>");

            html.append("</td></tr>");
        }

        html.append("</table>");
        html.append("</body>");
        html.append("</html>");

        return html.toString();
    }

    private boolean isRoomBooked(Room room, List<RoomReservation> reservations) {
        if (reservations == null) {
            return false;
        }

        for (RoomReservation reservation : reservations) {
            if (reservation.getRoom().getRoomNumber().equals(room.getRoomNumber())) {
                return true;
            }
        }
        return false;
    }
}
