package com.movie.booking.system.notification;

import com.movie.booking.system.model.Booking;
import com.movie.booking.system.pattern.observer.BookingObserver;
import org.springframework.stereotype.Service;

@Service
public class SMSNotificationService implements BookingObserver {

    @Override
    public void update(Booking booking, String eventType) {
        String message = buildMessage(booking, eventType);
        sendSMS(booking.getUser().getPhone(), message);
    }

    private String buildMessage(Booking booking, String eventType) {
        StringBuilder message = new StringBuilder();
        message.append("Dear ").append(booking.getUser().getName()).append(",\n");

        switch (eventType) {
            case "BOOKING_CREATED":
                message.append("Your booking has been confirmed.\n");
                message.append("Booking ID: ").append(booking.getId()).append("\n");
                message.append("Movie: ").append(booking.getShow().getMovie().getName()).append("\n");
                message.append("Show Date: ").append(booking.getShowDate()).append("\n");
                message.append("Total Amount: $").append(booking.getTotalAmount());
                break;
            case "BOOKING_CANCELLED":
                message.append("Your booking has been cancelled.\n");
                message.append("Booking ID: ").append(booking.getId()).append("\n");
                message.append("Movie: ").append(booking.getShow().getMovie().getName()).append("\n");
                message.append("Show Date: ").append(booking.getShowDate());
                break;
            default:
                message.append("There is an update regarding your booking.\n");
                message.append("Booking ID: ").append(booking.getId());
        }

        return message.toString();
    }

    private void sendSMS(String phoneNumber, String message) {
        // TODO: Integrate with actual SMS service provider
        System.out.println("Sending SMS to " + phoneNumber + ": " + message);
    }
} 