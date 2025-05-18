package com.movie.booking.system.model;

import lombok.Data;

@Data
public class Movie {

    private String id;
    private String name;
    private String description;
    private String genre;
    private String language;
    private Integer duration;
    private String releaseDate;
    private MovieStatus status;
}
