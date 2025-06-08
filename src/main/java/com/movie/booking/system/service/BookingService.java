package com.movie.booking.system.service;

import com.movie.booking.system.exception.InvalidBookingException;
import com.movie.booking.system.exception.InvalidPricingException;
import com.movie.booking.system.exception.InvalidSeatException;
import com.movie.booking.system.exception.UnavailableSeatException;
import com.movie.booking.system.model.*;

import java.util.Date;
import java.util.List;

public interface BookingService {
    Booking createBooking(User user, Show show, List<Seat> seats, Date showDate, Theatre theatre, Screen screen) throws UnavailableSeatException, InvalidPricingException, InvalidBookingException, InvalidSeatException, InterruptedException;

    Booking cancelBooking(String bookingId) throws InvalidBookingException, InvalidSeatException;

    Booking getBookingById(String bookingId) throws InvalidBookingException;

    List<Booking> getBookingsByUserId(User user) throws InvalidBookingException;
}
