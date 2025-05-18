package com.movie.booking.system.model;

import lombok.Data;

@Data
public class Venue {
    private String id;
    private String address;
    private Double latitude;
    private Double longitude;
    private String pincode;
}
