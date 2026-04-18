package com.deviceservice.adapter.out.persistance;

import com.deviceservice.adapter.out.persistance.entity.DeviceEntity;
import com.deviceservice.adapter.out.persistance.repository.DeviceJpaRepository;
import com.deviceservice.application.port.out.DeviceRepositoryPort;
import com.deviceservice.domain.model.Device;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DeviceRepositoryAdapter implements DeviceRepositoryPort {

    private final DeviceJpaRepository jpaRepository;
    private final CacheManager cacheManager;

    public DeviceRepositoryAdapter(DeviceJpaRepository jpaRepository, CacheManager cacheManager) {
        this.jpaRepository = jpaRepository;
        this.cacheManager = cacheManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Device device) {
        var saved = jpaRepository.save(toEntity(device));
        var savedDevice = toDomain(saved);
        addToCache(List.of(savedDevice));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable(value = "devices", key = "#id")
    public Optional<Device> findById(String id) {
        return jpaRepository.findById(id)
                .map(this::toDomain);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Device> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Device> findAllById(List<String> ids) {
        var devices = jpaRepository.findAllById(ids).stream()
                .map(this::toDomain)
                .toList();
        addToCache(devices);
        return devices;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable(value = "devices", key = "#id + ':exists'")
    public boolean existsById(String id) {
        return jpaRepository.existsById(id);
    }

    private void addToCache(List<Device> devices) {
        Cache cache = cacheManager.getCache("devices");
        if (cache != null) {
            devices.forEach(d -> cache.put(d.deviceId(), d));
        }
    }

    private DeviceEntity toEntity(Device d) {
        DeviceEntity e = new DeviceEntity();
        e.setId(d.deviceId());
        e.setName(d.name());
        e.setType(d.type());
        return e;
    }

    private Device toDomain(DeviceEntity e) {
        return new Device(e.getId(), e.getName(), e.getType());
    }

}