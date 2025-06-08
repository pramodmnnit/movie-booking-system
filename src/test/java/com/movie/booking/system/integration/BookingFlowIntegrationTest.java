package com.movie.booking.system.integration;

import com.movie.booking.system.dto.request.CreateBookingRequest;
import com.movie.booking.system.model.*;
import com.movie.booking.system.pattern.facade.BookingFacade;
import com.movie.booking.system.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookingFlowIntegrationTest {

    @Autowired
    private BookingFacade bookingFacade;

    @Autowired
    private UserService userService;

    @Autowired
    private ShowService showService;

    @Autowired
    private SeatService seatService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private PricingService pricingService;

    @Autowired
    private TheatreService theatreService;

    @Autowired
    private ScreenService screenService;

    private User testUser;
    private Show testShow;
    private List<Seat> testSeats;
    private CreateBookingRequest testRequest;
    private Theatre testTheatre;
    private Screen testScreen;

    @BeforeEach
    void setUp() {
        // Setup test user
        testUser = new User();
        testUser.setId("user1");
        testUser.setEmail("test@example.com");
        testUser.setPhone("1234567890");
        userService.saveUser(testUser);

        // Setup test theatre
        testTheatre = new Theatre();
        testTheatre.setId("theatre1");
        testTheatre.setName("Test Theatre");
        theatreService.saveTheatre(testTheatre);

        // Setup test screen
        testScreen = new Screen();
        testScreen.setId("screen1");
        testScreen.setName("Test Screen");
        testScreen.setShows(new java.util.ArrayList<>());
        screenService.saveScreen(testScreen);

        // Setup test show
        testShow = new Show();
        testShow.setId("show1");
        Movie movie = new Movie();
        movie.setName("Test Movie");
        testShow.setMovie(movie);
        testShow.setStartTime(15); // Set start time to 3 PM
        testShow.setEndTime(17);   // Set end time to 5 PM
        testShow.setDuration(2);   // Set duration to 2 hours

        showService.saveShow(testShow);

        // Setup test seats
        Seat seat1 = new Seat();
        seat1.setId("seat1");
        seat1.setSeatNumber(1);
        seat1.setStatus(SeatStatus.AVAILABLE);

        Seat seat2 = new Seat();
        seat2.setId("seat2");
        seat2.setSeatNumber(2);
        seat2.setStatus(SeatStatus.AVAILABLE);

        testSeats = new java.util.ArrayList<>();
        testSeats.add(seat1);
        testSeats.add(seat2);
        testShow.setSeats(new java.util.ArrayList<>(testSeats));
        testSeats.forEach(seat -> {
            try {
                seatService.saveSeat(seat);
            } catch (Exception e) {
                fail("Failed to save test seat: " + e.getMessage());
            }
        });

        // Setup pricing for the show
        try {
            org.joda.time.DateTime startDateTime = com.movie.booking.system.util.DateUtil.getDateTimeByDateAndTime(new Date(), testShow.getStartTime());
            org.joda.time.DateTime endDateTime = com.movie.booking.system.util.DateUtil.getDateTimeByDateAndTime(new Date(), testShow.getEndTime());

            Pricing pricingRegular = new Pricing();
            pricingRegular.setId("pricing-regular");
            pricingRegular.setSeatType(SeatType.REGULAR);
            pricingRegular.setAmount(100.0);
            pricingRegular.setStartTime(startDateTime);
            pricingRegular.setEndTime(endDateTime);
            pricingRegular.setShow(testShow);

            Pricing pricingPremium = new Pricing();
            pricingPremium.setId("pricing-premium");
            pricingPremium.setSeatType(SeatType.PREMIUM);
            pricingPremium.setAmount(200.0);
            pricingPremium.setStartTime(startDateTime);
            pricingPremium.setEndTime(endDateTime);
            pricingPremium.setShow(testShow);

            pricingService.savePricing(pricingRegular);
            pricingService.savePricing(pricingPremium);
        } catch (Exception e) {
            fail("Failed to save pricing: " + e.getMessage());
        }

        // Setup test request
        testRequest = new CreateBookingRequest();
        testRequest.setUserId("user1");
        testRequest.setShowId("show1");
        testRequest.setSeatIds(Arrays.asList("seat1", "seat2"));
        testRequest.setShowDate(new Date());
        testRequest.setTheatreId("theatre1");
        testRequest.setScreenId("screen1");
    }

    @Test
    void testCompleteBookingFlow() throws Exception {
        // 1. Create booking
        Booking booking = bookingFacade.createBooking(testRequest);
        assertNotNull(booking);
        assertEquals(BookingStatus.CONFIRMED, booking.getStatus());
        assertEquals(2, booking.getSeats().size());
        assertEquals(testTheatre.getId(), booking.getTheatre().getId());
        assertEquals(testScreen.getId(), booking.getScreen().getId());

        // 2. Get booking details
        Booking retrievedBooking = bookingFacade.getBookingDetails(booking.getId());
        assertNotNull(retrievedBooking);
        assertEquals(booking.getId(), retrievedBooking.getId());
        assertEquals(BookingStatus.CONFIRMED, retrievedBooking.getStatus());
        assertEquals(testTheatre.getId(), retrievedBooking.getTheatre().getId());
        assertEquals(testScreen.getId(), retrievedBooking.getScreen().getId());

        // 3. Get user bookings
        List<Booking> userBookings = bookingFacade.getUserBookings(testUser.getId());
        assertNotNull(userBookings);
        assertFalse(userBookings.isEmpty());
        assertEquals(2, userBookings.size());
        assertEquals(testTheatre.getId(), userBookings.get(0).getTheatre().getId());
        assertEquals(testScreen.getId(), userBookings.get(0).getScreen().getId());

        // 4. Cancel booking
        bookingFacade.cancelBooking(booking.getId());

        // 5. Verify seats are released
        List<Seat> seats = seatService.getSeatsByIds(Arrays.asList("seat1", "seat2"));
        seats.forEach(seat -> assertEquals(SeatStatus.AVAILABLE, seat.getStatus()));

        // 6. Verify booking status is cancelled
        Booking cancelledBooking = bookingFacade.getBookingDetails(booking.getId());
        assertEquals(BookingStatus.CANCELLED, cancelledBooking.getStatus());
    }

    @Test
    void testConcurrentBookingAttempts() throws Exception {
        // Create first booking
        Booking booking1 = bookingFacade.createBooking(testRequest);
        assertNotNull(booking1);
        assertEquals(testTheatre.getId(), booking1.getTheatre().getId());
        assertEquals(testScreen.getId(), booking1.getScreen().getId());

        // Try to book the same seats again
        assertThrows(Exception.class, () -> bookingFacade.createBooking(testRequest));

        // Verify only one booking was created
        List<Booking> userBookings = bookingFacade.getUserBookings(testUser.getId());
        assertEquals(1, userBookings.size());
    }

    @Test
    void testInvalidBookingAttempts() {
        // Test with non-existent user
        CreateBookingRequest invalidUserRequest = new CreateBookingRequest();
        invalidUserRequest.setUserId("nonExistentUser");
        invalidUserRequest.setShowId("show1");
        invalidUserRequest.setSeatIds(Arrays.asList("seat1", "seat2"));
        invalidUserRequest.setShowDate(new Date());
        invalidUserRequest.setTheatreId("theatre1");
        invalidUserRequest.setScreenId("screen1");

        assertThrows(Exception.class, () -> bookingFacade.createBooking(invalidUserRequest));

        // Test with non-existent theatre
        CreateBookingRequest invalidTheatreRequest = new CreateBookingRequest();
        invalidTheatreRequest.setUserId("user1");
        invalidTheatreRequest.setShowId("show1");
        invalidTheatreRequest.setSeatIds(Arrays.asList("seat1", "seat2"));
        invalidTheatreRequest.setShowDate(new Date());
        invalidTheatreRequest.setTheatreId("nonExistentTheatre");
        invalidTheatreRequest.setScreenId("screen1");

        assertThrows(Exception.class, () -> bookingFacade.createBooking(invalidTheatreRequest));

        // Test with non-existent screen
        CreateBookingRequest invalidScreenRequest = new CreateBookingRequest();
        invalidScreenRequest.setUserId("user1");
        invalidScreenRequest.setShowId("show1");
        invalidScreenRequest.setSeatIds(Arrays.asList("seat1", "seat2"));
        invalidScreenRequest.setShowDate(new Date());
        invalidScreenRequest.setTheatreId("theatre1");
        invalidScreenRequest.setScreenId("nonExistentScreen");

        assertThrows(Exception.class, () -> bookingFacade.createBooking(invalidScreenRequest));
    }
} 