package com.movie.booking.system.model;

import lombok.Data;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@Data
public class Seat {

    private final ConcurrentHashMap<String, ReentrantLock> lockMap = new ConcurrentHashMap<>();
    private String id;
    private int seatNumber;
    private SeatType type;
    private SeatStatus status;
    private double price = 0.0;

    public boolean isAvailable() {
        return this.status == SeatStatus.AVAILABLE;
    }

    public ReentrantLock lockSeat(Date datetime, String showId) {
        return lockMap.computeIfAbsent(datetime + "_" + showId + "_" + id, k -> new ReentrantLock());
    }

    public void releaseLock(DateTime datetime, String showId) {
        ReentrantLock lock = lockMap.get(datetime + "_" + showId + "_" + id);
        if (lock != null) {
            lock.unlock();
            lockMap.remove(datetime + "_" + showId + "_" + id);
        }
    }
}
