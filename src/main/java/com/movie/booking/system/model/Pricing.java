package com.movie.booking.system.model;

import lombok.Data;
import org.joda.time.DateTime;

@Data
public class Pricing {
    private String id;
    private Double amount;
    private String currency;
    private SeatType seatType;
    private DateTime startTime;
    private DateTime endTime;
    private Show show;
}
