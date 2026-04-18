package com.gps.registration.application.service;

import com.gps.registration.domain.model.Location;
import com.gps.registration.domain.port.in.SendLocationUseCase;
import com.gps.registration.domain.port.out.LocationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class LocationService implements SendLocationUseCase {

    private final LocationEventPublisher publisher;

    public LocationService(LocationEventPublisher publisher) {
        this.publisher = publisher;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void send(String deviceId, double lat, double lon, Instant timestamp) {
        Location event = new Location(deviceId, lat, lon, timestamp);
        publisher.publish(event);
    }
}