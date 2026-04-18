package com.gpsservice.application.service;


import com.gpsservice.adapter.in.rest.dto.RegisterDeviceLocationRequest;
import com.gpsservice.application.port.in.GetDeviceLocationUseCase;
import com.gpsservice.application.port.in.RegisterDeviceLocationUseCase;
import com.gpsservice.application.port.out.DeviceClientPort;
import com.gpsservice.application.port.out.DeviceLocationRepositoryPort;
import com.gpsservice.application.valdiator.LocationValidator;
import com.gpsservice.domain.model.DeviceLocation;
import com.gpsservice.exception.DeviceLocationNotFoundException;
import com.gpsservice.exception.DeviceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceLocationService implements
        RegisterDeviceLocationUseCase,
        GetDeviceLocationUseCase {

    private final DeviceLocationRepositoryPort repository;
    private final DeviceClientPort deviceClientPort;
    private final LocationValidator locationValidator;

    public DeviceLocationService(DeviceLocationRepositoryPort repository, LocationValidator locationValidator, DeviceClientPort deviceClientPort) {
        this.repository = repository;
        this.locationValidator = locationValidator;
        this.deviceClientPort = deviceClientPort;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeviceLocation getLatestLocation(String deviceId) {
        return repository
                .findLatestByDeviceId(deviceId)
                .orElseThrow(() -> new DeviceLocationNotFoundException("No location for device: " + deviceId));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DeviceLocation> getAllByDeviceId(String deviceId) {
        return repository.findAllByDeviceId(deviceId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void register(RegisterDeviceLocationRequest req) {
        var deviceLocation = toDeviceLocation(req);
        var deviceId = deviceLocation.deviceId();
        var deviceInfo = deviceClientPort.getDevice(deviceId)
                .orElseThrow(() -> new DeviceNotFoundException("Device not found: " + deviceId));
        var previous = repository.findLatestByDeviceId(deviceId).orElse(null);
        var location = new DeviceLocation(deviceId, req.latitude(), req.longitude(), req.timestamp());
        locationValidator.validate(location, deviceInfo, previous);
        repository.save(location);
    }

    private DeviceLocation toDeviceLocation(RegisterDeviceLocationRequest req) {
        return new DeviceLocation(req.deviceId(), req.latitude(), req.longitude(), req.timestamp());
    }
}