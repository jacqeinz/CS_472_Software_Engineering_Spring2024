/**
 * InitService.java
 */
package spring2024.cs472.hotelwebsite.services;

// Imports necessary for the class
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring2024.cs472.hotelwebsite.entities.*;
import spring2024.cs472.hotelwebsite.repositories.ReservationDetailsRepository;
import spring2024.cs472.hotelwebsite.repositories.RoomReservationRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Service class for initializing the system with sample data.
 * This class is responsible for initializing the system with sample data for testing and demonstration purposes.
 *
 * @author Team ABCFG
 */
@Service
public class InitService {

    // Attributes
    @Autowired
    private AccountService accountService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private RoomReservationRepository roomReservationRepository;
    @Autowired
    private ReservationDetailsRepository reservationDetailsRepository;
    private boolean isInitialized = false;

    /**
     * Initializes the system with sample data.
     * This method creates sample admin and guest accounts, as well as sample rooms and reservations.
     */
    public void init() {
        if(!isInitialized) {
            Admin admin = new Admin(true,123456);
            admin.setUserName("TestAdmin");
            admin.setUserPassword("password");
            admin.setName("John Doe");
            Guest guest = new Guest("Test Guest", "101 West FakeStreet, Fakeville, FK 11111",
                    "2/2/2002", "hotelabcfg@gmail.com", "555-555-5555", "TestGuest",
                    "password", "1111-2222-3333-4444");
            accountService.save(guest);
            accountService.save(admin);
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
            List<LocalDate> dates = new ArrayList<>();
            dates.add(today);
            dates.add(today.plusDays(1));
            dates.add(today.plusDays(2));
            dates.add(today.plusDays(3));

            RoomReservation roomReservation = new RoomReservation(guest, roomService.getRoomByRoomNumber("103"), dates);
            roomReservationRepository.save(roomReservation);

            List<RoomReservation> res = new ArrayList<>();
            res.add(roomReservation);
            ReservationDetails details = new ReservationDetails(guest, res, guest.getPaymentInfo(), res.get(0).getTotal());
            reservationDetailsRepository.save(details);

            guest.addCurrentReservation(details);
            accountService.save(guest);

            isInitialized = true;
        }
    }
}
