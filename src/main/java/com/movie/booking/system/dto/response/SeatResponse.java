package com.movie.booking.system.dto.response;

import com.movie.booking.system.model.SeatStatus;
import com.movie.booking.system.model.SeatType;
import lombok.Data;

@Data
public class SeatResponse {
    private String seatId;
    private int seatNumber;
    private SeatType seatType;
    private double price;
    private SeatStatus status;
} 