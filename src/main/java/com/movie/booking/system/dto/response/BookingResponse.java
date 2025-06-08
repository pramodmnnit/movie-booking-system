package com.movie.booking.system.dto.response;

import com.movie.booking.system.model.BookingStatus;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BookingResponse {
    private String bookingId;
    private String userId;
    private String showId;
    private List<SeatResponse> seats;
    private double totalAmount;
    private BookingStatus status;
    private Date bookingDate;
    private Date showDate;
    private String movieName;
    private String theaterName;
    private String screenName;
} 