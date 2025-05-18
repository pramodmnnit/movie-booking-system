package com.movie.booking.system.repository;

public interface VenueRepository {
    VenueRepository getVenueById(String id);

    VenueRepository getVenueByPincode(String pincode);
}
