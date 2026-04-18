package com.device.registration.domain.port.out;

import com.device.registration.domain.event.DeviceRegisteredEvent;

/**
 * Port interface responsible for publishing domain events related to devices.
 * <p>
 * This abstraction allows the domain layer to publish events without knowing
 * the underlying messaging infrastructure (e.g. Kafka, RabbitMQ, etc.).
 * </p>
 */
public interface DeviceEventPublisherPort {

    /**
     * Publishes an event indicating that a device has been registered.
     *
     * @param event the domain event containing device registration data
     */
    void publish(DeviceRegisteredEvent event);
}