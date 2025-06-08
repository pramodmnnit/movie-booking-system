package com.movie.booking.system.pattern.strategy;

import com.movie.booking.system.model.Seat;
import com.movie.booking.system.model.Show;
import org.joda.time.DateTime;

public interface PricingStrategy {
    double calculatePrice(Seat seat, Show show, DateTime showDate);
} 