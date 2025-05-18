package com.movie.booking.system.repository;

import com.movie.booking.system.exception.InvalidBookingException;
import com.movie.booking.system.model.Booking;

public interface BookingRepository {
    void saveBooking(Booking booking) throws InvalidBookingException;

    Booking getBookingById(String id) throws InvalidBookingException;
}
