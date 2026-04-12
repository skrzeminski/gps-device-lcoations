package com.device.registration.adapter.in.rest.controller;

import com.device.registration.adapter.in.rest.dto.RegisterDeviceRequest;
import com.device.registration.adapter.in.rest.dto.RegisterDeviceResponse;
import com.device.registration.port.in.RegisterDeviceUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/devices")
public class DeviceController {

    private final RegisterDeviceUseCase registerDeviceUseCase;

    public DeviceController(RegisterDeviceUseCase registerDeviceUseCase) {
        this.registerDeviceUseCase = registerDeviceUseCase;
    }

    @PostMapping
    public ResponseEntity<RegisterDeviceResponse> register(@Valid @RequestBody RegisterDeviceRequest request) {
        var deviceId = registerDeviceUseCase.register(request.name(), request.type());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new RegisterDeviceResponse(deviceId));
    }
}