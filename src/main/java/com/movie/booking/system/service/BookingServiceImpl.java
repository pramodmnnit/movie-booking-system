package com.movie.booking.system.service;

import com.movie.booking.system.exception.InvalidBookingException;
import com.movie.booking.system.exception.InvalidPricingException;
import com.movie.booking.system.exception.InvalidSeatException;
import com.movie.booking.system.exception.UnavailableSeatException;
import com.movie.booking.system.model.*;
import com.movie.booking.system.repository.BookingRepository;
import com.movie.booking.system.util.DateUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class BookingServiceImpl implements BookingService {

    private final PricingService pricingService;
    private final BookingRepository bookingRepository;
    private final SeatService seatService;


    public BookingServiceImpl(PricingService pricingService,
                              BookingRepository bookingRepository,
                              SeatService seatService) {
        this.pricingService = pricingService;
        this.bookingRepository = bookingRepository;
        this.seatService = seatService;
    }

    @Override
    public Booking createBooking(User user, Show show, List<Seat> seats, Date showDate, Theatre theatre, Screen screen) throws UnavailableSeatException, InvalidPricingException, InvalidBookingException, InvalidSeatException, InterruptedException {
        List<ReentrantLock> reentrantLocks = new ArrayList<>();
        Booking booking = null;
        try {
            reentrantLocks = seatService.getSeatLocks(seats, show, showDate);
            seatService.validateSeats(seats);
            DateTime startDateTime = DateUtil.getDateTimeByDateAndTime(showDate, show.getStartTime());
            Double totalAmount;
            double amount = 0.0;
            for (Seat seat : seats) {
                double price = pricingService.calculatePriceWithStrategy(seat, show, startDateTime);
                seat.setPrice(price);
                amount += price;
            }
            totalAmount = amount;
            Double taxAmount = pricingService.getTaxAmount(totalAmount);
            try {
                seatService.bookSeats(seats);
                booking = new Booking();
                booking.setId(UUID.randomUUID().toString());
                booking.setShow(show);
                booking.setAmount(totalAmount);
                booking.setSeats(seats);
                booking.setUser(user);
                booking.setShowDate(startDateTime);
                booking.setTax(taxAmount);
                booking.setCreatedAt(new DateTime());
                booking.setUpdatedAt(new DateTime());
                booking.setTotalAmount(pricingService.getTotalAmount(totalAmount, taxAmount));
                booking.setStatus(BookingStatus.CONFIRMED);
                booking.setTheatre(theatre);
                booking.setScreen(screen);
                bookingRepository.saveBooking(booking);
            } catch (Exception e) {
                if (booking != null) {
                    seatService.releaseSeats(seats);
                }
                throw new InvalidSeatException("Seat allocation failed and exception:" + e.getMessage());
            }
        } finally {
            for (ReentrantLock lock : reentrantLocks) {
                lock.unlock();
            }
        }
        return booking;
    }


    @Override
    public Booking cancelBooking(String bookingId) throws InvalidBookingException, InvalidSeatException {
        Booking booking = bookingRepository.getBookingById(bookingId);
        if (Objects.isNull(booking)) {
            throw new InvalidBookingException(String.format("Booking not found for the given booking id %s", bookingId));
        }
        List<Seat> seats = booking.getSeats();
        for (Seat seat : seats) {
            seat.setStatus(SeatStatus.AVAILABLE);
            seatService.saveSeat(seat);
        }

        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.saveBooking(booking);
        return booking;
    }

    @Override
    public Booking getBookingById(String bookingId) throws InvalidBookingException {
        return bookingRepository.getBookingById(bookingId);
    }

    @Override
    public List<Booking> getBookingsByUserId(User user) {
        return bookingRepository.getBookingsByUserId(user.getId());
    }
}
