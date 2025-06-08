package com.movie.booking.system.event;

import java.util.logging.Logger;

public class RabbitMQEventPublisher implements EventPublisher {
    private static final Logger logger = Logger.getLogger(RabbitMQEventPublisher.class.getName());
    private final String host;
    private final int port;

    public RabbitMQEventPublisher(String host, int port) {
        this.host = host;
        this.port = port;
        logger.info("Initializing RabbitMQ publisher with host: " + host + ", port: " + port);
    }

    @Override
    public void publish(String topic, String message) {
        // Simulate RabbitMQ publishing
        logger.info("Publishing to RabbitMQ - Exchange: " + topic + ", Message: " + message);
        // In a real implementation, this would use Channel to publish the message
    }

    @Override
    public void close() {
        logger.info("Closing RabbitMQ publisher");
        // In a real implementation, this would close the Channel and Connection
    }
} 