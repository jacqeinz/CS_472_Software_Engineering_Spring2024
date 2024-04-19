package spring2024.cs472.hotelwebsite.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring2024.cs472.hotelwebsite.entities.*;
import spring2024.cs472.hotelwebsite.repositories.RoomReservationRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class InitService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomReservationRepository roomReservationRepository;

    private boolean isInitialized = false;

    public void init() {
        if(!isInitialized) {
            Guest guest = new Guest("Test Guest", "101 West FakeStreet, Fakeville, FK 11111",
                    "2/2/2002", "test@test.test", "555-555-5555", "TestGuest",
                    "password", "1111-2222-3333-4444");
            accountService.save(guest);
            String[] roomNumbers = {"101", "102", "103", "104", "105", "106", "107", "108", "109",
            "110", "111", "112", "113", "201", "202", "203", "204", "205", "206", "207",
            "208", "301", "302", "303"};
            for(String roomNumber : roomNumbers) {
                int price = 100;
                int floorNumber = 1;
                String roomType = "Suite";
                if(roomNumber.charAt(0) == '2') {
                    floorNumber = 2;
                    price = 75;
                    roomType = "Standard";
                }else if(roomNumber.charAt(0) == '3') {
                    floorNumber = 3;
                    price = 250;
                    roomType = "Honeymoon";
                }

                roomService.saveRoom(new Room(roomNumber, roomType, price, floorNumber));
            }
            LocalDate today = LocalDate.now();
            LocalDate[] dates = {today, today.plusDays(1), today.plusDays(2), today.plusDays(3)};
            roomReservationRepository.save(new RoomReservation(guest, roomService.getRoomByRoomNumber("103"), List.of(dates)));

            isInitialized = true;
        }
    }
}
