package com.movie.booking.system.service;

import com.movie.booking.system.model.Venue;

import java.util.List;

public interface VenueService {
    /**
     * Save a new venue or update an existing one
     *
     * @param venue the venue to save
     * @throws IllegalArgumentException if venue is null or has no ID
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

    /**
     * Update an existing venue
     *
     * @param venue the venue to update
     * @throws IllegalArgumentException if venue is null, has no ID, or doesn't exist
     */
    void updateVenue(Venue venue);
} 