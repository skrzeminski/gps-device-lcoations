package com.gpsservice.application.port.out;

import com.gpsservice.domain.model.DeviceLocation;

import java.util.List;
import java.util.Optional;

/**
 * Port interface for managing persistence of {@link DeviceLocation} domain objects.
 * <p>
 * This abstraction defines access to device location storage without coupling
 * the domain layer to any specific database or persistence technology.
 * </p>
 */
public interface DeviceLocationRepositoryPort {

    /**
     * Saves a device location entry.
     *
     * @param device the device location to persist
     */
    void save(DeviceLocation device);

    /**
     * Finds the latest location recorded for a given device.
     *
     * @param deviceId unique identifier of the device
     * @return an {@link Optional} containing the latest location if exists, otherwise empty
     */
    Optional<DeviceLocation> findLatestByDeviceId(String deviceId);

    /**
     * Finds a device location by its unique identifier.
     *
     * @param id unique location identifier
     * @return an {@link Optional} containing the location if found, otherwise empty
     */
    Optional<DeviceLocation> findById(String id);

    /**
     * Retrieves all device location entries.
     *
     * @return list of all device locations
     */
    List<DeviceLocation> findAll();

    /**
     * Retrieves all location entries for a specific device.
     *
     * @param deviceId unique identifier of the device
     * @return list of all locations for the given device
     */
    List<DeviceLocation> findAllByDeviceId(String deviceId);
}