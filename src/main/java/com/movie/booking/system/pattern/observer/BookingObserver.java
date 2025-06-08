package com.movie.booking.system.pattern.observer;

import com.movie.booking.system.model.Booking;
 
public interface BookingObserver {
    void update(Booking booking, String eventType);
} 