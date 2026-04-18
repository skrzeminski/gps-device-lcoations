package com.gpsservice.application.port.out;

import com.gpsservice.domain.model.DeviceInfo;

import java.util.Optional;

/**
 * Port interface for retrieving device information from an external system.
 * <p>
 * This abstraction hides the underlying implementation details of how device
 * data is fetched (e.g. REST API, gRPC, database, etc.).
 * </p>
 */
public interface DeviceClientPort {

    /**
     * Retrieves device information by its unique identifier.
     *
     * @param deviceId unique identifier of the device
     * @return an {@link Optional} containing {@link DeviceInfo} if the device exists,
     *         otherwise an empty Optional
     */
    Optional<DeviceInfo> getDevice(String deviceId);
}