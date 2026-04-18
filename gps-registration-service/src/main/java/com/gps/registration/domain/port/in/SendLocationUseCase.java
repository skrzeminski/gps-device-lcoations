package com.gps.registration.domain.port.in;


import java.time.Instant;

/**
 * Use case responsible for sending a device location update.
 *
 */
public interface SendLocationUseCase {

    /**
     * Registers a new location for a device.
     *
     * @param deviceId  device identifier
     * @param lat       latitude (-90 to 90)
     * @param lon       longitude (-180 to 180)
     * @param timestamp time when the location was recorded
     */
    void send(String deviceId, double lat, double lon, Instant timestamp);
}