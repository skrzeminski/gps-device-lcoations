package com.gpsservice.adapter.out.persistance.repository;


import com.gpsservice.adapter.out.persistance.entity.DeviceLocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA repository for managing {@link DeviceLocationEntity} persistence.
 * <p>
 * Provides database access methods for storing and retrieving device GPS locations,
 * including queries for latest location and bulk device lookups.
 * </p>
 */
@Repository
public interface DeviceLocationJpaRepository extends JpaRepository<DeviceLocationEntity, UUID> {

    /**
     * Finds the latest location entry for a given device ordered by timestamp descending.
     *
     * @param deviceId unique identifier of the device
     * @return the most recent {@link DeviceLocationEntity} for the device, if exists
     */
    Optional<DeviceLocationEntity> findFirstByDeviceIdOrderByTimestampDesc(UUID deviceId);

    /**
     * Retrieves all location entries for a given device.
     *
     * @param deviceId unique identifier of the device
     * @return list of all location records for the device
     */
    List<DeviceLocationEntity> findAllByDeviceId(UUID deviceId);

    /**
     * Retrieves all location entries for multiple devices.
     *
     * @param deviceIds list of device identifiers
     * @return list of matching location records for the given devices
     */
    List<DeviceLocationEntity> findAllByDeviceIdIn(List<UUID> deviceIds);
}