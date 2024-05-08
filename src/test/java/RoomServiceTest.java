/**
 * RoomServiceTest.java
 */

// Imports necessary for the class
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
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
import spring2024.cs472.hotelwebsite.entities.Room;
import spring2024.cs472.hotelwebsite.repositories.RoomRepository;
import spring2024.cs472.hotelwebsite.services.RoomService;

/**
 * This class tests the functionality of the RoomService class.
 * It covers scenarios related to room operations such as retrieving all rooms,
 * checking room availability, and handling success/failure cases.
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

    /**
     * Test to verify that all rooms are retrieved successfully.
     */
    @Test
    public void testGetAllRoomsSuccess(){
        // Given
        Room room = new Room("420", "TestRoom", 500, 42);

        // When
        roomService.saveRoom(room);
        List<Room> rooms = List.of(room);
        when(roomRepository.findAll()).thenReturn(rooms);

        // Then
        assertThat(rooms).contains(room);
    }

    /**
     * Test to verify that retrieving all rooms fails when some rooms are missing.
     */
    @Test
    public void testGetAllRoomsFails(){
        // Given
        Room room1 = new Room("420", "TestRoom", 500, 42);
        Room room2 = new Room("69", "TestRoom2", 500, 42);

        // When
        roomService.saveRoom(room1);
        List<Room> rooms = List.of(room1);
        when(roomRepository.findAll()).thenReturn(rooms);

        // Then
        assertThat(rooms).doesNotContain(room2);
    }

    /**
     * Test to verify room availability within a specified date range.
     */
    @Test
    public void testGetRoomsByAvailability(){
        // Given
        var startDate = LocalDate.of(2025, 12, 1);
        var endDate = LocalDate.of(2025, 12, 31);
        List<Room> rooms = roomRepo.findAll();

        // When
        List<Room> available = roomService.getRoomsByAvailability(startDate, endDate);

        // Then
        assertThat(Arrays.asList(available.toArray())).isEqualTo(rooms);
    }

    /**
     * Test to verify room unavailability within a specified date range.
     */
    @Test
    public void testGetRoomsByAvailabilityFails(){
        // Given
        var startDate = LocalDate.now();
        var endDate = startDate.plusDays(1);
        Room room = roomService.getRoomByRoomNumber("103");
        List<Room> rooms = roomRepo.findAll();

        // When
        List<Room> available = roomService.getRoomsByAvailability(startDate, endDate);

        // Then
        assertThat(Arrays.asList(available.toArray())).doesNotContain(room);
    }
}
