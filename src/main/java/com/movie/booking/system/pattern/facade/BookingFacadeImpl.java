package com.movie.booking.system.pattern.facade;

import com.movie.booking.system.dto.request.CreateBookingRequest;
import com.movie.booking.system.exception.*;
import com.movie.booking.system.event.EventPublisher;
import com.movie.booking.system.event.EventPublisherFactory;
import com.movie.booking.system.model.*;
import com.movie.booking.system.notification.BookingNotificationManager;
import com.movie.booking.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingFacadeImpl implements BookingFacade {

    private final BookingService bookingService;
    private final UserService userService;
    private final ShowService showService;
    private final SeatService seatService;
    private final ScreenService screenService;
    private final TheatreService theatreService;
    private BookingNotificationManager notificationManager;
    private final EventPublisher eventPublisher;

    @Autowired
    public BookingFacadeImpl(
            BookingService bookingService,
            UserService userService,
            ShowService showService,
            SeatService seatService,
            ScreenService screenService,
            TheatreService theatreService,
            BookingNotificationManager notificationManager
    ) {
        this.bookingService = bookingService;
        this.userService = userService;
        this.showService = showService;
        this.seatService = seatService;
        this.screenService = screenService;
        this.theatreService = theatreService;
        this.notificationManager = notificationManager;
        // Initialize Kafka publisher - in a real application, this would be configured via properties
        this.eventPublisher = EventPublisherFactory.createPublisher(
                EventPublisherFactory.PublisherType.KAFKA,
                "localhost:9092"
        );
    }

    @Override
    public Booking createBooking(CreateBookingRequest request) throws InvalidSeatException, InvalidShowException, InvalidUserException, InvalidBookingException, UnavailableSeatException, InvalidPricingException, InterruptedException, InvalidTheatreException, InvalidScreenException {
        // Validate user
        User user = userService.getUserById(request.getUserId());
        if (user == null) {
            throw new InvalidUserException("User not found");
        }
        Theatre theatre = theatreService.getTheatreById(request.getTheatreId());
        if (theatre == null) {
            throw new InvalidTheatreException("Theatre not found");
        }
        Screen screen = screenService.getScreenById(request.getScreenId());
        if (screen == null) {
            throw new InvalidScreenException("Screen not found");
        }
        // Validate show
        Show show = showService.getShowById(request.getShowId());
        if (show == null) {
            throw new InvalidShowException("Show not found");
        }

        // Validate seats
        List<Seat> seats = seatService.getSeatsByIds(request.getSeatIds());
        if (seats.size() != request.getSeatIds().size()) {
            throw new InvalidSeatException("One or more seats not found");
        }

        seatService.validateSeats(seats);

        // Create booking
        Booking booking = bookingService.createBooking(user, show, seats, request.getShowDate(), theatre, screen);

        // Send notifications
        notificationManager.notifyBookingCreated(booking);

        // Publish booking created event
        String eventMessage = String.format(
                "{\"event\":\"BOOKING_CREATED\",\"bookingId\":\"%s\",\"userId\":\"%s\",\"theatreId\":\"%s\",\"showId\":\"%s\"}",
                booking.getId(), request.getUserId(), request.getTheatreId(), request.getShowId()
        );
        eventPublisher.publish("booking-events", eventMessage);

        return booking;
    }

    @Override
    public void cancelBooking(String bookingId) throws InvalidBookingException, InvalidSeatException {
        Booking booking = bookingService.getBookingById(bookingId);
        if (booking == null) {
            throw new InvalidBookingException("Booking not found");
        }

        bookingService.cancelBooking(bookingId);

        // Send notifications
        notificationManager.notifyBookingCancelled(booking);

        // Publish booking cancelled event
        String eventMessage = String.format(
                "{\"event\":\"BOOKING_CANCELLED\",\"bookingId\":\"%s\",\"userId\":\"%s\"}",
                booking.getId(), booking.getUser().getId()
        );
        eventPublisher.publish("booking-events", eventMessage);
    }

    @Override
    public Booking getBookingDetails(String bookingId) throws InvalidBookingException {
        Booking booking = bookingService.getBookingById(bookingId);
        if (booking == null) {
            throw new InvalidBookingException("Booking not found");
        }
        return booking;
    }

    @Override
    public List<Booking> getUserBookings(String userId) throws InvalidUserException, InvalidBookingException {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new InvalidUserException("User not found");
        }
        return bookingService.getBookingsByUserId(user);
    }

    // Setter for testing purposes
    void setNotificationManager(BookingNotificationManager notificationManager) {
        this.notificationManager = notificationManager;
    }
} 