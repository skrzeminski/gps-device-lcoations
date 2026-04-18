package com.deviceservice.application.port.out;

import com.deviceservice.domain.model.Device;

import java.util.List;
import java.util.Optional;

/**
 * Port interface for accessing and persisting {@link Device} domain objects.
 * <p>
 * This interface defines the contract for device persistence operations,
 * independent of the underlying storage mechanism (e.g. JPA, MongoDB, external API).
 * </p>
 */
public interface DeviceRepositoryPort {

    /**
     * Saves a device to the repository.
     * If the device already exists, it may be updated depending on implementation.
     *
     * @param device the device to persist
     */
    void save(Device device);

    /**
     * Finds a device by its unique identifier.
     *
     * @param id unique device identifier
     * @return an {@link Optional} containing the device if found, otherwise empty
     */
    Optional<Device> findById(String id);

    /**
     * Retrieves all devices from the repository.
     *
     * @return list of all devices
     */
    List<Device> findAll();

    /**
     * Finds all devices matching the given list of identifiers.
     *
     * @param ids list of device identifiers
     * @return list of matching devices; empty list if none found
     */
    List<Device> findAllById(List<String> ids);

    /**
     * Checks whether a device exists in the repository.
     *
     * @param id unique device identifier
     * @return true if the device exists, false otherwise
     */
    boolean existsById(String id);
}