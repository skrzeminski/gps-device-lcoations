package com.gps.registration.domain.port.out;

import com.gps.registration.domain.model.Location;

/**
 * Port responsible for publishing location-related events to external systems.
 *
 * <p>Used to propagate device location updates (e.g. to messaging systems like Kafka)
 * so that other services can react to changes in device position.</p>
 */
public interface LocationEventPublisher {

    /**
     * Publishes a location event.
     *
     * @param event location event to be published
     *
     */
    void publish(Location event);
}