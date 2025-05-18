package com.movie.booking.system.service;

import com.movie.booking.system.exception.InvalidPricingException;
import com.movie.booking.system.model.Pricing;
import com.movie.booking.system.model.Seat;
import com.movie.booking.system.model.SeatType;
import com.movie.booking.system.model.Show;
import com.movie.booking.system.repository.PricingRepository;
import com.movie.booking.system.strategy.PricingStrategy;
import com.movie.booking.system.strategy.PricingStrategyFactory;
import org.joda.time.DateTime;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class PricingServiceImpl implements PricingService {
    private final static Double TAX_RATE = 0.18;
    private final PricingRepository pricingRepository;

    public PricingServiceImpl(PricingRepository pricingRepository) {
        this.pricingRepository = pricingRepository;
    }

    @Override
    public List<Pricing> getPricingByStartTimeEndTimeAndShowId(DateTime startTime, DateTime endTime, String showId) throws InvalidPricingException {
        List<Pricing> pricingList = pricingRepository.getPricingByStartTimeEndTimeAndShowId(startTime, endTime, showId);
        if (CollectionUtils.isEmpty(pricingList)) {
            throw new InvalidPricingException(String.format("Pricing not found for the given start time %s, end time %s and show id %s",
                    startTime, endTime, showId));
        }
        return pricingList;
    }

    @Override
    public Double calculateTotalAmount(List<Seat> seats, Map<SeatType, Pricing> seatTypeToPricingMap) throws InvalidPricingException {
        Double totalAmount = 0.0;
        for (Seat seat : seats) {
            Pricing pricing = seatTypeToPricingMap.get(seat.getType());
            if (Objects.isNull(pricing)) {
                throw new InvalidPricingException(String.format("Pricing not found for the given seat type %s", seat.getType()));
            }
            totalAmount += pricing.getAmount();
        }
        return totalAmount;
    }

    @Override
    public Map<SeatType, Pricing> getSeatTypeToPricingMap(DateTime startTime, DateTime endTime, String showId) throws InvalidPricingException {
        return getPricingByStartTimeEndTimeAndShowId(startTime, endTime, showId).stream().collect(Collectors.toMap(Pricing::getSeatType, each -> each));
    }

    @Override
    public Double getTotalAmount(Double amount, Double tax) {
        return amount + tax;
    }

    @Override
    public Double getTaxAmount(Double amount) {
        return amount * TAX_RATE;
    }

    @Override
    public void savePricing(Pricing pricing) throws InvalidPricingException {
        pricingRepository.savePricing(pricing);
    }

    @Override
    public double calculatePriceWithStrategy(Seat seat, Show show, DateTime showDate) {
        PricingStrategy strategy = PricingStrategyFactory.getPricingStrategy(showDate);
        return strategy.calculatePrice(seat, show, showDate);
    }
}
