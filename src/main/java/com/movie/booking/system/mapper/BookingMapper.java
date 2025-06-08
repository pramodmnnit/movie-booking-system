package com.movie.booking.system.mapper;

import com.movie.booking.system.dto.response.BookingResponse;
import com.movie.booking.system.dto.response.SeatResponse;
import com.movie.booking.system.model.Booking;
import com.movie.booking.system.model.Seat;
import com.movie.booking.system.util.DateUtil;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookingMapper {

    public BookingResponse toResponse(Booking booking) {
        BookingResponse response = new BookingResponse();
        response.setBookingId(booking.getId());
        response.setUserId(booking.getUser().getId());
        response.setShowId(booking.getShow().getId());
        response.setSeats(toSeatResponses(booking.getSeats()));
        response.setTotalAmount(booking.getTotalAmount());
        response.setStatus(booking.getStatus());
        response.setBookingDate(DateUtil.getDateByDateTime(booking.getCreatedAt()));
        response.setShowDate(DateUtil.getDateByDateTime(booking.getShowDate()));
        response.setMovieName(booking.getShow().getMovie().getName());
        response.setTheaterName(booking.getTheatre().getName());
        response.setScreenName(booking.getScreen().getName());
        return response;
    }

    public List<BookingResponse> toResponseList(List<Booking> bookings) {
        return bookings.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private List<SeatResponse> toSeatResponses(List<Seat> seats) {
        return seats.stream()
                .map(this::toSeatResponse)
                .collect(Collectors.toList());
    }

    private SeatResponse toSeatResponse(Seat seat) {
        SeatResponse response = new SeatResponse();
        response.setSeatId(seat.getId());
        response.setSeatNumber(seat.getSeatNumber());
        response.setSeatType(seat.getType());
        response.setPrice(seat.getPrice());
        response.setStatus(seat.getStatus());
        return response;
    }
} 