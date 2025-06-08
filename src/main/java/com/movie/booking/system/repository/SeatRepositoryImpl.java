package com.movie.booking.system.repository;

import com.movie.booking.system.exception.InvalidSeatException;
import com.movie.booking.system.model.Seat;
import com.movie.booking.system.model.SeatType;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Repository
public class SeatRepositoryImpl implements SeatRepository {

    private final Map<String, Seat> seatMap = new HashMap<>();

    @Override
    public Seat getSeatById(String id) {
        return null;
    }

    @Override
    public Seat getSeatByStatus(String status) {
        return null;
    }

    @Override
    public Seat getSeatByType(SeatType type) {
        return null;
    }

    @Override
    public void saveSeat(Seat seat) throws InvalidSeatException {

        if (Objects.isNull(seat)) {
            throw new InvalidSeatException("Seat cannot be null");
        }

        seatMap.put(seat.getId(), seat);

    }

    @Override
    public List<Seat> getSeatsByIds(List<String> seatIds) {
        return seatIds.stream()
                .map(seatMap::get)
                .filter(Objects::nonNull)
                .toList();
    }
}
