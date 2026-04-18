package com.deviceservice.adapter.in.rest;


import com.deviceservice.application.port.in.GetDeviceUseCase;
import com.deviceservice.domain.model.Device;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/devices")
public class DeviceController {

    private final GetDeviceUseCase getDeviceUseCase;

    public DeviceController(GetDeviceUseCase getDeviceUseCase) {
        this.getDeviceUseCase = getDeviceUseCase;
    }

    @GetMapping("/{id}")
    public Device get(@PathVariable String id) {
        return getDeviceUseCase.get(id);
    }

    @GetMapping()
    public List<Device> getAll() {
        return getDeviceUseCase.getAll();
    }


}