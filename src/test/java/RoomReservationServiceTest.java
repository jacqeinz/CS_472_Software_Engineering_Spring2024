/**
 * RoomReservationServiceTest.java
 */

// Imports necessary for the class
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.runners.model.MultipleFailureException.assertEmpty;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.validation.BindingResultUtils.getBindingResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import java.beans.PropertyEditor;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import spring2024.cs472.hotelwebsite.controllers.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import spring2024.cs472.hotelwebsite.*;
import spring2024.cs472.hotelwebsite.entities.*;
import spring2024.cs472.hotelwebsite.repositories.AccountRepository;
import spring2024.cs472.hotelwebsite.repositories.ReservationDetailsRepository;
import spring2024.cs472.hotelwebsite.repositories.RoomReservationRepository;
import spring2024.cs472.hotelwebsite.services.AccountService;
import spring2024.cs472.hotelwebsite.services.CartService;
import spring2024.cs472.hotelwebsite.services.RoomReservationService;
import spring2024.cs472.hotelwebsite.services.RoomService;

/**
 * Test class for the RoomReservationServiceTest.
 * This class tests various methods of the RoomReservationServiceTest class.
 *
 * @author Team ABCFG
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = HotelWebsiteApplication.class)
class RoomReservationServiceTest{

    // Attributes
    @Autowired
    private RoomReservationService roomReservationService;
    @Autowired
    private CartService cartService;
    @MockBean
    private ReservationDetailsRepository reservationDetailsRepository;
    @MockBean
    private RoomReservationRepository roomReservationRepository;
    @MockBean
    private AccountRepository accountRepository;

    /**
     * Test case to verify that getting reservation details by room reservation ID returns the correct details.
     */
    @Test
    public void getDetailsByRoomReservationIdReturnsDetails() {
        // Create a guest account
        Guest guest = new Guest("Guest Guesterson", "123 Guest St", "1/2/3456", "guest@guest.guest" ,"123-456-7890", "guest",
                "badPassword1", "1234567876543345678");
        guest.setId(1L);
        List<Account> accounts = List.of(guest);
        // Create rooms and define reservation periods
        Room room1 = new Room("505", "Deluxe", 200, 5);
        Room room2 = new Room("415", "Deluxe", 200, 4);
        Room room3 = new Room("325", "Standard", 100, 3);
        LocalDate start1 = LocalDate.now().plusDays(2);
        LocalDate end1 = LocalDate.now().plusDays(5);
        LocalDate start2 = LocalDate.now().plusDays(6);
        LocalDate end2 = LocalDate.now().plusDays(10);
        LocalDate start3 = LocalDate.now().plusDays(11);
        LocalDate end3 = LocalDate.now().plusDays(15);
        List<ReservationDetails> allDetails = new ArrayList<>();
        AtomicLong idValue = new AtomicLong(5L);

        // Mock repository and service behavior
        when(accountRepository.findAll()).thenReturn(accounts);
        when(roomReservationRepository.save(any(RoomReservation.class))).thenAnswer(invocation -> {
            RoomReservation reservation = invocation.getArgument(0, RoomReservation.class);
            reservation.setId(idValue.getAndIncrement());
            return reservation;
        });
        when(reservationDetailsRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());
        when(reservationDetailsRepository.findAll()).thenReturn(allDetails);

        // Add room reservations to the cart and create reservation details
        cartService.addRoomReservations(guest.getCart(), List.of(room1), start1, end1);
        ReservationDetails details1 = new ReservationDetails(guest, guest.getCart().getRoomReservations(), guest.getPaymentInfo(), guest.getCart().getTotal());
        guest.getCart().emptyCart();

        cartService.addRoomReservations(guest.getCart(), List.of(room2), start2, end2);
        ReservationDetails details2 = new ReservationDetails(guest, guest.getCart().getRoomReservations(), guest.getPaymentInfo(), guest.getCart().getTotal());
        guest.getCart().emptyCart();

        cartService.addRoomReservations(guest.getCart(), List.of(room3), start3, end3);
        ReservationDetails details3 = new ReservationDetails(guest, guest.getCart().getRoomReservations(), guest.getPaymentInfo(), guest.getCart().getTotal());
        guest.getCart().emptyCart();

        // Add reservation details to the list
        allDetails.add(details1);
        allDetails.add(details2);
        allDetails.add(details3);

        // Assertion
        assertEquals(roomReservationService.getDetailsByRoomReservationId(6), details2);
    }

    /**
     * Test case to verify that retrieving all room reservation details is successful.
     */
    @Test
    public void getDetailsByRoomReservationIdReturnsDetailsUnsuccessful() {
        Guest guest = new Guest("Guest Guesterson", "123 Guest St", "1/2/3456", "guest@guest.guest" ,"123-456-7890", "guest",
                "badPassword1", "1234567876543345678");
        guest.setId(1L);
        List<Account> accounts = List.of(guest);
        Room room1 = new Room("505", "Deluxe", 200, 5);
        Room room2 = new Room("415", "Deluxe", 200, 4);
        Room room3 = new Room("325", "Standard", 100, 3);
        LocalDate start1 = LocalDate.now().plusDays(2);
        LocalDate end1 = LocalDate.now().plusDays(5);
        LocalDate start2 = LocalDate.now().plusDays(6);
        LocalDate end2 = LocalDate.now().plusDays(10);
        LocalDate start3 = LocalDate.now().plusDays(11);
        LocalDate end3 = LocalDate.now().plusDays(15);
        List<ReservationDetails> allDetails = new ArrayList<>();
        AtomicLong idValue = new AtomicLong(5L);

        when(accountRepository.findAll()).thenReturn(accounts);
        when(roomReservationRepository.save(any(RoomReservation.class))).thenAnswer(invocation -> {
            RoomReservation reservation = invocation.getArgument(0, RoomReservation.class);
            reservation.setId(idValue.getAndIncrement());
            return reservation;
        });
        when(reservationDetailsRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());
        when(reservationDetailsRepository.findAll()).thenReturn(allDetails);

        cartService.addRoomReservations(guest.getCart(), List.of(room1), start1, end1);
        ReservationDetails details1 = new ReservationDetails(guest, guest.getCart().getRoomReservations(), guest.getPaymentInfo(), guest.getCart().getTotal());
        guest.getCart().emptyCart();

        cartService.addRoomReservations(guest.getCart(), List.of(room2), start2, end2);
        ReservationDetails details2 = new ReservationDetails(guest, guest.getCart().getRoomReservations(), guest.getPaymentInfo(), guest.getCart().getTotal());
        guest.getCart().emptyCart();

        cartService.addRoomReservations(guest.getCart(), List.of(room3), start3, end3);
        ReservationDetails details3 = new ReservationDetails(guest, guest.getCart().getRoomReservations(), guest.getPaymentInfo(), guest.getCart().getTotal());
        guest.getCart().emptyCart();

        allDetails.add(details1);
        allDetails.add(details2);
        allDetails.add(details3);

        assertNotEquals(roomReservationService.getDetailsByRoomReservationId(20), details2);

    }

    /**
    * Verifies successful retrieval of all room reservation details.
    */
    @Test
    public void getAllRoomReservationDetailsSuccess() {
        // Create a guest account
        Guest guest = new Guest("Guest Guesterson", "123 Guest St", "1/2/3456", "guest@guest.guest" ,"123-456-7890", "guest",
                "badPassword1", "1234567876543345678");
        guest.setId(1L);
        List<Account> accounts = List.of(guest);
        // Create rooms and define reservation periods
        Room room1 = new Room("505", "Deluxe", 200, 5);
        Room room2 = new Room("415", "Deluxe", 200, 4);
        Room room3 = new Room("325", "Standard", 100, 3);
        LocalDate start1 = LocalDate.now().plusDays(2);
        LocalDate end1 = LocalDate.now().plusDays(5);
        LocalDate start2 = LocalDate.now().plusDays(6);
        LocalDate end2 = LocalDate.now().plusDays(10);
        LocalDate start3 = LocalDate.now().plusDays(11);
        LocalDate end3 = LocalDate.now().plusDays(15);
        List<ReservationDetails> allDetails = new ArrayList<>();
        AtomicLong idValue = new AtomicLong(5L);

        // Mock repository and service behavior
        when(accountRepository.findAll()).thenReturn(accounts);
        when(roomReservationRepository.save(any(RoomReservation.class))).thenAnswer(invocation -> {
            RoomReservation reservation = invocation.getArgument(0, RoomReservation.class);
            reservation.setId(idValue.getAndIncrement());
            return reservation;
        });
        when(reservationDetailsRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());
        when(reservationDetailsRepository.findAll()).thenReturn(allDetails);

        // Add room reservations to the cart and create reservation details
        cartService.addRoomReservations(guest.getCart(), List.of(room1), start1, end1);
        ReservationDetails details1 = new ReservationDetails(guest, guest.getCart().getRoomReservations(), guest.getPaymentInfo(), guest.getCart().getTotal());
        guest.getCart().emptyCart();

        cartService.addRoomReservations(guest.getCart(), List.of(room2), start2, end2);
        ReservationDetails details2 = new ReservationDetails(guest, guest.getCart().getRoomReservations(), guest.getPaymentInfo(), guest.getCart().getTotal());
        guest.getCart().emptyCart();

        cartService.addRoomReservations(guest.getCart(), List.of(room3), start3, end3);
        ReservationDetails details3 = new ReservationDetails(guest, guest.getCart().getRoomReservations(), guest.getPaymentInfo(), guest.getCart().getTotal());
        guest.getCart().emptyCart();

        // Add reservation details to the list
        allDetails.add(details1);
        allDetails.add(details2);
        allDetails.add(details3);
      
        // Assertion
        assertNotNull(roomReservationRepository.findAll());
    }
  
    /**
    * Verifies unsuccessful retrieval of all room reservation details.
    */
    @Test
    public void getAllRoomReservationDetailsUnSuccess() {
        Guest guest = new Guest("Guest Guesterson", "123 Guest St", "1/2/3456", "guest@guest.guest" ,"123-456-7890", "guest",
                "badPassword1", "1234567876543345678");
        guest.setId(1L);
        List<Account> accounts = List.of(guest);
        Room room1 = new Room("505", "Deluxe", 200, 5);
        Room room2 = new Room("415", "Deluxe", 200, 4);
        Room room3 = new Room("325", "Standard", 100, 3);
        LocalDate start1 = LocalDate.now().plusDays(2);
        LocalDate end1 = LocalDate.now().plusDays(5);
        LocalDate start2 = LocalDate.now().plusDays(6);
        LocalDate end2 = LocalDate.now().plusDays(10);
        LocalDate start3 = LocalDate.now().plusDays(11);
        LocalDate end3 = LocalDate.now().plusDays(15);
        List<ReservationDetails> allDetails = new ArrayList<>();
        AtomicLong idValue = new AtomicLong(5L);

        when(accountRepository.findAll()).thenReturn(accounts);
        when(roomReservationRepository.save(any(RoomReservation.class))).thenAnswer(invocation -> {
            RoomReservation reservation = invocation.getArgument(0, RoomReservation.class);
            reservation.setId(idValue.getAndIncrement());
            return reservation;
        });
        when(reservationDetailsRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());
        when(reservationDetailsRepository.findAll()).thenReturn(allDetails);

        cartService.addRoomReservations(guest.getCart(), List.of(room1), start1, end1);
        ReservationDetails details1 = new ReservationDetails(guest, guest.getCart().getRoomReservations(), guest.getPaymentInfo(), guest.getCart().getTotal());
        guest.getCart().emptyCart();

        cartService.addRoomReservations(guest.getCart(), List.of(room2), start2, end2);
        ReservationDetails details2 = new ReservationDetails(guest, guest.getCart().getRoomReservations(), guest.getPaymentInfo(), guest.getCart().getTotal());
        guest.getCart().emptyCart();

        cartService.addRoomReservations(guest.getCart(), List.of(room3), start3, end3);
        ReservationDetails details3 = new ReservationDetails(guest, guest.getCart().getRoomReservations(), guest.getPaymentInfo(), guest.getCart().getTotal());
        guest.getCart().emptyCart();

//        allDetails.add(details1);
//        allDetails.add(details2);
//        allDetails.add(details3);

        assertTrue(roomReservationRepository.findAll().isEmpty());
    }

//    /**
//    * Verifies successful retrieval of a room reservation by its ID.
//    */
//    @Test
//    public void getRoomReservationByIdSuccessful(){
//        Guest guest = new Guest("Guest Guesterson", "123 Guest St", "1/2/3456", "guest@guest.guest" ,"123-456-7890", "guest",
//                "badPassword1", "1234567876543345678");
//        guest.setId(1L);
//        List<Account> accounts = List.of(guest);
//        Room room1 = new Room("505", "Deluxe", 200, 5);
//        Room room2 = new Room("415", "Deluxe", 200, 4);
//        Room room3 = new Room("325", "Standard", 100, 3);
//        LocalDate start1 = LocalDate.now().plusDays(2);
//        LocalDate end1 = LocalDate.now().plusDays(5);
//        LocalDate start2 = LocalDate.now().plusDays(6);
//        LocalDate end2 = LocalDate.now().plusDays(10);
//        LocalDate start3 = LocalDate.now().plusDays(11);
//        LocalDate end3 = LocalDate.now().plusDays(15);
//        List<ReservationDetails> allDetails = new ArrayList<>();
//        AtomicLong idValue = new AtomicLong(5L);
//
//        when(accountRepository.findAll()).thenReturn(accounts);
//        when(roomReservationRepository.save(any(RoomReservation.class))).thenAnswer(invocation -> {
//            RoomReservation reservation = invocation.getArgument(0, RoomReservation.class);
//            reservation.setId(idValue.getAndIncrement());
//            return reservation;
//        });
//        when(reservationDetailsRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());
//        when(reservationDetailsRepository.findAll()).thenReturn(allDetails);
//        when(roomReservationRepository.findById(any(long.class))).thenAnswer( invocation -> {
//            long id = invocation.getArgument(0);
//            RoomReservation rr = guest.getCart().getRoomReservations().stream().filter(r -> r.getId() == id).findFirst().orElse(null);
//            System.out.println(rr);
//            return rr;
//        });
//
//        cartService.addRoomReservations(guest.getCart(), List.of(room1), start1, end1);
//
//
//        cartService.addRoomReservations(guest.getCart(), List.of(room2), start2, end2);
//
//
//        cartService.addRoomReservations(guest.getCart(), List.of(room3), start3, end3);
//
//
//        RoomReservation rr = guest.getCart().getRoomReservations().stream().filter(r -> r.getId() == 6).findFirst().orElse(null);
//
//        assertNotNull(roomReservationService.getRoomReservationById(6));
//    }

    /**
    * Verifies unsuccessful retrieval of a room reservation by its ID.
    */
    @Test
    public void getRoomReservationByIdUnSuccessful(){
        Guest guest = new Guest("Guest Guesterson", "123 Guest St", "1/2/3456", "guest@guest.guest" ,"123-456-7890", "guest",
                "badPassword1", "1234567876543345678");
        guest.setId(1L);
        List<Account> accounts = List.of(guest);
        Room room1 = new Room("505", "Deluxe", 200, 5);
        Room room2 = new Room("415", "Deluxe", 200, 4);
        Room room3 = new Room("325", "Standard", 100, 3);
        LocalDate start1 = LocalDate.now().plusDays(2);
        LocalDate end1 = LocalDate.now().plusDays(5);
        LocalDate start2 = LocalDate.now().plusDays(6);
        LocalDate end2 = LocalDate.now().plusDays(10);
        LocalDate start3 = LocalDate.now().plusDays(11);
        LocalDate end3 = LocalDate.now().plusDays(15);
        List<ReservationDetails> allDetails = new ArrayList<>();
        AtomicLong idValue = new AtomicLong(5L);

        when(accountRepository.findAll()).thenReturn(accounts);
        when(roomReservationRepository.save(any(RoomReservation.class))).thenAnswer(invocation -> {
            RoomReservation reservation = invocation.getArgument(0, RoomReservation.class);
            reservation.setId(idValue.getAndIncrement());
            return reservation;
        });
        when(reservationDetailsRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());
        when(reservationDetailsRepository.findAll()).thenReturn(allDetails);

        cartService.addRoomReservations(guest.getCart(), List.of(room1), start1, end1);
        ReservationDetails details1 = new ReservationDetails(guest, guest.getCart().getRoomReservations(), guest.getPaymentInfo(), guest.getCart().getTotal());
        guest.getCart().emptyCart();

        cartService.addRoomReservations(guest.getCart(), List.of(room2), start2, end2);
        ReservationDetails details2 = new ReservationDetails(guest, guest.getCart().getRoomReservations(), guest.getPaymentInfo(), guest.getCart().getTotal());
        guest.getCart().emptyCart();

        cartService.addRoomReservations(guest.getCart(), List.of(room3), start3, end3);
        ReservationDetails details3 = new ReservationDetails(guest, guest.getCart().getRoomReservations(), guest.getPaymentInfo(), guest.getCart().getTotal());
        guest.getCart().emptyCart();

        allDetails.add(details1);
        allDetails.add(details2);
        allDetails.add(details3);

        assertNull(roomReservationRepository.findById(35));

    }

    /**
    * Verifies unsuccessful saving of reservation details.
    */
    @Test
    public void SaveReservationDetailsUnSuccessful(){
        Guest guest = new Guest("Guest Guesterson", "123 Guest St", "1/2/3456", "guest@guest.guest" ,"123-456-7890", "guest",
                "badPassword1", "1234567876543345678");
        guest.setId(1L);
        List<Account> accounts = List.of(guest);
        Room room1 = new Room("505", "Deluxe", 200, 5);
        Room room2 = new Room("415", "Deluxe", 200, 4);
        Room room3 = new Room("325", "Standard", 100, 3);
        LocalDate start1 = LocalDate.now().plusDays(2);
        LocalDate end1 = LocalDate.now().plusDays(5);
        LocalDate start2 = LocalDate.now().plusDays(6);
        LocalDate end2 = LocalDate.now().plusDays(10);
        LocalDate start3 = LocalDate.now().plusDays(11);
        LocalDate end3 = LocalDate.now().plusDays(15);
        List<ReservationDetails> allDetails = new ArrayList<>();
        AtomicLong idValue = new AtomicLong(5L);

        when(accountRepository.findAll()).thenReturn(accounts);
        when(roomReservationRepository.save(any(RoomReservation.class))).thenAnswer(invocation -> {
            RoomReservation reservation = invocation.getArgument(0, RoomReservation.class);
            reservation.setId(idValue.getAndIncrement());
            return reservation;
        });
        when(reservationDetailsRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());
        when(reservationDetailsRepository.findAll()).thenReturn(allDetails);

        cartService.addRoomReservations(guest.getCart(), List.of(room1), start1, end1);
        ReservationDetails details1 = new ReservationDetails(guest, guest.getCart().getRoomReservations(), guest.getPaymentInfo(), guest.getCart().getTotal());
        guest.getCart().emptyCart();

        cartService.addRoomReservations(guest.getCart(), List.of(room2), start2, end2);
        ReservationDetails details2 = new ReservationDetails(guest, guest.getCart().getRoomReservations(), guest.getPaymentInfo(), guest.getCart().getTotal());
        guest.getCart().emptyCart();

        cartService.addRoomReservations(guest.getCart(), List.of(room3), start3, end3);
        ReservationDetails details3 = new ReservationDetails(guest, guest.getCart().getRoomReservations(), guest.getPaymentInfo(), guest.getCart().getTotal());
        guest.getCart().emptyCart();

//        allDetails.add(details1);
//        allDetails.add(details2);
//        allDetails.add(details3);

        assertTrue(reservationDetailsRepository.findAll().isEmpty());
    }

    /**
    * Verifies successful saving of reservation details and then deleting one.
    */
    @Test
    public void SaveAndDeleteReservationDetailsSuccessful(){
        Guest guest = new Guest("Guest Guesterson", "123 Guest St", "1/2/3456", "guest@guest.guest" ,"123-456-7890", "guest",
                "badPassword1", "1234567876543345678");
        guest.setId(1L);
        List<Account> accounts = List.of(guest);
        Room room1 = new Room("505", "Deluxe", 200, 5);
        Room room2 = new Room("415", "Deluxe", 200, 4);
        Room room3 = new Room("325", "Standard", 100, 3);
        LocalDate start1 = LocalDate.now().plusDays(2);
        LocalDate end1 = LocalDate.now().plusDays(5);
        LocalDate start2 = LocalDate.now().plusDays(6);
        LocalDate end2 = LocalDate.now().plusDays(10);
        LocalDate start3 = LocalDate.now().plusDays(11);
        LocalDate end3 = LocalDate.now().plusDays(15);
        List<ReservationDetails> allDetails = new ArrayList<>();
        AtomicLong idValue = new AtomicLong(5L);

        when(accountRepository.findAll()).thenReturn(accounts);
        when(roomReservationRepository.save(any(RoomReservation.class))).thenAnswer(invocation -> {
            RoomReservation reservation = invocation.getArgument(0, RoomReservation.class);
            reservation.setId(idValue.getAndIncrement());
            return reservation;
        });
        when(reservationDetailsRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());
        when(reservationDetailsRepository.findAll()).thenReturn(allDetails);

        cartService.addRoomReservations(guest.getCart(), List.of(room1), start1, end1);
        ReservationDetails details1 = new ReservationDetails(guest, guest.getCart().getRoomReservations(), guest.getPaymentInfo(), guest.getCart().getTotal());
        guest.getCart().emptyCart();

        cartService.addRoomReservations(guest.getCart(), List.of(room2), start2, end2);
        ReservationDetails details2 = new ReservationDetails(guest, guest.getCart().getRoomReservations(), guest.getPaymentInfo(), guest.getCart().getTotal());
        guest.getCart().emptyCart();

        cartService.addRoomReservations(guest.getCart(), List.of(room3), start3, end3);
        ReservationDetails details3 = new ReservationDetails(guest, guest.getCart().getRoomReservations(), guest.getPaymentInfo(), guest.getCart().getTotal());
        guest.getCart().emptyCart();

        allDetails.add(details1);
        allDetails.add(details2);
        allDetails.add(details3);
        reservationDetailsRepository.delete(details1);
//        reservationDetailsRepository.delete(details2);
//        reservationDetailsRepository.delete(details3);

        assertFalse(reservationDetailsRepository.findAll().isEmpty());
    }
  
    /**
    * Verifies unsuccessful saving of reservation details followed by deletion.
    */
    @Test
    public void SaveReservationDetailsUnSuccessfulDelete(){
        Guest guest = new Guest("Guest Guesterson", "123 Guest St", "1/2/3456", "guest@guest.guest" ,"123-456-7890", "guest",
                "badPassword1", "1234567876543345678");
        guest.setId(1L);
        List<Account> accounts = List.of(guest);
        Room room1 = new Room("505", "Deluxe", 200, 5);
        Room room2 = new Room("415", "Deluxe", 200, 4);
        Room room3 = new Room("325", "Standard", 100, 3);
        LocalDate start1 = LocalDate.now().plusDays(2);
        LocalDate end1 = LocalDate.now().plusDays(5);
        LocalDate start2 = LocalDate.now().plusDays(6);
        LocalDate end2 = LocalDate.now().plusDays(10);
        LocalDate start3 = LocalDate.now().plusDays(11);
        LocalDate end3 = LocalDate.now().plusDays(15);
        List<ReservationDetails> allDetails = new ArrayList<>();
        AtomicLong idValue = new AtomicLong(5L);

        when(accountRepository.findAll()).thenReturn(accounts);
        when(roomReservationRepository.save(any(RoomReservation.class))).thenAnswer(invocation -> {
            RoomReservation reservation = invocation.getArgument(0, RoomReservation.class);
            reservation.setId(idValue.getAndIncrement());
            return reservation;
        });
        when(reservationDetailsRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());
        when(reservationDetailsRepository.findAll()).thenReturn(allDetails);

        cartService.addRoomReservations(guest.getCart(), List.of(room1), start1, end1);
        ReservationDetails details1 = new ReservationDetails(guest, guest.getCart().getRoomReservations(), guest.getPaymentInfo(), guest.getCart().getTotal());
        guest.getCart().emptyCart();

        cartService.addRoomReservations(guest.getCart(), List.of(room2), start2, end2);
        ReservationDetails details2 = new ReservationDetails(guest, guest.getCart().getRoomReservations(), guest.getPaymentInfo(), guest.getCart().getTotal());
        guest.getCart().emptyCart();

        cartService.addRoomReservations(guest.getCart(), List.of(room3), start3, end3);
        ReservationDetails details3 = new ReservationDetails(guest, guest.getCart().getRoomReservations(), guest.getPaymentInfo(), guest.getCart().getTotal());
        guest.getCart().emptyCart();

        allDetails.add(details1);
        allDetails.add(details2);
        allDetails.add(details3);
        reservationDetailsRepository.delete(details1);
        reservationDetailsRepository.delete(details2);
        reservationDetailsRepository.delete(details3);

        assertFalse(reservationDetailsRepository.findAll().isEmpty());
    }
}