package com.movie.booking.system.event;

public class EventPublisherFactory {
    public enum PublisherType {
        KAFKA,
        RABBITMQ
    }

    public static EventPublisher createPublisher(PublisherType type, String... config) {
        return switch (type) {
            case KAFKA -> {
                if (config.length < 1) {
                    throw new IllegalArgumentException("Kafka requires bootstrap servers configuration");
                }
                yield new KafkaEventPublisher(config[0]);
            }
            case RABBITMQ -> {
                if (config.length < 2) {
                    throw new IllegalArgumentException("RabbitMQ requires host and port configuration");
                }
                yield new RabbitMQEventPublisher(config[0], Integer.parseInt(config[1]));
            }
            default -> throw new IllegalArgumentException("Unsupported publisher type: " + type);
        };
    }
} 