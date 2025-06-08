package com.movie.booking.system.event;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EventPublisherFactoryTest {

    @Test
    void testCreateKafkaPublisher() {
        EventPublisher publisher = EventPublisherFactory.createPublisher(
            EventPublisherFactory.PublisherType.KAFKA,
            "localhost:9092"
        );
        assertNotNull(publisher);
        assertTrue(publisher instanceof KafkaEventPublisher);
        
        // Test publishing
        publisher.publish("test-topic", "test-message");
        publisher.close();
    }

    @Test
    void testCreateRabbitMQPublisher() {
        EventPublisher publisher = EventPublisherFactory.createPublisher(
            EventPublisherFactory.PublisherType.RABBITMQ,
            "localhost",
            "5672"
        );
        assertNotNull(publisher);
        assertTrue(publisher instanceof RabbitMQEventPublisher);
        
        // Test publishing
        publisher.publish("test-exchange", "test-message");
        publisher.close();
    }

    @Test
    void testInvalidKafkaConfig() {
        assertThrows(IllegalArgumentException.class, () -> 
            EventPublisherFactory.createPublisher(EventPublisherFactory.PublisherType.KAFKA)
        );
    }

    @Test
    void testInvalidRabbitMQConfig() {
        assertThrows(IllegalArgumentException.class, () -> 
            EventPublisherFactory.createPublisher(EventPublisherFactory.PublisherType.RABBITMQ, "localhost")
        );
    }
} 