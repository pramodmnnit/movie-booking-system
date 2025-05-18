package com.movie.booking.system.service;

import com.movie.booking.system.exception.InvalidSeatException;
import com.movie.booking.system.exception.UnavailableSeatException;
import com.movie.booking.system.model.Seat;
import com.movie.booking.system.model.Show;

import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public interface SeatService {

    void validateSeats(List<Seat> seats) throws UnavailableSeatException;

    void bookSeats(List<Seat> seats) throws InvalidSeatException;

    void saveSeat(Seat seat) throws InvalidSeatException;

    void releaseSeats(List<Seat> seats) throws InvalidSeatException;

    List<ReentrantLock> getSeatLocks(List<Seat> seats, Show show, Date showDate) throws UnavailableSeatException, InterruptedException;
}
