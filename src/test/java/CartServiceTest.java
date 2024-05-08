/**
 * CartServiceTest.java
 */

// Imports necessary for the class
import static org.apache.coyote.http11.Constants.a;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.validation.BindingResultUtils.getBindingResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import java.beans.PropertyEditor;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.ArgumentCaptor;
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
import org.springframework.mail.javamail.JavaMailSender;
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
 * Test class for the CartServiceTest.
 * This class tests various methods of the CartServiceTest class.
 *
 * @author Team ABCFG
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = HotelWebsiteApplication.class)
@AutoConfigureMockMvc
class CartServiceTest {

    // Attributes
    @Autowired
    private CartService cartService;
    @MockBean
    private RoomReservationRepository roomReservationRepository;
    @MockBean
    private AccountService accountService;
    @Autowired
    RoomReservationService roomReservationService;
    @MockBean
    private AccountRepository accountRepository;
    @MockBean
    private ReservationDetailsRepository reservationDetailsRepository;
    @MockBean
    JavaMailSender javaMailSender;

    /**
     * Test case to verify that adding a room reservation to the cart is successful.
     */
    @Test
    public void addRoomReservationReturnSuccess() {
        // Create a guest account
        Guest guest = new Guest("Guest Guesterson", "123 Guest St", "1/2/3456", "guest@guest.guest" ,"123-456-7890", "guest",
                "badPassword1", "1234567876543345678");
        guest.setId(1L);
        List<Account> accounts = List.of(guest);
        when(accountRepository.findAll()).thenReturn(accounts);
        // Define start and end dates
        LocalDate start = LocalDate.now().plusDays(2);
        LocalDate end = LocalDate.now().plusDays(5);
        // Create a room
        Room room = new Room("505", "Deluxe", 200, 5);

        // Mock the behavior of the room reservation repository to return the saved room reservation
        when(roomReservationRepository.save(any(RoomReservation.class))).then(AdditionalAnswers.returnsFirstArg());
        // Add room reservation to the guest's cart
        cartService.addRoomReservations(guest.getCart(), List.of(room), start, end);

        // Assert that the cart contains one reservation
        assertEquals(1, guest.getCart().getCartSize());
    }

    /**
     * Test case to verify that checking out the cart is successful.
     */
    @Test
    public void addRoomReservationReturnUnSuccess() {

        Guest guest = new Guest("Guest Guesterson", "123 Guest St", "1/2/3456", "guest@guest.guest" ,"123-456-7890", "guest",
                "badPassword1", "1234567876543345678");
        guest.setId(1L);
        List<Account> accounts = List.of(guest);
        when(accountRepository.findAll()).thenReturn(accounts);
        LocalDate start = LocalDate.now().plusDays(2);
        LocalDate end = LocalDate.now().plusDays(5);
        Room room = new Room("505", "Deluxe", 200, 5);
//        RoomReservation returnedRoomReservation;
//        when(roomReservationRepository.save(any(RoomReservation.class))).then(AdditionalAnswers.returnsFirstArg());
//        cartService.addRoomReservations(guest.getCart(), List.of(room), start, end);
        assertEquals(0, guest.getCart().getCartSize());
    }

     /**
     * Test case to verify that the checkoutCart method successfully checks out a guest's cart and returns success.
     * It tests the functionality of adding room reservations to a cart, saving the guest account, and sending a confirmation email.
     */
    @Test
    public void checkoutCartReturnSuccess() {
        // Create a guest account
        Guest guest = new Guest("Guest Guesterson", "123 Guest St", "1/2/3456", "guest@guest.guest", "123-456-7890", "guest",
                "badPassword1", "1234567876543345678");
        guest.setId(1L);
        List<Account> accounts = List.of(guest);
        when(accountRepository.findAll()).thenReturn(accounts);

        // Define start and end dates
        LocalDate start = LocalDate.now().plusDays(2);
        LocalDate end = LocalDate.now().plusDays(5);

        // Create a room
        Room room = new Room("505", "Deluxe", 200, 5);

        // Mock the behavior of the repositories and service methods
        when(roomReservationRepository.save(any(RoomReservation.class))).then(AdditionalAnswers.returnsFirstArg());
        when(reservationDetailsRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());
        when(accountService.sendConfirmationEmail(any(Account.class), any(ReservationDetails.class))).thenReturn(true);

        // Capture the account and reservation details passed to sendConfirmationEmail method
        ArgumentCaptor<Account> capturedAccount = ArgumentCaptor.forClass(Account.class);
        ArgumentCaptor<ReservationDetails> capturedReservationDetails = ArgumentCaptor.forClass(ReservationDetails.class);

        // Add room reservation to the guest's cart
        cartService.addRoomReservations(guest.getCart(), List.of(room), start, end);
        // Save the guest account
        accountRepository.save(accounts.get(0));
        // Checkout the cart
        ReservationDetails reservationDetails = cartService.checkoutCart(guest.getCart(), guest);

        // Verify that sendConfirmationEmail method was called with the correct arguments
        verify(accountService).sendConfirmationEmail(capturedAccount.capture(), capturedReservationDetails.capture());
        Account capAccount = capturedAccount.getValue();
        ReservationDetails capDetails = capturedReservationDetails.getValue();

        // Assertions
        assertThat(reservationDetails, is(in(guest.getCurrentReservations())));
        assertEquals(capDetails, reservationDetails);
        assertEquals(capAccount, guest);
    }

    /**
     * Test case to verify that the setupDateList method returns the correct list of dates.
     */
    @Test
    public void setupDateListReturnsCorrectDateArray() {
        // Define start and end dates
        LocalDate start = LocalDate.now().plusDays(2);
        LocalDate end = LocalDate.now().plusDays(10);

        // Call the setupDateList method
        List<LocalDate> dates = cartService.setupDateList(start, end);

        // Assertions
        assertNotNull(dates);
        assertEquals(dates.size(), 8);
    }

    /**
     * Test case to verify that the setupDateList method returns a correct date array with a specific range of dates.
     */
    @Test
    public void setupDateListReturnsCorrectDateArray2() {
        // Define the start and end dates for the date range
        LocalDate start = LocalDate.now().plusDays(3);
        LocalDate end = LocalDate.now().plusDays(8);

        // Call the setupDateList method to generate the list of dates
        List<LocalDate> dates = cartService.setupDateList(start, end);

        // Ensure that the generated list is not null and has the expected size
        assertNotNull(dates);
        assertEquals(dates.size(), 5);
    }

    /**
     * Test case to verify that the setupDateList method returns a correct date array with a different range of dates.
     */
    @Test
    public void setupDateListReturnsCorrectDateArray3() {
        // Define the start and end dates for the date range
        LocalDate start = LocalDate.now().plusDays(1);
        LocalDate end = LocalDate.now().plusDays(20);

        // Call the setupDateList method to generate the list of dates
        List<LocalDate> dates = cartService.setupDateList(start, end);

        // Ensure that the generated list is not null and has the expected size
        assertNotNull(dates);
        assertEquals(dates.size(), 19);
    }
}
