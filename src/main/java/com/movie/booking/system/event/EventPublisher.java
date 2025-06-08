package com.movie.booking.system.event;

public interface EventPublisher {
    void publish(String topic, String message);
    void close();
} 