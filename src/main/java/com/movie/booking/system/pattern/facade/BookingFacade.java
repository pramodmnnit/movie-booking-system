package com.movie.booking.system.pattern.facade;

import com.movie.booking.system.dto.request.CreateBookingRequest;
import com.movie.booking.system.exception.*;
import com.movie.booking.system.model.Booking;

import java.util.List;

public interface BookingFacade {
    Booking createBooking(CreateBookingRequest request) throws InvalidSeatException, InvalidShowException, InvalidUserException, InvalidBookingException, UnavailableSeatException, InvalidPricingException, InterruptedException, InvalidTheatreException, InvalidScreenException;

    void cancelBooking(String bookingId) throws InvalidBookingException, InvalidSeatException;

    Booking getBookingDetails(String bookingId) throws InvalidBookingException;

    List<Booking> getUserBookings(String userId) throws InvalidUserException, InvalidBookingException;

} 