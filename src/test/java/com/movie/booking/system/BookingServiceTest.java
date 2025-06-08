package com.movie.booking.system;

import com.movie.booking.system.exception.*;
import com.movie.booking.system.model.*;
import com.movie.booking.system.repository.*;
import com.movie.booking.system.service.*;
import com.movie.booking.system.util.DateUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookingServiceTest {

    private TheatreService theatreService;
    private ScreenService screenService;
    private BookingService bookingService;
    private PricingService pricingService;
    private SeatService seatService;
    private ShowService showService;
    private MovieService movieService;
    private User user;
    private Show show;
    private List<Seat> seats;
    private Date showDate;
    private Theatre theatre;
    private Screen screen;

    private static Movie getActiveMovie() {
        Movie activeMovie = new Movie();
        activeMovie.setId("1");
        activeMovie.setName("Inception");
        activeMovie.setDuration(148);
        activeMovie.setGenre("Sci-Fi");
        activeMovie.setReleaseDate("2010-07-16");
        activeMovie.setLanguage("English");
        activeMovie.setDescription("A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a CEO.");
        activeMovie.setStatus(MovieStatus.ACTIVE);
        return activeMovie;
    }

    @BeforeEach
    void setUp() throws InvalidPricingException, InvalidMovieException, InvalidSeatException {
        pricingService = new PricingServiceImpl(new PricingRepositoryImpl());
        UserService userService = new UserServiceImpl(new UserRepositoryImpl());
        seatService = new SeatServiceImpl(new SeatRepositoryImpl());
        showService = new ShowServiceImpl(new ShowRepositoryImpl());
        movieService = new MovieServiceImpl(new MovieRepositoryImpl());
        bookingService = new BookingServiceImpl(pricingService, new BookingRepositoryImpl(), seatService);
        theatreService = new TheatreServiceImpl(new TheatreRepositoryImpl());
        screenService = new ScreenServiceImpl(new ScreenRepositoryImpl());

        // Setup user
        user = new User();
        user.setId("1");
        user.setName("John Doe");
        user.setEmail("123@gmail.com");
        user.setPhone("1234567890");
        user.setUsername("username1");
        userService.saveUser(user);

        // Setup seats
        Seat seat1 = new Seat();
        seat1.setId("1");
        seat1.setType(SeatType.PREMIUM);
        seat1.setStatus(SeatStatus.AVAILABLE);
        seat1.setSeatNumber(1);

        Seat seat2 = new Seat();
        seat2.setId("2");
        seat2.setType(SeatType.REGULAR);
        seat2.setStatus(SeatStatus.AVAILABLE);
        seat2.setSeatNumber(2);

        Seat seat3 = new Seat();
        seat3.setId("3");
        seat3.setType(SeatType.REGULAR);
        seat3.setStatus(SeatStatus.AVAILABLE);
        seat3.setSeatNumber(3);

        Seat seat4 = new Seat();
        seat4.setId("4");
        seat4.setType(SeatType.PREMIUM);
        seat4.setStatus(SeatStatus.AVAILABLE);
        seat4.setSeatNumber(4);

        seats = Arrays.asList(seat1, seat2, seat3, seat4);
        for (Seat seat : seats) {
            seatService.saveSeat(seat);
        }

        // Setup show
        show = new Show();
        show.setId("1");
        show.setStartTime(15);
        show.setEndTime(17);
        show.setDuration(2);
        show.setSeats(seats);
        show.setLanguage("English");
        show.setFormat("2D");

        Movie activeMovie = getActiveMovie();
        show.setMovie(activeMovie);
        showDate = new Date();

        // Setup pricing
        Pricing pricing = new Pricing();
        pricing.setId("1");
        pricing.setSeatType(SeatType.PREMIUM);
        pricing.setAmount(200.0);
        pricing.setStartTime(DateUtil.getDateTimeByDateAndTime(showDate, show.getStartTime()));
        pricing.setEndTime(DateUtil.getDateTimeByDateAndTime(showDate, show.getEndTime()));
        pricing.setShow(show);

        Pricing pricing2 = new Pricing();
        pricing2.setId("2");
        pricing2.setSeatType(SeatType.REGULAR);
        pricing2.setAmount(100.0);
        pricing2.setStartTime(DateUtil.getDateTimeByDateAndTime(showDate, show.getStartTime()));
        pricing2.setEndTime(DateUtil.getDateTimeByDateAndTime(showDate, show.getEndTime()));
        pricing2.setShow(show);

        movieService.saveMovie(activeMovie);
        showService.saveShow(show);
        pricingService.savePricing(pricing);
        pricingService.savePricing(pricing2);

        screen = new Screen();
        screen.setName("Screen 1");
        screen.setId("1");
        screen.setShows(List.of(show));
        screenService.saveScreen(screen);
        theatre = new Theatre();
        theatre.setId("1");
        theatre.setName("Theatre 1");
        theatre.setScreens(List.of(screen));
        theatreService.saveTheatre(theatre);

    }

    @Test
    void createBooking() throws Exception {
        // Act
        Booking booking = bookingService.createBooking(user, show, Arrays.asList(seats.get(0), seats.get(2)), showDate, theatre, screen);

        // Assert
        assertNotNull(booking, "Booking should not be null");
        assertNotNull(booking.getId(), "Booking ID should not be null");
        assertNotNull(booking.getShow(), "Show should not be null");
        assertNotNull(booking.getSeats(), "Seats should not be null");
        assertNotNull(booking.getUser(), "User should not be null");
        assertNotNull(booking.getShowDate(), "Show date should not be null");
        assertNotNull(booking.getTotalAmount(), "Total amount should not be null");
        assertEquals(2, booking.getSeats().size(), "Should have 2 seats");
        assertEquals(360.0, booking.getAmount(), "Base amount should be 360");
        assertEquals(424.8, booking.getTotalAmount(), "Total amount with tax should be 424.8");
        assertEquals(BookingStatus.CONFIRMED, booking.getStatus(), "Booking status should be CONFIRMED");
    }

    @Test
    void createBookingWithUnavailableSeats() {
        // First booking to make seats unavailable
        assertDoesNotThrow(() -> bookingService.createBooking(user, show, seats, showDate, theatre, screen),
                "Initial booking should be created successfully");

        // Try to book the same seats again
        UnavailableSeatException exception = assertThrows(UnavailableSeatException.class,
                () -> bookingService.createBooking(user, show, Arrays.asList(seats.get(0), seats.get(1)), showDate, theatre, screen),
                "Booking unavailable seats should throw UnavailableSeatException");
        assertEquals("Seats are not available", exception.getMessage(), "Exception message should match");
    }

    @Test
    void cancelBooking() throws Exception {
        // Arrange
        Booking booking = bookingService.createBooking(user, show, Arrays.asList(seats.get(0), seats.get(2)), showDate, theatre, screen);
        assertNotNull(booking, "Initial booking should be created successfully");

        // Act
        Booking cancelledBooking = bookingService.cancelBooking(booking.getId());

        // Assert
        assertNotNull(cancelledBooking, "Cancelled booking should not be null");
        assertEquals(BookingStatus.CANCELLED, cancelledBooking.getStatus(), "Booking status should be CANCELLED");
        assertEquals(booking.getId(), cancelledBooking.getId(), "Booking ID should remain the same");

        // Verify seats are available again
        for (Seat seat : cancelledBooking.getSeats()) {
            assertEquals(SeatStatus.AVAILABLE, seat.getStatus(), "Seat should be available after cancellation");
        }
    }

    @Test
    void cancelNonExistentBooking() {
        // Act & Assert
        assertThrows(InvalidBookingException.class,
                () -> bookingService.cancelBooking("non-existent-id"),
                "Cancelling non-existent booking should throw UnavailableSeatException");
    }
}
