package com.movie.booking.system.repository;

import com.movie.booking.system.exception.InvalidSeatException;
import com.movie.booking.system.model.Seat;
import com.movie.booking.system.model.SeatType;

public interface SeatRepository {
    Seat getSeatById(String id);

    Seat getSeatByStatus(String status);

    Seat getSeatByType(SeatType type);

    void saveSeat(Seat seat) throws InvalidSeatException;

}
