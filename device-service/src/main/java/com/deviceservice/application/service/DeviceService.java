package com.deviceservice.application.service;

import com.deviceservice.adapter.in.rest.dto.RegisterDeviceRequest;
import com.deviceservice.application.exception.DeviceAlreadyExistException;
import com.deviceservice.application.exception.DeviceNotFoundException;
import com.deviceservice.application.port.in.GetDeviceUseCase;
import com.deviceservice.application.port.in.RegisterDeviceUseCase;
import com.deviceservice.application.port.out.DeviceRepositoryPort;
import com.deviceservice.domain.model.Device;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DeviceService implements
        RegisterDeviceUseCase,
        GetDeviceUseCase {

    private final DeviceRepositoryPort deviceRepo;

    public DeviceService(DeviceRepositoryPort deviceRepo) {
        this.deviceRepo = deviceRepo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String register(RegisterDeviceRequest request) {
        var device = Device.create(request.deviceId(), request.name(), request.type());
        var optionalDevice = deviceRepo.findById(device.deviceId());
        if (optionalDevice.isPresent()) {
            throw new DeviceAlreadyExistException("Device already exists " + device.deviceId());
        }
        deviceRepo.save(device);
        return device.deviceId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Device get(String deviceId) {
        return deviceRepo.findById(deviceId).orElseThrow(() -> new DeviceNotFoundException("Device not found"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Device> getAll() {
        return deviceRepo.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Device> getAllByIds(List<String> deviceIds) {
        return deviceRepo.findAllById(deviceIds);
    }
}