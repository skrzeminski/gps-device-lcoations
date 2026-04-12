package com.device.registration.port.out;

import com.device.registration.domain.event.DeviceRegisteredEvent;

public interface DeviceEventPublisherPort {
    void publish(DeviceRegisteredEvent event);
}