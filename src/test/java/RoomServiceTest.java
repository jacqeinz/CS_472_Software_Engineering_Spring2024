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
        List<Room> availabel= roomService.getRoomsByAvailability(startDate, endDate);
        assertThat(Arrays.asList(availabel.toArray())).isEqualTo(rooms);
    }

    @Test
    public void testGetRoomsByAvailabilityFails(){
        var startDate=LocalDate.now();
        var endDate=startDate.plusDays(1);
        Room room=roomService.getRoomByRoomNumber("103");
        List<Room> rooms = roomRepo.findAll();
        List<Room> availabel= roomService.getRoomsByAvailability(startDate, endDate);
        assertThat(Arrays.asList(availabel.toArray())).doesNotContain(room);
    }


}
