package com.gpsservice.application.port.in;

import com.gpsservice.adapter.in.rest.dto.RegisterDeviceLocationRequest;

/**
 * Use case responsible for registering device location updates.
 * <p>
 * This operation processes incoming GPS location data for a device,
 * validates it, and persists it in the system.
 * It may also trigger domain events or additional processing.
 * </p>
 */
public interface RegisterDeviceLocationUseCase {

    /**
     * Registers a new device location event.
     *
     * @param registerRequest request containing device location data
     *
     * @throws IllegalArgumentException if the request contains invalid data
     * @throws RuntimeException if business rules are violated (e.g. duplicate timestamp)
     */
    void register(RegisterDeviceLocationRequest registerRequest);
}