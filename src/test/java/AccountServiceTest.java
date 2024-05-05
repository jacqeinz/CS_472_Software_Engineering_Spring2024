
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


import java.time.LocalDateTime;
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
import spring2024.cs472.hotelwebsite.entities.Account;
import spring2024.cs472.hotelwebsite.entities.Admin;
import spring2024.cs472.hotelwebsite.entities.Guest;
import spring2024.cs472.hotelwebsite.repositories.AccountRepository;
import spring2024.cs472.hotelwebsite.repositories.TokenRepository;
import spring2024.cs472.hotelwebsite.services.AccountService;
import spring2024.cs472.hotelwebsite.services.ResetPasswordService;
import org.springframework.ui.Model;
import spring2024.cs472.hotelwebsite.entities.PasswordResetToken;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = HotelWebsiteApplication.class)
@AutoConfigureMockMvc
class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ResetPasswordService resetPasswordService;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private Model model;
    @Autowired
    private TokenRepository tokenRepository;


//    @Before
//    public void setUp() {
//    }

//    @Test
//    void showUpdateFormGuest() {
//    }

    @Test
    public void validateLoginShouldReturnNull() {
        Guest guest = new Guest("Guest Guesterson", "123 Guest St", "1/2/3456", "guest@guest.guest" ,"123-456-7890", "guest",
                "badPassword1", "1234567876543345678");
        guest.setId(1L);
        List<Account> accounts = List.of(guest);
        when(accountRepository.findAll()).thenReturn(accounts);

        Account account = accountService.validateLogin("guest", "wrongPassword2");

        assertNull(account);

    }

    @Test
    public void validateLoginShouldReturnSuccess(){
        Guest guest = new Guest("Guest Guesterson", "123 Guest St", "1/2/3456", "guest@guest.guest" ,"123-456-7890", "guest",
                "badPassword1", "1234567876543345678");
        guest.setId(1L);
        List<Account> accounts = List.of(guest);
        when(accountRepository.findAll()).thenReturn(accounts);
        Account account = accountService.validateLogin("guest", "badPassword1");
        assertNotNull(account);
    }

    @Test
    public void validateLoginShouldReturnSuccessAdmin(){
        Admin admin = new Admin(true, 123456);
        admin.setUserName("Admin");
        admin.setUserPassword("admin");
        admin.setId(1L);
        List<Account> accounts = List.of(admin);
        when(accountRepository.findAll()).thenReturn(accounts);
        Account account = accountService.validateLogin("Admin", "admin");
        assertNotNull(account);
    }

    @Test
    public void validateLoginShouldReturnNullAdmin(){
        Admin admin = new Admin(true, 123456);
        admin.setUserName("Admin");
        admin.setUserPassword("admin");
        admin.setId(1L);
        List<Account> accounts = List.of(admin);
        when(accountRepository.findAll()).thenReturn(accounts);
        Account account = accountService.validateLogin("admin", "incorrectPassword1");
        assertNull(account);
    }

    @Test
    public void resetPasswordEmailIsSent(){
        Account account = new Account("Guest Guesterson", "123 Guest St", "1/2/3456", "guest@guest.guest" ,"123-456-7890",
                "user", "12");
        assertEquals("Success", accountService.sendEmail(account));

    }

    @Test
    public void resetTokenCreatedSuccessfully(){
        Account account = new Account("Guest Guesterson", "123 Guest St", "1/2/3456", "guest@guest.guest" ,"123-456-7890", "guest",
                "badPassword1", "1234567876543345678");
        String result = accountService.generateResetToken(account);
        assertNotNull(result);
    }

    @Test
    public void accountPasswordChangedAfterReset(){
        Account account = new Account("Guest Guesterson", "123 Guest St", "1/2/3456", "guest@guest.guest" ,"123-456-7890",
                "user", "12");
        accountRepository.save(account);
        String result = accountService.generateResetToken(account);
        when(accountRepository.findByEmail("guest@guest.guest")).thenReturn(account);
        resetPasswordService.resetPasswordProcess(result, "1234", "guest@guest.guest");
        assertEquals("1234", account.getUserPassword());
    }

    @Test void TokenIsExpired(){
        Account account = new Account("Guest Guesterson", "123 Guest St", "1/2/3456", "guest@guest.guest" ,"123-456-7890",
                "user", "12");
        PasswordResetToken token = new PasswordResetToken();
        LocalDateTime expiryDateTime = LocalDateTime.now().minusMinutes(2);
        token.setExpiryDateTime(expiryDateTime);
        assertFalse(accountService.isNotExpired(token.getExpiryDateTime()));

    }
    @Test void TokenIsNotExpired() {
        Account account = new Account("Guest Guesterson", "123 Guest St", "1/2/3456", "guest@guest.guest", "123-456-7890",
                "user", "12");
        PasswordResetToken token = new PasswordResetToken();
        LocalDateTime expiryDateTime = LocalDateTime.now().plusMinutes(2);
        token.setExpiryDateTime(expiryDateTime);
        assertTrue(accountService.isNotExpired(token.getExpiryDateTime()));
    }

    @Test void getAllAdminsRetrievesAllAdmins(){
        Admin admin = new Admin(true, 123456);
        List<Account> accounts = List.of(admin);
        when(accountRepository.findAll()).thenReturn(accounts);
        assertNotNull(accountService.getAllAdmins());
    }



}

