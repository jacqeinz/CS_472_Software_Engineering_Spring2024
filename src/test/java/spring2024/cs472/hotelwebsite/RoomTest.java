/**
 * RoomTest.java
 */
package spring2024.cs472.hotelwebsite;

// Imports necessary for the class
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import spring2024.cs472.hotelwebsite.entities.Room;

/**
 * This class contains unit tests for the Room class, testing its getters, setters, and constructors.
 *
 * @author Team ABCFG
 */
public class RoomTest {

    /**
     * Test method to verify the getters and setters of the Room class.
     * This method sets attributes using setters and then verifies their values using getters.
     */
    @Test
    public void testRoomGettersAndSetters() {
        // Create a new Room object
        Room room = new Room();

        // Set attributes using setters
        room.setId(1L);
        room.setRoomNumber("101");
        room.setRoomType("Single");
        room.setPrice(100);
        room.setFloorNumber(1);

        // Verify attribute values using getters and assertions
        assertThat(room.getId(), is(1L));
        assertThat(room.getRoomNumber(), is("101"));
        assertThat(room.getRoomType(), is("Single"));
        assertThat(room.getPrice(), is(100));
        assertThat(room.getFloorNumber(), is(1));
    }

    /**
     * Test method to verify the constructor and getters of the Room class.
     * This method creates a Room object using a constructor and verifies its attributes using getters.
     */
    @Test
    public void testRoomConstructorAndGetters() {
        // Create a new Room object using constructor
        Room room = new Room("101", "Single", 100, 1);

        // Verify attribute values using getters and assertions
        assertThat(room.getRoomNumber(), is("101"));
        assertThat(room.getRoomType(), is("Single"));
        assertThat(room.getPrice(), is(100));
        assertThat(room.getFloorNumber(), is(1));
    }
}
