package com.gpsservice.adapter.in.rest;

import com.gpsservice.application.port.in.GetDeviceLocationUseCase;
import com.gpsservice.domain.model.DeviceLocation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/devices")
public class DeviceLocationController {

    private final GetDeviceLocationUseCase getDeviceUseCase;

    public DeviceLocationController(GetDeviceLocationUseCase getDeviceUseCase) {
        this.getDeviceUseCase = getDeviceUseCase;
    }

    @GetMapping("/{id}/locations")
    public List<DeviceLocation> getAllById(@PathVariable String id) {
        return getDeviceUseCase.getAllByDeviceId(id);
    }

    @GetMapping("/{id}/locations/latest")
    public DeviceLocation get(@PathVariable String id) {
        return getDeviceUseCase.getLatestLocation(id);
    }
}