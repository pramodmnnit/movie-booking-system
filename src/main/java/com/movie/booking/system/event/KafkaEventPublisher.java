package com.movie.booking.system.event;

import java.util.logging.Logger;

public class KafkaEventPublisher implements EventPublisher {
    private static final Logger logger = Logger.getLogger(KafkaEventPublisher.class.getName());
    private final String bootstrapServers;

    public KafkaEventPublisher(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
        logger.info("Initializing Kafka publisher with servers: " + bootstrapServers);
    }

    @Override
    public void publish(String topic, String message) {
        // Simulate Kafka publishing
        logger.info("Publishing to Kafka - Topic: " + topic + ", Message: " + message);
        // In a real implementation, this would use KafkaProducer to send the message
    }

    @Override
    public void close() {
        logger.info("Closing Kafka publisher");
        // In a real implementation, this would close the KafkaProducer
    }
} 