/**
 * RoomService.java
 */
package spring2024.cs472.hotelwebsite.services;

// Imports necessary for the class
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring2024.cs472.hotelwebsite.entities.Room;
import spring2024.cs472.hotelwebsite.entities.RoomReservation;
import spring2024.cs472.hotelwebsite.repositories.RoomRepository;
import spring2024.cs472.hotelwebsite.repositories.RoomReservationRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Service class for managing rooms.
 * This class is responsible for managing rooms, including retrieving all rooms, retrieving rooms by availability for a specific date range,
 * retrieving a room by its ID or room number, saving rooms, and deleting rooms.
 *
 * @author Team ABCFG
 */
@Service
public class RoomService {

    // Attributes
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RoomReservationRepository roomReservationRepository;

    /**
     * Retrieves all rooms.
     *
     * @return A list of all rooms.
     */
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    /**
     * Retrieves rooms by availability for a specific date range.
     *
     * @param start The start date of the date range.
     * @param end   The end date of the date range.
     * @return A list of rooms available for the specified date range.
     */
    public List<Room> getRoomsByAvailability(LocalDate start, LocalDate end) {
        List<Room> rooms = roomRepository.findAll();
        List<Room> roomsToRemove = new ArrayList<>();
        for (Room room : rooms) {
            boolean unavailable = false;
            for (RoomReservation roomReservation : roomReservationRepository.findByRoom(room)) {
                if (unavailable) {
                    break;
                }
                for (LocalDate date : roomReservation.getDates()) {
                    if (date.atTime(12, 0).isAfter(start.atStartOfDay()) && date.atTime(12, 0).isBefore(end.atTime(23, 59))) {
                        unavailable = true;
                        break;
                    }
                }
            }
            if (unavailable) {
                roomsToRemove.add(room);
            }
        }
        rooms.removeAll(roomsToRemove);
        return rooms;
    }

    /**
     * Retrieves a room by its ID.
     *
     * @param id The ID of the room to retrieve.
     * @return The room with the specified ID, or null if not found.
     */
    public Room getRoomById(int id) {
        return roomRepository.findById(id);
    }

    /**
     * Retrieves a room by its room number.
     *
     * @param roomNumber The room number of the room to retrieve.
     * @return The room with the specified room number, or null if not found.
     */
    public Room getRoomByRoomNumber(String roomNumber) {
        return roomRepository.findByRoomNumber(roomNumber);
    }

    /**
     * Saves a room.
     *
     * @param room The room to save.
     * @return The saved room.
     */
    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }

    /**
     * Deletes a room.
     *
     * @param room The room to delete.
     */
    public void deleteRoom(Room room) {
        roomRepository.delete(room);
    }
}
