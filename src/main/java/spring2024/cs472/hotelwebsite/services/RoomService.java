package spring2024.cs472.hotelwebsite.services;

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

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomReservationRepository roomReservationRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public List<Room> getRoomsByAvailability(LocalDate  start, LocalDate  end) {
        List<Room> rooms = roomRepository.findAll();
        List<Room> roomsToRemove = new ArrayList<>();
        for (Room room : rooms) {
            boolean unavailable = false;
            for (RoomReservation roomReservation : roomReservationRepository.findByRoom(room)) {
                if(unavailable) {
                    break;
                }
                for (LocalDate date : roomReservation.getDates()) {
                    if (date.atTime(12,0).isAfter(start.atStartOfDay()) && date.atTime(12,0).isBefore(end.atTime(23, 59))) {
                        unavailable = true;
                        break;
                    }
                }
            }
            if(unavailable) {
                roomsToRemove.add(room);
            }
        }
        rooms.removeAll(roomsToRemove);
        return rooms;
    }

    public Room getRoomById(int id) {
        return roomRepository.findById(id);
    }

    public Room getRoomByRoomNumber(String roomNumber) {
        return roomRepository.findByRoomNumber(roomNumber);
    }

    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }

}
