package com.movie.booking.system.repository;

import com.movie.booking.system.model.Pricing;
import org.joda.time.DateTime;

import java.util.*;

public class PricingRepositoryImpl implements PricingRepository {

    private final Map<String, Pricing> pricingMap = new HashMap<>();

    @Override
    public void savePricing(Pricing pricing) {
        if (Objects.nonNull(pricing)) {
            pricingMap.put(pricing.getId(), pricing);
        }
    }

    @Override
    public Pricing getPricingById(String id) {
        return null;
    }

    @Override
    public List<Pricing> getPricingByStartTimeEndTimeAndShowId(DateTime startTime, DateTime endTime, String showId) {
        List<Pricing> pricingList = new ArrayList<>();
        for (Pricing pricing : pricingMap.values()) {
            if (pricing.getStartTime().isEqual(startTime) && pricing.getEndTime().isEqual(endTime)
                    && pricing.getShow().getId().equals(showId)) {
                pricingList.add(pricing);
            }
        }
        return pricingList;
    }
}
