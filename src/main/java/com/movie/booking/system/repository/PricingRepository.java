package com.movie.booking.system.repository;

import com.movie.booking.system.model.Pricing;
import org.joda.time.DateTime;

import java.util.List;

public interface PricingRepository {
    void savePricing(Pricing pricing);

    Pricing getPricingById(String id);

    List<Pricing> getPricingByStartTimeEndTimeAndShowId(DateTime startTime, DateTime endTime, String showId);
}
