package com.movie.booking.system.pattern.facade;

import com.movie.booking.system.dto.request.CreateBookingRequest;
import com.movie.booking.system.exception.*;
import com.movie.booking.system.model.*;
import com.movie.booking.system.notification.BookingNotificationManager;
import com.movie.booking.system.notification.EmailNotificationService;
import com.movie.booking.system.notification.SMSNotificationService;
import com.movie.booking.system.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingFacadeImplTest {

    @Mock
    private BookingService bookingService;

    @Mock
    private UserService userService;

    @Mock
    private ShowService showService;

    @Mock
    private SeatService seatService;

    @Mock
    private TheatreService theatreService;

    @Mock
    private ScreenService screenService;

    @InjectMocks
    private BookingFacadeImpl bookingFacade;

    private User testUser;
    private Show testShow;
    private List<Seat> testSeats;
    private CreateBookingRequest testRequest;
    private BookingNotificationManager notificationManager;
    private Theatre testTheatre;
    private Screen testScreen;

    @BeforeEach
    void setUp() {
        // Setup test user
        testUser = new User();
        testUser.setId("user1");
        testUser.setEmail("test@example.com");
        testUser.setPhone("1234567890");

        // Setup test show
        testShow = new Show();
        testShow.setId("show1");
        Movie movie = new Movie();
        movie.setName("Test Movie");
        testShow.setMovie(movie);

        // Setup test seats
        Seat seat1 = new Seat();
        seat1.setId("seat1");
        seat1.setSeatNumber(1);
        seat1.setStatus(SeatStatus.AVAILABLE);

        Seat seat2 = new Seat();
        seat2.setId("seat2");
        seat2.setSeatNumber(2);
        seat2.setStatus(SeatStatus.AVAILABLE);

        testSeats = Arrays.asList(seat1, seat2);

        // Setup test theatre
        testTheatre = new Theatre();
        testTheatre.setId("theatre1");
        testTheatre.setName("Test Theatre");

        // Setup test screen
        testScreen = new Screen();
        testScreen.setId("screen1");
        testScreen.setName("Test Screen");

        // Setup test request
        testRequest = new CreateBookingRequest();
        testRequest.setUserId("user1");
        testRequest.setShowId("show1");
        testRequest.setSeatIds(Arrays.asList("seat1", "seat2"));
        testRequest.setShowDate(new Date());
        testRequest.setTheatreId("theatre1");
        testRequest.setScreenId("screen1");

        // Initialize notification services and manager
        SMSNotificationService smsService = new SMSNotificationService();
        EmailNotificationService emailService = new EmailNotificationService();
        notificationManager = new BookingNotificationManager(smsService, emailService);

        // Set the notification manager in the facade
        bookingFacade.setNotificationManager(notificationManager);
    }

    @Test
    void createBookingSuccess() throws Exception {
        // Arrange
        when(userService.getUserById("user1")).thenReturn(testUser);
        when(showService.getShowById("show1")).thenReturn(testShow);
        when(seatService.getSeatsByIds(any())).thenReturn(testSeats);
        when(theatreService.getTheatreById("theatre1")).thenReturn(testTheatre);
        when(screenService.getScreenById("screen1")).thenReturn(testScreen);

        Booking booking = new Booking();
        booking.setUser(testUser);
        booking.setShow(testShow);
        booking.setTheatre(testTheatre);
        booking.setScreen(testScreen);
        when(bookingService.createBooking(any(), any(), any(), any(), any(), any())).thenReturn(booking);

        // Act
        Booking result = bookingFacade.createBooking(testRequest);

        // Assert
        assertNotNull(result);
        verify(theatreService).getTheatreById("theatre1");
        verify(screenService).getScreenById("screen1");
    }

    @Test
    void createBookingUserNotFound() {
        // Arrange
        when(userService.getUserById("user1")).thenReturn(null);

        // Act & Assert
        assertThrows(InvalidUserException.class, () -> bookingFacade.createBooking(testRequest));
    }

    @Test
    void createBookingShowNotFound() {
        // Arrange
        when(userService.getUserById("user1")).thenReturn(testUser);
        when(theatreService.getTheatreById("theatre1")).thenReturn(testTheatre);
        when(screenService.getScreenById("screen1")).thenReturn(testScreen);
        when(showService.getShowById("show1")).thenReturn(null);

        // Act & Assert
        assertThrows(InvalidShowException.class, () -> bookingFacade.createBooking(testRequest));
    }

    @Test
    void createBookingSeatsNotFound() {
        // Arrange
        when(userService.getUserById("user1")).thenReturn(testUser);
        when(showService.getShowById("show1")).thenReturn(testShow);
        when(theatreService.getTheatreById("theatre1")).thenReturn(testTheatre);
        when(screenService.getScreenById("screen1")).thenReturn(testScreen);
        when(seatService.getSeatsByIds(any())).thenReturn(Collections.singletonList(testSeats.get(0)));

        // Act & Assert
        assertThrows(InvalidSeatException.class, () -> bookingFacade.createBooking(testRequest));
    }

    @Test
    void createBookingTheatreNotFound() {
        // Arrange
        when(userService.getUserById("user1")).thenReturn(testUser);
        when(theatreService.getTheatreById("theatre1")).thenReturn(null);
        // Do not stub screenService or showService

        // Act & Assert
        assertThrows(InvalidTheatreException.class, () -> bookingFacade.createBooking(testRequest));
    }

    @Test
    void createBookingScreenNotFound() {
        // Arrange
        when(userService.getUserById("user1")).thenReturn(testUser);
        when(theatreService.getTheatreById("theatre1")).thenReturn(testTheatre);
        when(screenService.getScreenById("screen1")).thenReturn(null);
        // Do not stub showService

        // Act & Assert
        assertThrows(InvalidScreenException.class, () -> bookingFacade.createBooking(testRequest));
    }

    @Test
    void cancelBookingSuccess() throws Exception {
        // Arrange
        Booking booking = new Booking();
        booking.setId("booking1");
        booking.setUser(testUser);
        booking.setShow(testShow);
        booking.setTheatre(testTheatre);
        booking.setScreen(testScreen);
        when(bookingService.getBookingById("booking1")).thenReturn(booking);

        // Act
        bookingFacade.cancelBooking("booking1");

        // Assert
        verify(bookingService).cancelBooking("booking1");
    }

    @Test
    void cancelBookingNotFound() throws InvalidBookingException {
        // Arrange
        when(bookingService.getBookingById("booking1")).thenReturn(null);

        // Act & Assert
        assertThrows(InvalidBookingException.class, () -> bookingFacade.cancelBooking("booking1"));
    }

    @Test
    void getBookingDetailsSuccess() throws Exception {
        // Arrange
        Booking booking = new Booking();
        booking.setId("booking1");
        when(bookingService.getBookingById("booking1")).thenReturn(booking);

        // Act
        Booking result = bookingFacade.getBookingDetails("booking1");

        // Assert
        assertNotNull(result);
        assertEquals("booking1", result.getId());
    }

    @Test
    void getBookingDetailsNotFound() throws InvalidBookingException {
        // Arrange
        when(bookingService.getBookingById("booking1")).thenReturn(null);

        // Act & Assert
        assertThrows(InvalidBookingException.class, () -> bookingFacade.getBookingDetails("booking1"));
    }

    @Test
    void getUserBookingsSuccess() throws Exception {
        // Arrange
        List<Booking> bookings = Arrays.asList(
                new Booking(), new Booking()
        );
        when(userService.getUserById("user1")).thenReturn(testUser);
        when(bookingService.getBookingsByUserId(testUser)).thenReturn(bookings);

        // Act
        List<Booking> result = bookingFacade.getUserBookings("user1");

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void getUserBookingsUserNotFound() {
        // Arrange
        when(userService.getUserById("user1")).thenReturn(null);

        // Act & Assert
        assertThrows(InvalidUserException.class, () -> bookingFacade.getUserBookings("user1"));
    }
} 