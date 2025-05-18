package com.movie.booking.system;

import com.movie.booking.system.exception.UnavailableSeatException;
import com.movie.booking.system.model.*;
import com.movie.booking.system.repository.*;
import com.movie.booking.system.service.*;
import com.movie.booking.system.util.DateUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BookingServiceTest {

    private BookingService bookingService;
    private PricingService pricingService;
    private BookingRepository bookingRepository;
    private SeatService seatService;
    private ShowService showService;
    private MovieService movieService;
    private User user;

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
    void setUp() {
        pricingService = new PricingServiceImpl(new PricingRepositoryImpl());
        UserService userService = new UserServiceImpl(new UserRepositoryImpl());
        seatService = new SeatServiceImpl(new SeatRepositoryImpl());
        showService = new ShowServiceImpl(new ShowRepositoryImpl());
        movieService = new MovieServiceImpl(new MovieRepositoryImpl());
        bookingService = new BookingServiceImpl(pricingService, new BookingRepositoryImpl(), seatService);
        user = new User();
        user.setId("1");
        user.setName("John Doe");
        user.setEmail("123@gmail.com");
        user.setPhone("1234567890");
        user.setUsername("username1");
        userService.saveUser(user);

    }

    @Test
    void createBooking() throws Exception {
        // Arrange
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
        List<Seat> seats = Arrays.asList(seat1, seat2, seat3, seat4);
        for (Seat seat : seats) {
            seatService.saveSeat(seat);
        }

        Show show = new Show();
        show.setId("1");
        show.setStartTime(15);
        show.setEndTime(17);
        show.setDuration(2);
        show.setSeats(seats);
        show.setLanguage("English");
        show.setFormat("2D");

        Movie activeMovie = getActiveMovie();
        show.setMovie(activeMovie);
        Date showDate = new Date();

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

        // Act
        Booking booking = bookingService.createBooking(user, show, Arrays.asList(seat1, seat3), showDate);

        // Assert
        assertNotNull(booking);
        assertNotNull(booking.getId());
        assertNotNull(booking.getShow());
        assertNotNull(booking.getSeats());
        assertNotNull(booking.getUser());
        assertNotNull(booking.getShowDate());
        assertNotNull(booking.getTotalAmount());
        assertEquals(2, booking.getSeats().size());
        assertEquals(360, booking.getAmount());
        assertEquals(424.8, booking.getTotalAmount());
        assertEquals(BookingStatus.CONFIRMED, booking.getStatus());

    }

    @Test
    void createBookings() throws Exception {
        // Arrange
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
        List<Seat> seats = Arrays.asList(seat1, seat2, seat3, seat4);
        for (Seat seat : seats) {
            seatService.saveSeat(seat);
        }

        Show show = new Show();
        show.setId("1");
        show.setStartTime(15);
        show.setEndTime(17);
        show.setDuration(2);
        show.setSeats(seats);
        show.setLanguage("English");
        show.setFormat("2D");

        Movie activeMovie = getActiveMovie();
        show.setMovie(activeMovie);
        Date showDate = new Date();

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
        bookingService.createBooking(user, show, seats, showDate);
        try {
            Booking booking1 = bookingService.createBooking(user, show, Arrays.asList(seat1, seat2), showDate);
        } catch (UnavailableSeatException ex) {
            assertEquals("Seats are not available", ex.getMessage());
        }
        // Assert

    }

    @Test
    void cancelBooking() throws Exception {
        // Arrange
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
        List<Seat> seats = Arrays.asList(seat1, seat2, seat3, seat4);
        for (Seat seat : seats) {
            seatService.saveSeat(seat);
        }

        Show show = new Show();
        show.setId("1");
        show.setStartTime(15);
        show.setEndTime(17);
        show.setDuration(2);
        show.setSeats(seats);
        show.setLanguage("English");
        show.setFormat("2D");

        Movie activeMovie = getActiveMovie();
        show.setMovie(activeMovie);
        Date showDate = new Date();

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

        Booking booking = bookingService.createBooking(user, show, Arrays.asList(seat1, seat3), showDate);
        assertNotNull(booking);
        assertEquals(BookingStatus.CONFIRMED, booking.getStatus());
        assertEquals(2, booking.getSeats().size());
        bookingService.cancelBooking(booking.getId());
        booking = bookingService.getBookingById(booking.getId());
        assertEquals(BookingStatus.CANCELLED, booking.getStatus());


    }
}
