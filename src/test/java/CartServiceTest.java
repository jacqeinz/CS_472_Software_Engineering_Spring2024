
import static org.apache.coyote.http11.Constants.a;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
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

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = HotelWebsiteApplication.class)
@AutoConfigureMockMvc
class CartServiceTest {

    @Autowired
    private CartService cartService;

    @MockBean
    private RoomReservationRepository roomReservationRepository;

    @MockBean
    private AccountService accountService;

    @MockBean
    private ReservationDetailsRepository reservationDetailsRepository;

    public void addRoomReservationReturnSuccess() {

        Guest guest = new Guest("Guest Guesterson", "123 Guest St", "1/2/3456", "guest@guest.guest" ,"123-456-7890", "guest",
                "badPassword1", "1234567876543345678");
        guest.setId(1L);
        List<Account> accounts = List.of(guest);
        LocalDate start = LocalDate.now().plusDays(2);
        LocalDate end = LocalDate.now().plusDays(5);
        Room room = new Room("505", "Deluxe", 200, 5);
        RoomReservation returnedRoomReservation;

        when(roomReservationRepository.save(any(RoomReservation.class))).then(AdditionalAnswers.returnsFirstArg());

        cartService.addRoomReservations(guest.getCart(), List.of(room), start, end);





    }

}
