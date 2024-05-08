/**
 * AccountServiceTest.java
 */

// Imports necessary for the class
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.*;

import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.junit.jupiter.api.Test;
import spring2024.cs472.hotelwebsite.*;
import spring2024.cs472.hotelwebsite.entities.*;
import spring2024.cs472.hotelwebsite.repositories.AccountRepository;
import spring2024.cs472.hotelwebsite.repositories.ReservationDetailsRepository;
import spring2024.cs472.hotelwebsite.repositories.RoomReservationRepository;
import spring2024.cs472.hotelwebsite.repositories.TokenRepository;
import spring2024.cs472.hotelwebsite.services.AccountService;
import spring2024.cs472.hotelwebsite.services.CartService;

import spring2024.cs472.hotelwebsite.services.ResetPasswordService;
import spring2024.cs472.hotelwebsite.entities.PasswordResetToken;

/**
 * Test class for the AccountService.
 * This class tests various methods of the AccountService class.
 *
 * @author Team ABCFG
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = HotelWebsiteApplication.class)
@AutoConfigureMockMvc
class AccountServiceTest {

    // Attributes
    @Autowired
    private AccountService accountService;
    @Autowired
    private CartService cartService;
    @Autowired
    private ResetPasswordService resetPasswordService;
    @MockBean
    private AccountRepository accountRepository;
    @MockBean
    private RoomReservationRepository roomReservationRepository;
    @MockBean
    private ReservationDetailsRepository reservationDetailsRepository;
    @MockBean
    JavaMailSender javaMailSender;


    @Autowired
    private TokenRepository tokenRepository;


//    @Before
//    public void setUp() {
//    }

//    @Test
//    void showUpdateFormGuest() {
//    }

    /**
     * Test case to validate that login with incorrect credentials returns null.
     */
    @Test
    public void validateLoginShouldReturnNull() {
        // Create a guest account with specific credentials
        Guest guest = new Guest("Guest Guesterson", "123 Guest St", "1/2/3456", "guest@guest.guest" ,"123-456-7890", "guest",
                "badPassword1", "1234567876543345678");
        guest.setId(1L);
        List<Account> accounts = List.of(guest);
        // Mock the behavior of accountRepository to return the guest account
        when(accountRepository.findAll()).thenReturn(accounts);

        // Validate login with incorrect password
        Account account = accountService.validateLogin("guest", "wrongPassword2");

        // Assert that the login attempt returns null
        assertNull(account);

    }

    /**
     * Test case to validate that login with correct credentials returns an account.
     */
    @Test
    public void validateLoginShouldReturnSuccess() {
        // Create a guest account with specific credentials
        Guest guest = new Guest("Guest Guesterson", "123 Guest St", "1/2/3456", "guest@guest.guest" ,"123-456-7890", "guest",
                "badPassword1", "1234567876543345678");
        guest.setId(1L);
        List<Account> accounts = List.of(guest);
        // Mock the behavior of accountRepository to return the guest account
        when(accountRepository.findAll()).thenReturn(accounts);

        // Validate login with correct password
        Account account = accountService.validateLogin("guest", "badPassword1");

        // Assert that the login attempt returns a non-null account
        assertNotNull(account);
    }

    /**
     * Test case to validate that login with correct admin credentials returns an admin account.
     */
    @Test
    public void validateLoginShouldReturnSuccessAdmin() {
        // Create an admin account with specific credentials
        Admin admin = new Admin(true, 123456);
        admin.setUserName("Admin");
        admin.setUserPassword("admin");
        admin.setId(1L);
        List<Account> accounts = List.of(admin);
        // Mock the behavior of accountRepository to return the admin account
        when(accountRepository.findAll()).thenReturn(accounts);

        // Validate admin login with correct password
        Account account = accountService.validateLogin("Admin", "admin");

        // Assert that the login attempt returns a non-null admin account
        assertNotNull(account);
    }

    /**
     * Test case to validate that login with incorrect admin credentials returns null.
     */
    @Test
    public void validateLoginShouldReturnNullAdmin() {
        // Create an admin account with specific credentials
        Admin admin = new Admin(true, 123456);
        admin.setUserName("Admin");
        admin.setUserPassword("admin");
        admin.setId(1L);
        List<Account> accounts = List.of(admin);
        // Mock the behavior of accountRepository to return the admin account
        when(accountRepository.findAll()).thenReturn(accounts);

        // Validate admin login with incorrect password
        Account account = accountService.validateLogin("admin", "incorrectPassword1");

        // Assert that the login attempt returns null
        assertNull(account);
    }

    /**
     * Test case to validate that sending a confirmation email is successful.
     */
    @Test
    public void sendConfirmationShouldReturnSuccess() {
        // Create a guest account and a room
        Guest guest = new Guest("Guest Guesterson", "123 Guest St", "1/2/3456",
                "iam13islucky@gmail.com" ,"123-456-7890", "guest",
                "badPassword1", "1234567876543345678");
        guest.setId(1L);
        List<Account> accounts = List.of(guest);
        LocalDate start = LocalDate.now().plusDays(2);
        LocalDate end = LocalDate.now().plusDays(5);
        Room room = new Room("505", "Deluxe", 200, 5);

        // Mock behavior of repositories and service methods
        when(accountRepository.findAll()).thenReturn(accounts);
        when(roomReservationRepository.save(any(RoomReservation.class))).then(AdditionalAnswers.returnsFirstArg());
        when(reservationDetailsRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());

        // Capture the email message sent by the method
        ArgumentCaptor<SimpleMailMessage> capturedMail = ArgumentCaptor.forClass(SimpleMailMessage.class);

        // Add room reservation to guest's cart and create reservation details
        cartService.addRoomReservations(guest.getCart(), List.of(room), start, end);
        ReservationDetails details = new ReservationDetails(guest, guest.getCart().getRoomReservations(), guest.getPaymentInfo(), guest.getCart().getTotal());

        // Call the method to send confirmation email
        boolean result = accountService.sendConfirmationEmail(guest, details);
        // Verify that the email was sent
        verify(javaMailSender).send(capturedMail.capture());
        SimpleMailMessage capturedMailMessage = capturedMail.getValue();

        // Assertions
        assertTrue(result);
        assertTrue(Objects.requireNonNull(capturedMailMessage.getText()).contains(Double.toString(details.getTotal())));
        assertEquals(Objects.requireNonNull(capturedMailMessage.getTo())[0], guest.getEmail());
    }

    /**
     * Test case to verify that a reset password email is sent successfully.
     */
    @Test
    public void resetPasswordEmailIsSent() {
        // Create a new account
        Account account = new Account("Guest Guesterson", "123 Guest St", "1/2/3456", "guest@guest.guest", "123-456-7890",
                "user", "12");
        // Ensure that the email sending process returns "Success"
        assertEquals("Success", accountService.sendEmail(account));
    }

    /**
     * Test case to verify that a reset token is generated successfully for an account.
     */
    @Test
    public void resetTokenCreatedSuccessfully() {
        // Create a new account
        Account account = new Account("Guest Guesterson", "123 Guest St", "1/2/3456", "guest@guest.guest", "123-456-7890", "guest",
                "badPassword1", "1234567876543345678");
        // Generate a reset token for the account and ensure it is not null
        String result = accountService.generateRestToken(account);
        assertNotNull(result);
    }

    /**
     * Test case to verify that an account's password is changed after a reset process.
     */
    @Test
    public void accountPasswordChangedAfterReset() {
        // Create a new account
        Account account = new Account("Guest Guesterson", "123 Guest St", "1/2/3456", "guest@guest.guest", "123-456-7890",
                "user", "12");
        // Save the account in the repository
        accountRepository.save(account);
        // Generate a reset token for the account
        String result = accountService.generateRestToken(account);
        // Mock the repository behavior
        when(accountRepository.findByEmail("guest@guest.guest")).thenReturn(account);
        // Perform the reset password process
        resetPasswordService.resetPasswordProcess(result, "1234", "guest@guest.guest");
        // Ensure that the account's password is changed to the new one
        assertEquals("1234", account.getUserPassword());
    }

    /**
     * Test case to verify that a reset token is expired.
     */
    @Test
    void TokenIsExpired() {
        // Create a new account
        Account account = new Account("Guest Guesterson", "123 Guest St", "1/2/3456", "guest@guest.guest", "123-456-7890",
                "user", "12");
        // Create a password reset token with an expiry date in the past
        PasswordResetToken token = new PasswordResetToken();
        LocalDateTime expiryDateTime = LocalDateTime.now().minusMinutes(2);
        token.setExpiryDateTime(expiryDateTime);
        // Ensure that the token is considered expired
        assertFalse(accountService.isNotExpired(token.getExpiryDateTime()));
    }

    /**
     * Test case to verify that a reset token is not expired.
     */
    @Test
    void TokenIsNotExpired() {
        // Create a new account
        Account account = new Account("Guest Guesterson", "123 Guest St", "1/2/3456", "guest@guest.guest", "123-456-7890",
                "user", "12");
        // Create a password reset token with an expiry date in the future
        PasswordResetToken token = new PasswordResetToken();
        LocalDateTime expiryDateTime = LocalDateTime.now().plusMinutes(2);
        token.setExpiryDateTime(expiryDateTime);
        // Ensure that the token is considered not expired
        assertTrue(accountService.isNotExpired(token.getExpiryDateTime()));
    }

    /**
     * Test case to verify that all admin accounts are retrieved successfully.
     */
    @Test
    void getAllAdminsRetrievesAllAdmins() {
        // Create a new admin account
        Admin admin = new Admin(true, 123456);
        // Create a list of accounts containing the admin
        List<Account> accounts = List.of(admin);
        // Mock the repository behavior to return the list of accounts
        when(accountRepository.findAll()).thenReturn(accounts);
        // Ensure that the list of admins is not null
        assertNotNull(accountService.getAllAdmins());
    }
    @Test
    public void SaveAccountShouldReturnSuccessAdmin(){
        Admin admin = new Admin(true, 123456);
        admin.setUserName("Admin");
        admin.setUserPassword("admin");
        admin.setId(1L);
        List<Account> accounts = List.of(admin);
        when(accountRepository.findAll()).thenReturn(accounts);
        Account account = accountRepository.save(admin);
        assertFalse(accountRepository.findAll().isEmpty());
    }
    @Test
    public void SaveAccountShouldReturnUnSuccessAdmin(){
        Admin admin = new Admin(true, 123456);
        admin.setUserName("Admin");
        admin.setUserPassword("admin");
        admin.setId(1L);
//        List<Account> accounts = List.of(admin);
//        when(accountRepository.findAll()).thenReturn(accounts);
////        Account account = accountRepository.save(admin);

        assertTrue(accountRepository.findAll().isEmpty());
    }
    @Test
    public void SaveAccountShouldReturnSuccessGuest(){
        Guest guest = new Guest("Guest Guesterson", "123 Guest St", "1/2/3456", "guest@guest.guest" ,"123-456-7890", "guest",
                "badPassword1", "1234567876543345678");
        guest.setId(1L);
        List<Account> accounts = List.of(guest);
        when(accountRepository.findAll()).thenReturn(accounts);
        Account account = accountRepository.save(guest);
        assertFalse(accountRepository.findAll().isEmpty());
    }
    @Test
    public void SaveAccountShouldReturnUnSuccessGuest(){
        Guest guest = new Guest("Guest Guesterson", "123 Guest St", "1/2/3456", "guest@guest.guest" ,"123-456-7890", "guest",
                "badPassword1", "1234567876543345678");
        guest.setId(1L);
//        List<Account> accounts = List.of(guest);
//        when(accountRepository.findAll()).thenReturn(accounts);

        assertTrue(accountRepository.findAll().isEmpty());
    }
    @Test
    public void UpdateAccountShouldReturnSuccessAdmin(){
        Admin admin = new Admin(true, 123456);
        admin.setUserName("Admin");
        admin.setUserPassword("admin");
        admin.setId(1L);
        List<Account> accounts = List.of(admin);
        when(accountRepository.findAll()).thenReturn(accounts);
        Account account = accountRepository.save(admin);
        admin.setId(2L);
        Account updatedAccount = accountRepository.save(admin);
        assertFalse(accountRepository.findAll().isEmpty());
    }

    @Test
    public void UpdateAccountShouldReturnUnSuccessGuest(){
        Guest guest = new Guest("Guest Guesterson", "123 Guest St", "1/2/3456", "guest@guest.guest" ,"123-456-7890", "guest",
                "badPassword1", "1234567876543345678");
        guest.setId(1L);
        List<Account> accounts = List.of(guest);
        when(accountRepository.findAll()).thenReturn(accounts);
        assertNotNull(accounts);
        guest.setId(2L);
        Account updatedAccount = accountRepository.save(guest);
        assertFalse(accountRepository.findAll().isEmpty());
    }

    @Test
    public void DeleteAccountShouldReturnSuccessAdmin(){
        Admin admin = new Admin(true, 123456);
        admin.setUserName("Admin");
        admin.setUserPassword("admin");
        admin.setId(1L);
        List<Account> accounts = List.of(admin);
        accountRepository.save(admin);
        assertNotNull(accounts);
        accountRepository.delete(admin);
        assertTrue(accountRepository.findAll().isEmpty());
    }
    @Test
    public void DeleteAccountShouldReturnSuccessGuest(){
        Guest guest = new Guest("Guest Guesterson", "123 Guest St", "1/2/3456", "guest@guest.guest" ,"123-456-7890", "guest",
                "badPassword1", "1234567876543345678");
        guest.setId(1L);
        List<Account> accounts = List.of(guest);
        accountRepository.save(guest);
        assertNotNull(accounts);
        accountRepository.delete(guest);
        assertTrue(accountRepository.findAll().isEmpty());
    }




}

