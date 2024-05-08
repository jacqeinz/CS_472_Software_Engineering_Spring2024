/**
 * RoomServiceTest.java
 */
package spring2024.cs472.hotelwebsite;

// Imports necessary for the class
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.util.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.junit.jupiter.api.Test;
import spring2024.cs472.hotelwebsite.*;
import spring2024.cs472.hotelwebsite.entities.*;
import spring2024.cs472.hotelwebsite.repositories.RoomRepository;
import spring2024.cs472.hotelwebsite.repositories.RoomReservationRepository;
import spring2024.cs472.hotelwebsite.services.RoomService;

/**
 * This class contains unit tests for the RoomService class.
 * It ensures the correctness of methods in the RoomService class.
 *
 * @author Team ABCFG
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = HotelWebsiteApplication.class)
@AutoConfigureMockMvc
class RoomServiceTest {

    // Attributes
    @Autowired
    private RoomService roomService;
    @Autowired
    private RoomRepository roomRepo;
    @MockBean
    private RoomRepository roomRepository;
    @MockBean
    private RoomReservationRepository roomReservationRepository;

    /**
     * Tests the retrieval of all rooms successfully.
     */
    @Test
    public void testGetAllRoomsSuccess(){
        // Create a room
        Room room = new Room("420", "TestRoom", 500, 42);

        // Save the room
        roomService.saveRoom(room);

        // Create a list of rooms containing the created room
        List<Room> rooms = List.of(room);

        // Mock behavior of roomRepository to return the list of rooms
        when(roomRepository.findAll()).thenReturn(rooms);

        // Assertion
        assertThat(rooms).contains(room); // Verifies that the retrieved list contains the created room
    }


    /**
     * Tests the retrieval of all rooms fails.
     */
    @Test
    public void testGetAllRoomsFails(){
        // Create rooms
        Room room1 = new Room("420", "TestRoom", 500, 42);
        Room room2 = new Room("69", "TestRoom2", 500, 42);

        // Save one of the rooms
        roomService.saveRoom(room1);

        // Create a list of rooms containing the first room
        List<Room> rooms = List.of(room1);

        // Mock behavior of roomRepository to return the list of rooms
        when(roomRepository.findAll()).thenReturn(rooms);

        // Assertion
        assertThat(rooms).doesNotContain(room2); // Verifies that the retrieved list does not contain the second room
    }

    /**
     * Tests the retrieval of rooms by availability.
     */
    @Test
    public void testGetRoomsByAvailability(){
        // Define start and end dates
        var startDate = LocalDate.of(2025, 12, 1);
        var endDate = LocalDate.of(2025, 12, 31);

        // Fetch all rooms from the repository
        List<Room> rooms = roomRepo.findAll();

        // Get available rooms within the specified date range
        List<Room> available = roomService.getRoomsByAvailability(startDate, endDate);

        // Assertion
        assertThat(available).isEqualTo(rooms); // Verifies that available rooms match the retrieved rooms
    }

    /**
     * Tests the retrieval of rooms by availability fails.
     */
    @Test
    public void testGetRoomsByAvailabilityFails(){
        // Define start and end dates including the current date
        var startDate = LocalDate.now();
        var endDate = startDate.plusDays(1);

        // Fetch a room by room number
        Room room = roomService.getRoomByRoomNumber("103");

        // Fetch all rooms from the repository
        List<Room> rooms = roomRepo.findAll();

        // Get available rooms within the specified date range
        List<Room> available = roomService.getRoomsByAvailability(startDate, endDate);

        // Assertion
        assertThat(available).doesNotContain(room); // Verifies that the room is not available within the specified date range
    }

    /**
     * Tests the retrieval of all rooms.
     */
    @Test
    public void getAllRoomsShouldReturnAllRooms() {
        // Create rooms
        Room room1 = new Room("101", "Single", 100, 1);
        Room room2 = new Room("102", "Double", 200, 1);
        List<Room> rooms = List.of(room1, room2);

        // Mock behavior of roomRepository to return the list of rooms
        when(roomRepository.findAll()).thenReturn(rooms);

        // Call the method under test
        List<Room> result = roomService.getAllRooms();

        // Assertion
        assertNotNull(result); // Verifies that the result is not null
        assertEquals(2, result.size()); // Verifies that the correct number of rooms is retrieved
    }

    /**
     * Tests the retrieval of available rooms by availability.
     */
    @Test
    public void getRoomsByAvailabilityShouldReturnAvailableRooms() {
        // Create rooms
        Room room1 = new Room("101", "Single", 100, 1);
        Room room2 = new Room("102", "Double", 200, 1);

        // Create a mutable list of rooms
        List<Room> rooms = new ArrayList<>(List.of(room1, room2));

        // Mock behavior of roomRepository to return the list of rooms
        when(roomRepository.findAll()).thenReturn(rooms);

        // Create a guest
        Guest guest = new Guest("John Doe", "123 Main St", "01/01/2000", "john.doe@example.com", "123-456-7890", "johndoe", "password", "4111111111111111");

        // Create reservations for the rooms
        List<LocalDate> dates1 = Arrays.asList(LocalDate.now());
        RoomReservation roomReservation1 = new RoomReservation(guest, room1, dates1);
        when(roomReservationRepository.findByRoom(room1)).thenReturn(Arrays.asList(roomReservation1));

        List<LocalDate> dates2 = Arrays.asList(LocalDate.now().minusDays(1));
        RoomReservation roomReservation2 = new RoomReservation(guest, room2, dates2);
        when(roomReservationRepository.findByRoom(room2)).thenReturn(Arrays.asList(roomReservation2));

        // Define start and end dates
        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now().plusDays(1);

        // Call the method under test
        List<Room> result = roomService.getRoomsByAvailability(start, end);

        // Assertion
        assertNotNull(result); // Verifies that the result is not null
        assertEquals(1, result.size()); // Verifies that only the available room is retrieved
        assertEquals("102", result.get(0).getRoomNumber()); // Verifies the correctness of the retrieved room
    }

    /**
     * Tests the retrieval of a room by its ID.
     */
    @Test
    public void getRoomByIdShouldReturnRoom() {
        // Create a room
        Room room = new Room("101", "Single", 100, 1);

        // Mock behavior of roomRepository to return the room
        when(roomRepository.findById(1L)).thenReturn(room);

        // Call the method under test
        Room result = roomService.getRoomById(1);

        // Assertion
        assertNotNull(result); // Verifies that the result is not null
        assertEquals("101", result.getRoomNumber()); // Verifies the correctness of the retrieved room
    }

    /**
     * Tests the retrieval of a room by its room number.
     */
    @Test
    public void getRoomByRoomNumberShouldReturnRoom() {
        // Create a room
        Room room = new Room("101", "Single", 100, 1);

        // Mock behavior of roomRepository to return the room
        when(roomRepository.findByRoomNumber("101")).thenReturn(room);

        // Call the method under test
        Room result = roomService.getRoomByRoomNumber("101");

        // Assertion
        assertNotNull(result); // Verifies that the result is not null
        assertEquals("101", result.getRoomNumber()); // Verifies the correctness of the retrieved room
    }

    /**
     * Tests the saving of a room.
     */
    @Test
    public void saveRoomShouldReturnSavedRoom() {
        // Create a room
        Room room = new Room("101", "Single", 100, 1);

        // Mock behavior of roomRepository to return the saved room
        when(roomRepository.save(room)).thenReturn(room);

        // Call the method under test
        Room result = roomService.saveRoom(room);

        // Assertion
        assertNotNull(result); // Verifies that the result is not null
        assertEquals("101", result.getRoomNumber()); // Verifies the correctness of the saved room
    }

    /**
     * Tests the deletion of a room.
     */
    @Test
    public void deleteRoomShouldDeleteRoom() {
        // Create a room
        Room room = new Room("101", "Single", 100, 1);

        // Call the method under test to delete the room
        roomService.deleteRoom(room);

        // Mock behavior of roomRepository to return null when searching for the deleted room
        when(roomRepository.findByRoomNumber("101")).thenReturn(null);

        // Call the method under test to retrieve the deleted room
        Room result = roomService.getRoomByRoomNumber("101");

        // Assertion
        assertNull(result); // Verifies that the deleted room cannot be found
    }
}

