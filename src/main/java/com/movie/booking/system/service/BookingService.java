package com.movie.booking.system.service;

import com.movie.booking.system.exception.InvalidBookingException;
import com.movie.booking.system.exception.InvalidPricingException;
import com.movie.booking.system.exception.InvalidSeatException;
import com.movie.booking.system.exception.UnavailableSeatException;
import com.movie.booking.system.model.Booking;
import com.movie.booking.system.model.Seat;
import com.movie.booking.system.model.Show;
import com.movie.booking.system.model.User;

import java.util.Date;
import java.util.List;

public interface BookingService {
    Booking createBooking(User user, Show show, List<Seat> seats, Date showDate) throws UnavailableSeatException, InvalidPricingException, InvalidBookingException, InvalidSeatException, InterruptedException;

    void cancelBooking(String bookingId) throws InvalidBookingException, InvalidSeatException;
}
