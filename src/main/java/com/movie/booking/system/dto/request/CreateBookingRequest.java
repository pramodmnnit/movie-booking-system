package com.movie.booking.system.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CreateBookingRequest {
    @NotNull(message = "User ID is required")
    private String userId;

    @NotNull(message = "Show ID is required")
    private String showId;

    @NotEmpty(message = "At least one seat must be selected")
    private List<String> seatIds;

    @NotNull(message = "Show date is required")
    private Date showDate;

    @NotEmpty(message = "Screen ID is required")
    private String screenId;

    @NotEmpty(message = "Theatre ID is required")
    private String theatreId;
} 