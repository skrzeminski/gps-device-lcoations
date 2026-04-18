package com.deviceservice.application.port.in;

import com.deviceservice.domain.model.Device;

import java.util.List;

/**
 * Use case for retrieving device information from the system.
 * <p>
 * This interface defines read operations for devices, including fetching
 * a single device, all devices, or a subset by identifiers.
 * </p>
 */
public interface GetDeviceUseCase {

    /**
     * Retrieves a device by its unique identifier.
     *
     * @param deviceId unique identifier of the device
     * @return the {@link Device} associated with the given id
     */
    Device get(String deviceId);

    /**
     * Retrieves all devices available in the system.
     *
     * @return list of all registered devices
     */
    List<Device> getAll();

    /**
     * Retrieves all devices matching the given identifiers.
     *
     * @param deviceIds list of device identifiers
     * @return list of matching devices; empty list if none found
     */
    List<Device> getAllByIds(List<String> deviceIds);
}