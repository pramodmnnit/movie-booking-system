package com.movie.booking.system.pattern.strategy;

import com.movie.booking.system.model.Seat;
import com.movie.booking.system.model.Show;
import org.joda.time.DateTime;

public class WeekendPricingStrategy implements PricingStrategy {
    private static final double WEEKEND_MULTIPLIER = 1.2;
    private final StandardPricingStrategy standardPricingStrategy;

    public WeekendPricingStrategy() {
        this.standardPricingStrategy = new StandardPricingStrategy();
    }

    @Override
    public double calculatePrice(Seat seat, Show show, DateTime showDate) {
        double standardPrice = standardPricingStrategy.calculatePrice(seat, show, showDate);
        return standardPrice * WEEKEND_MULTIPLIER;
    }
} 