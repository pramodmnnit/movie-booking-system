package com.movie.booking.system.pattern.strategy;

import com.movie.booking.system.model.Seat;
import com.movie.booking.system.model.SeatType;
import com.movie.booking.system.model.Show;
import org.joda.time.DateTime;

public class StandardPricingStrategy implements PricingStrategy {
    private static final double STANDARD_MULTIPLIER = 1.0;
    private static final double PREMIUM_MULTIPLIER = 1.5;
    private static final double DELUXE_MULTIPLIER = 2.0;

    @Override
    public double calculatePrice(Seat seat, Show show, DateTime showDate) {
        double basePrice = getBasePrice(show);
        double multiplier = getMultiplierForSeatType(seat.getType());
        return basePrice * multiplier;
    }

    private double getBasePrice(Show show) {
        // This could be fetched from a configuration or database
        return 100.0;
    }

    private double getMultiplierForSeatType(SeatType seatType) {
        if (seatType == SeatType.REGULAR) {
            return STANDARD_MULTIPLIER;
        } else if (seatType == SeatType.PREMIUM) {
            return DELUXE_MULTIPLIER;
        }
        return PREMIUM_MULTIPLIER;
    }
} 