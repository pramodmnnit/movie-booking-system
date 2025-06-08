package com.movie.booking.system.repository;

import com.movie.booking.system.model.Venue;

import java.util.List;

public interface VenueRepository {
    /**
     * Save a venue
     *
     * @param venue the venue to save
     */
    void saveVenue(Venue venue);

    /**
     * Get a venue by its ID
     *
     * @param id the venue ID
     * @return the venue if found, null otherwise
     */
    Venue getVenueById(String id);

    /**
     * Get a venue by its pincode
     *
     * @param pincode the venue pincode
     * @return the venue if found, null otherwise
     */
    Venue getVenueByPincode(String pincode);

    /**
     * Get all venues
     *
     * @return list of all venues
     */
    List<Venue> getAllVenues();

    /**
     * Delete a venue by its ID
     *
     * @param id the venue ID to delete
     * @return true if deleted, false if not found
     */
    boolean deleteVenue(String id);
}
