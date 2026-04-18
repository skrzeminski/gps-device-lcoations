package com.gpsservice.adapter.out.persistance;

import com.gpsservice.adapter.out.persistance.entity.DeviceLocationEntity;
import com.gpsservice.adapter.out.persistance.repository.DeviceLocationJpaRepository;
import com.gpsservice.application.port.out.DeviceLocationRepositoryPort;
import com.gpsservice.domain.model.DeviceLocation;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class DeviceLocationRepositoryAdapter implements DeviceLocationRepositoryPort {

    private final DeviceLocationJpaRepository jpaRepository;

    public DeviceLocationRepositoryAdapter(DeviceLocationJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable(value = "device-locations-latest", key = "#deviceId")
    public Optional<DeviceLocation> findLatestByDeviceId(String deviceId) {
        return jpaRepository
                .findFirstByDeviceIdOrderByTimestampDesc(UUID.fromString(deviceId))
                .map(this::toDomain);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @CacheEvict(value = {"device-locations-latest", "device-locations-all"}, key = "#location.deviceId()")
    public void save(DeviceLocation location) {
        jpaRepository.save(toEntity(location));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<DeviceLocation> findById(String id) {
        return jpaRepository.findById(UUID.fromString(id))
                .map(this::toDomain);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DeviceLocation> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(this::toDomain)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable(value = "device-locations-all", key = "#deviceId")
    public List<DeviceLocation> findAllByDeviceId(String deviceId) {
        return jpaRepository.findAllByDeviceId(UUID.fromString(deviceId))
                .stream()
                .map(this::toDomain)
                .toList();
    }

    private DeviceLocationEntity toEntity(DeviceLocation d) {
        return new DeviceLocationEntity(
                d.deviceId(),
                d.latitude(),
                d.longitude(),
                d.timestamp()
        );
    }

    private DeviceLocation toDomain(DeviceLocationEntity e) {
        return new DeviceLocation(
                e.getDeviceId().toString(),
                e.getLatitude(),
                e.getLongitude(),
                e.getTimestamp()
        );
    }
}