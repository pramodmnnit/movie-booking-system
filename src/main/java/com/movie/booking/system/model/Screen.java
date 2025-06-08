package com.movie.booking.system.model;

import lombok.Data;

import java.util.List;

@Data
public class Screen {
    private String id;
    private String name;
    private List<Show> shows;
}
