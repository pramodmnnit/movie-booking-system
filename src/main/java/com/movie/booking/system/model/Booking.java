package com.movie.booking.system.model;

import lombok.Data;
import org.joda.time.DateTime;

import java.util.List;

@Data
public class Booking {

    private String id;
    private User user;
    private BookingStatus status;
    private Show show;
    private DateTime showDate;
    private List<Seat> seats;
    private Double amount;
    private Double tax;
    private Double totalAmount;
    private DateTime cratedAt;
    private DateTime updatedAt;
}
