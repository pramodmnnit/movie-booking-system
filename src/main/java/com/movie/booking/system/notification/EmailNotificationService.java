package com.movie.booking.system.notification;

import com.movie.booking.system.model.Booking;
import com.movie.booking.system.pattern.observer.BookingObserver;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationService implements BookingObserver {

    @Override
    public void update(Booking booking, String eventType) {
        String subject = buildSubject(eventType);
        String body = buildEmailBody(booking, eventType);
        sendEmail(booking.getUser().getEmail(), subject, body);
    }

    private String buildSubject(String eventType) {
        switch (eventType) {
            case "BOOKING_CREATED":
                return "Booking Confirmation - Movie Tickets";
            case "BOOKING_CANCELLED":
                return "Booking Cancellation Confirmation";
            default:
                return "Booking Update";
        }
    }

    private String buildEmailBody(Booking booking, String eventType) {
        StringBuilder body = new StringBuilder();
        body.append("<html><body>");
        body.append("<h2>Dear ").append(booking.getUser().getName()).append(",</h2>");

        switch (eventType) {
            case "BOOKING_CREATED":
                body.append("<p>Your booking has been confirmed. Here are the details:</p>");
                body.append("<ul>");
                body.append("<li>Booking ID: ").append(booking.getId()).append("</li>");
                body.append("<li>Movie: ").append(booking.getShow().getMovie().getName()).append("</li>");
                body.append("<li>Theater: ").append(booking.getTheatre().getName()).append("</li>");
                body.append("<li>Screen: ").append(booking.getScreen().getName()).append("</li>");
                body.append("<li>Show Date: ").append(booking.getShowDate()).append("</li>");
                body.append("<li>Total Amount: $").append(booking.getTotalAmount()).append("</li>");
                body.append("</ul>");
                body.append("<p>Thank you for choosing our service!</p>");
                break;
            case "BOOKING_CANCELLED":
                body.append("<p>Your booking has been cancelled. Here are the details:</p>");
                body.append("<ul>");
                body.append("<li>Booking ID: ").append(booking.getId()).append("</li>");
                body.append("<li>Movie: ").append(booking.getShow().getMovie().getName()).append("</li>");
                body.append("<li>Show Date: ").append(booking.getShowDate()).append("</li>");
                body.append("</ul>");
                body.append("<p>We hope to serve you again soon!</p>");
                break;
            default:
                body.append("<p>There is an update regarding your booking:</p>");
                body.append("<ul>");
                body.append("<li>Booking ID: ").append(booking.getId()).append("</li>");
                body.append("</ul>");
        }

        body.append("</body></html>");
        return body.toString();
    }

    private void sendEmail(String email, String subject, String body) {
        // TODO: Integrate with actual email service provider
        System.out.println("Sending email to " + email);
        System.out.println("Subject: " + subject);
        System.out.println("Body: " + body);
    }
} 