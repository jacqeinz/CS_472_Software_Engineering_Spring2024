/**
 * RoomReservationDates.java
 */
package spring2024.cs472.hotelwebsite.entities;

// Imports necessary for the class
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Represents the start and end dates of a room reservation.
 * This class is used to store the start and end dates of a room reservation.
 *
 * @author Team ABCFG
 */
public class RoomReservationDates implements Serializable {

    // Attributes
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    /**
     * Default constructor.
     */
    public RoomReservationDates() {}

    /**
     * Constructs a RoomReservationDates object with specified start and end dates.
     *
     * @param startDate The start date of the reservation.
     * @param endDate The end date of the reservation.
     */
    public RoomReservationDates(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Retrieves the start date of the reservation.
     *
     * @return The start date.
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date of the reservation.
     *
     * @param startDate The start date to set.
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * Retrieves the end date of the reservation.
     *
     * @return The end date.
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date of the reservation.
     *
     * @param endDate The end date to set.
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
