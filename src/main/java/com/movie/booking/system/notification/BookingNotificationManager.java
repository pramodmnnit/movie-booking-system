package com.movie.booking.system.notification;

import com.movie.booking.system.model.Booking;
import com.movie.booking.system.pattern.observer.BookingObserver;
import com.movie.booking.system.pattern.observer.BookingSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookingNotificationManager implements BookingSubject {

    private final List<BookingObserver> observers;
    private final SMSNotificationService smsNotificationService;
    private final EmailNotificationService emailNotificationService;

    @Autowired
    public BookingNotificationManager(
            SMSNotificationService smsNotificationService,
            EmailNotificationService emailNotificationService) {
        this.observers = new ArrayList<>();
        this.smsNotificationService = smsNotificationService;
        this.emailNotificationService = emailNotificationService;

        // Register default observers
        registerObserver(smsNotificationService);
        registerObserver(emailNotificationService);
    }

    @Override
    public void registerObserver(BookingObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void removeObserver(BookingObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(Booking booking, String eventType) {
        for (BookingObserver observer : observers) {
            observer.update(booking, eventType);
        }
    }

    public void notifyBookingCreated(Booking booking) {
        notifyObservers(booking, "BOOKING_CREATED");
    }

    public void notifyBookingCancelled(Booking booking) {
        notifyObservers(booking, "BOOKING_CANCELLED");
    }
} 