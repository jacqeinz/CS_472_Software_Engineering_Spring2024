package spring2024.cs472.hotelwebsite;

import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import spring2024.cs472.hotelwebsite.entities.Room;

public class RoomTest {

    @Test
    public void testRoomGettersAndSetters() {
        Room room = new Room();
        room.setId(1L);
        room.setRoomNumber("101");
        room.setRoomType("Single");
        room.setPrice(100);
        room.setFloorNumber(1);

        assertThat(room.getId(), is(1L));
        assertThat(room.getRoomNumber(), is("101"));
        assertThat(room.getRoomType(), is("Single"));
        assertThat(room.getPrice(), is(100));
        assertThat(room.getFloorNumber(), is(1));
    }

    @Test
    public void testRoomConstructorAndGetters() {
        Room room = new Room("101", "Single", 100, 1);

        assertThat(room.getRoomNumber(), is("101"));
        assertThat(room.getRoomType(), is("Single"));
        assertThat(room.getPrice(), is(100));
        assertThat(room.getFloorNumber(), is(1));
    }
}
