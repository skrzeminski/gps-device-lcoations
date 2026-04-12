package com.device.registration.application.service;

import com.device.registration.domain.event.DeviceRegisteredEvent;
import com.device.registration.port.in.RegisterDeviceUseCase;
import com.device.registration.port.out.DeviceEventPublisherPort;

public class RegisterDeviceService implements RegisterDeviceUseCase {

    private final DeviceEventPublisherPort publisher;

    public RegisterDeviceService(DeviceEventPublisherPort publisher) {
        this.publisher = publisher;
    }

    @Override
    public String register(String name, String type) {
        var event = DeviceRegisteredEvent.create(name, type);
        publisher.publish(event);
        return event.deviceId();
    }
}