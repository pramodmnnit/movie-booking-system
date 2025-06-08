package com.movie.booking.system.pattern.strategy;

import org.joda.time.DateTime;

public class PricingStrategyFactory {
    public static PricingStrategy getPricingStrategy(DateTime showDate) {
        // Check if the show date is a weekend
        int dayOfWeek = showDate.getDayOfWeek();
        if (dayOfWeek == 6 || dayOfWeek == 7) { // Saturday or Sunday
            return new WeekendPricingStrategy();
        }
        return new StandardPricingStrategy();
    }
} 