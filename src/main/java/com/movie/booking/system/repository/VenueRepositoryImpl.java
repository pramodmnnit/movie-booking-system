package com.movie.booking.system.repository;

import com.movie.booking.system.model.Venue;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class VenueRepositoryImpl implements VenueRepository {

    private final Map<String, Venue> venueMap = new HashMap<>();

    @Override
    public void saveVenue(Venue venue) {
        if (venue != null && venue.getId() != null) {
            venueMap.put(venue.getId(), venue);
        }
    }

    @Override
    public Venue getVenueById(String id) {
        return venueMap.get(id);
    }

    @Override
    public Venue getVenueByPincode(String pincode) {
        return venueMap.values().stream()
                .filter(each -> each.getPincode().equals(pincode))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Venue> getAllVenues() {
        return new ArrayList<>(venueMap.values());
    }

    @Override
    public boolean deleteVenue(String id) {
        if (id == null || !venueMap.containsKey(id)) {
            return false;
        }
        venueMap.remove(id);
        return true;
    }
}
