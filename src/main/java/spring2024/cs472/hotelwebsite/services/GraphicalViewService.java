package spring2024.cs472.hotelwebsite.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring2024.cs472.hotelwebsite.entities.GraphicalView;
import spring2024.cs472.hotelwebsite.entities.Room;

import java.util.List;

@Service
public class GraphicalViewService {

    private final GraphicalView graphicalView;

    @Autowired
    public GraphicalViewService(GraphicalView graphicalView) {
        this.graphicalView = graphicalView;
    }

    public String generateHotelFloorPlanHTML(List<Room> allRooms, List<Room> availableRooms) {
        // Call the generateHotelFloorPlanHTML method of GraphicalView with the provided arguments
        return graphicalView.generateHotelFloorPlanHTML(allRooms, availableRooms);
    }
}
