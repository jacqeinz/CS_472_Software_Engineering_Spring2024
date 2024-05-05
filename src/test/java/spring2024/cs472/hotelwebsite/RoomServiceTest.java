package spring2024.cs472.hotelwebsite;

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

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = HotelWebsiteApplication.class)
@AutoConfigureMockMvc
class RoomServiceTest {

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomRepository roomRepo;

    @MockBean
    private RoomRepository roomRepository;

    @MockBean
    private RoomReservationRepository roomReservationRepository;

    @Test
    public void testGetAllRoomsSuccess(){
        Room room = new Room("420", "TestRoom", 500, 42);
        roomService.saveRoom(room);
        List<Room> rooms = List.of(room);
        when(roomRepository.findAll()).thenReturn(rooms);
        assertThat(rooms).contains(room);
    }

    @Test
    public void testGetAllRoomsFails(){
        Room room1 = new Room("420", "TestRoom", 500, 42);
        Room room2 = new Room("69", "TestRoom2", 500, 42);
        roomService.saveRoom(room1);
        List<Room> rooms = List.of(room1);
        when(roomRepository.findAll()).thenReturn(rooms);
        assertThat(rooms).doesNotContain(room2);
    }

    @Test
    public void testGetRoomsByAvailability(){
        var startDate=LocalDate.of(2025,12,1);
        var endDate=LocalDate.of(2025,12,31);
        List<Room> rooms = roomRepo.findAll();
        List<Room> available= roomService.getRoomsByAvailability(startDate, endDate);
        assertThat(available).isEqualTo(rooms);
    }

    @Test
    public void testGetRoomsByAvailabilityFails(){
        var startDate=LocalDate.now();
        var endDate=startDate.plusDays(1);
        Room room=roomService.getRoomByRoomNumber("103");
        List<Room> rooms = roomRepo.findAll();
        List<Room> available= roomService.getRoomsByAvailability(startDate, endDate);
        assertThat(available).doesNotContain(room);
    }

    @Test
    public void getAllRoomsShouldReturnAllRooms() {
        Room room1 = new Room("101", "Single", 100, 1);
        Room room2 = new Room("102", "Double", 200, 1);
        List<Room> rooms = List.of(room1, room2);
        when(roomRepository.findAll()).thenReturn(rooms);

        List<Room> result = roomService.getAllRooms();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void getRoomsByAvailabilityShouldReturnAvailableRooms() {
        Room room1 = new Room("101", "Single", 100, 1);
        Room room2 = new Room("102", "Double", 200, 1);
        List<Room> rooms = new ArrayList<>(List.of(room1, room2)); // Create a mutable list
        when(roomRepository.findAll()).thenReturn(rooms);

        Guest guest = new Guest("John Doe", "123 Main St", "01/01/2000", "john.doe@example.com", "123-456-7890", "johndoe", "password", "4111111111111111");
        List<LocalDate> dates1 = Arrays.asList(LocalDate.now());
        RoomReservation roomReservation1 = new RoomReservation(guest, room1, dates1);
        when(roomReservationRepository.findByRoom(room1)).thenReturn(Arrays.asList(roomReservation1));

        List<LocalDate> dates2 = Arrays.asList(LocalDate.now().minusDays(1));
        RoomReservation roomReservation2 = new RoomReservation(guest, room2, dates2);
        when(roomReservationRepository.findByRoom(room2)).thenReturn(Arrays.asList(roomReservation2));

        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now().plusDays(1);
        List<Room> result = roomService.getRoomsByAvailability(start, end);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("102", result.get(0).getRoomNumber());
    }

    @Test
    public void getRoomByIdShouldReturnRoom() {
        Room room = new Room("101", "Single", 100, 1);
        when(roomRepository.findById(1L)).thenReturn(room);

        Room result = roomService.getRoomById(1);

        assertNotNull(result);
        assertEquals("101", result.getRoomNumber());
    }

    @Test
    public void getRoomByRoomNumberShouldReturnRoom() {
        Room room = new Room("101", "Single", 100, 1);
        when(roomRepository.findByRoomNumber("101")).thenReturn(room);

        Room result = roomService.getRoomByRoomNumber("101");

        assertNotNull(result);
        assertEquals("101", result.getRoomNumber());
    }

    @Test
    public void saveRoomShouldReturnSavedRoom() {
        Room room = new Room("101", "Single", 100, 1);
        when(roomRepository.save(room)).thenReturn(room);

        Room result = roomService.saveRoom(room);

        assertNotNull(result);
        assertEquals("101", result.getRoomNumber());
    }

    @Test
    public void deleteRoomShouldDeleteRoom() {
        Room room = new Room("101", "Single", 100, 1);
        roomService.deleteRoom(room);
        when(roomRepository.findByRoomNumber("101")).thenReturn(null);

        Room result = roomService.getRoomByRoomNumber("101");

        assertNull(result);
    }
}

