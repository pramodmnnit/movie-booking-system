package com.movie.booking.system.model;

import lombok.Data;

import java.util.List;

@Data
public class Show {

    private String id;
    private Integer startTime;
    private Integer endTime;
    private Integer duration;
    private Movie movie;
    private List<Seat> seats;
    private String language;
    private String format;
}
