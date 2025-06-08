package com.movie.booking.system.repository;

import com.movie.booking.system.exception.InvalidBookingException;
import com.movie.booking.system.model.Booking;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class BookingRepositoryImpl implements BookingRepository {
    private final Map<String, Booking> bookingMap = new HashMap<>();

    @Override
    public void saveBooking(Booking booking) throws InvalidBookingException {
        if (Objects.isNull(booking)) {
            throw new InvalidBookingException("Booking cannot be null");
        }
        bookingMap.put(booking.getId(), booking);

    }

    @Override
    public Booking getBookingById(String id) throws InvalidBookingException {
        Booking booking = bookingMap.get(id);
        if (Objects.isNull(booking)) {
            throw new InvalidBookingException("Booking not found");
        }
        return booking;

    }

    @Override
    public List<Booking> getBookingsByUserId(String id) {
        return bookingMap.values().stream()
                .filter(booking -> booking.getUser() != null && booking.getUser().getId().equals(id))
                .toList();
    }
}
