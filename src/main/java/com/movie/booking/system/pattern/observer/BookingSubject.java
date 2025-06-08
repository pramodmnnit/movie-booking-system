package com.movie.booking.system.pattern.observer;

import com.movie.booking.system.model.Booking;

public interface BookingSubject {
    void registerObserver(BookingObserver observer);

    void removeObserver(BookingObserver observer);

    void notifyObservers(Booking booking, String eventType);
} 