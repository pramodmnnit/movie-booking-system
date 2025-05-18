package com.movie.booking.system.repository;

import com.movie.booking.system.model.Venue;

import java.util.HashMap;
import java.util.Map;

public class VenueRepositoryImpl implements VenueRepository {

    private final Map<String, Venue> venueMap = new HashMap<>();

    @Override
    public VenueRepository getVenueById(String id) {
        return null;
    }

    @Override
    public VenueRepository getVenueByPincode(String pincode) {
        return null;
    }
}
