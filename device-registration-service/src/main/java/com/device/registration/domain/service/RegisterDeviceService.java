package com.device.registration.domain.service;

import com.device.registration.domain.event.DeviceRegisteredEvent;
import com.device.registration.domain.port.in.RegisterDeviceUseCase;
import com.device.registration.domain.port.out.DeviceEventPublisherPort;
import org.springframework.stereotype.Service;

@Service
public class RegisterDeviceService implements RegisterDeviceUseCase {

    private final DeviceEventPublisherPort publisher;

    public RegisterDeviceService(DeviceEventPublisherPort publisher) {
        this.publisher = publisher;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String register(String id, String name, String type) {
        var event = DeviceRegisteredEvent.create(id, name, type);
        publisher.publish(event);
        return event.deviceId();
    }
}