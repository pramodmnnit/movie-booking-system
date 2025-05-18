package com.movie.booking.system.model;

import lombok.Data;

import java.util.List;

@Data
public class Theatre {
    public String id;
    private Venue venue;
    private String name;
    private String description;
    private List<Screen> screens;
    private TheatreStatus status;
}
