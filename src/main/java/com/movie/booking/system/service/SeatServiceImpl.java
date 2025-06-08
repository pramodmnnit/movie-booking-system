package com.movie.booking.system.service;

import com.movie.booking.system.exception.InvalidSeatException;
import com.movie.booking.system.exception.UnavailableSeatException;
import com.movie.booking.system.model.Seat;
import com.movie.booking.system.model.SeatStatus;
import com.movie.booking.system.model.Show;
import com.movie.booking.system.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;


@Service
public class SeatServiceImpl implements SeatService {

    private final SeatRepository repository;

    public SeatServiceImpl(SeatRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveSeat(Seat seat) throws InvalidSeatException {
        repository.saveSeat(seat);
    }

    @Override
    public List<ReentrantLock> getSeatLocks(List<Seat> seats, Show show, Date showDate) throws UnavailableSeatException, InterruptedException {
        List<ReentrantLock> reentrantLocks = new ArrayList<>();
        List<Seat> mutableSeats = new ArrayList<>(seats);
        mutableSeats.sort(Comparator.comparingInt(Seat::getSeatNumber));
        for (Seat seat : mutableSeats) {
            ReentrantLock lock = seat.lockSeat(showDate, show.getId());
            if (lock.tryLock(2, TimeUnit.SECONDS)) {
                reentrantLocks.add(lock);
            } else {
                throw new UnavailableSeatException("Seats are not available");
            }
        }
        return reentrantLocks;
    }

    @Override
    public List<Seat> getSeatsByIds(List<String> seatIds) {
        return repository.getSeatsByIds(seatIds);
    }

    @Override
    public void bookSeats(List<Seat> seats) throws InvalidSeatException {
        for (Seat seat : seats) {
            seat.setStatus(SeatStatus.BOOKED);
            this.saveSeat(seat);
        }
    }

    @Override
    public void releaseSeats(List<Seat> seats) throws InvalidSeatException {
        for (Seat seat : seats) {
            seat.setStatus(SeatStatus.AVAILABLE);
            this.saveSeat(seat);
        }
    }

    @Override
    public void validateSeats(List<Seat> seats) throws UnavailableSeatException {

        List<Seat> unavailableSeats = seats.stream()
                .filter(each -> !each.isAvailable())
                .toList();
        if (!unavailableSeats.isEmpty()) {
            throw new UnavailableSeatException("Seats are not available");
        }
    }
}
