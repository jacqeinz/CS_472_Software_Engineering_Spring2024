package spring2024.cs472.hotelwebsite.services;

import org.springframework.stereotype.Service;
import spring2024.cs472.hotelwebsite.entities.GraphicalView;
import spring2024.cs472.hotelwebsite.entities.Room;
import spring2024.cs472.hotelwebsite.entities.RoomReservation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class GraphicalViewService {

    private final GraphicalView graphicalView;

    public GraphicalViewService(GraphicalView graphicalView) {
        this.graphicalView = graphicalView;
    }

    public String generateHotelFloorPlanHTML(List<Room> rooms, List<RoomReservation> reservations) {
        // If rooms are null, generate dummy rooms for testing
        if (rooms == null || rooms.isEmpty()) {
            rooms = generateDummyRooms();
        }

        // Call the generateHotelFloorPlanHTML method of GraphicalView with the provided arguments
        return graphicalView.generateHotelFloorPlanHTML(rooms, reservations);
    }

    @org.jetbrains.annotations.NotNull
    private List<Room> generateDummyRooms() {
        List<Room> dummyRooms = new ArrayList<>();
        Random random = new Random();
        // Generate some dummy rooms (you can customize this as needed)
        for (int i = 1; i <= 10; i++) {
            Room room = new Room();
            // Set the ID as a Long value
            room.setRoomNumber("Room " + i);
            // Randomly assign some rooms as occupied and others as vacant
            boolean isOccupied = random.nextBoolean();
            room.setOccupied(isOccupied);
            // You can set other room properties as needed
            dummyRooms.add(room);
        }
        return dummyRooms;
    }
}
