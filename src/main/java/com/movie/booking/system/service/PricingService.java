package com.movie.booking.system.service;

import com.movie.booking.system.exception.InvalidPricingException;
import com.movie.booking.system.model.Pricing;
import com.movie.booking.system.model.Seat;
import com.movie.booking.system.model.SeatType;
import org.joda.time.DateTime;

import java.util.List;
import java.util.Map;

public interface PricingService {

    List<Pricing> getPricingByStartTimeEndTimeAndShowId(DateTime startTime, DateTime endTime, String showId) throws InvalidPricingException;

    Double calculateTotalAmount(List<Seat> seats, Map<SeatType, Pricing> seatTypeToPricingMap) throws InvalidPricingException;

    Map<SeatType, Pricing> getSeatTypeToPricingMap(DateTime startTime, DateTime endTime, String showId) throws InvalidPricingException;

    Double getTotalAmount(Double amount, Double tax);

    Double getTaxAmount(Double amount);
}
