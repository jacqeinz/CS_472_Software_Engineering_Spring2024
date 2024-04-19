package spring2024.cs472.hotelwebsite.services;

import org.springframework.stereotype.Service;
import spring2024.cs472.hotelwebsite.entities.GraphicalView;
import spring2024.cs472.hotelwebsite.entities.Room;
import spring2024.cs472.hotelwebsite.entities.RoomReservation;

import java.util.List;

@Service
public class GraphicalViewService {

    private final GraphicalView graphicalView;

    public GraphicalViewService(GraphicalView graphicalView) {
        this.graphicalView = graphicalView;
    }

    public String generateHotelFloorPlanHTML(List<Room> rooms, List<RoomReservation> reservations) {
        // Call the generateHotelFloorPlanHTML method of GraphicalView with the provided arguments
        return graphicalView.generateHotelFloorPlanHTML(rooms, reservations);
    }
}






