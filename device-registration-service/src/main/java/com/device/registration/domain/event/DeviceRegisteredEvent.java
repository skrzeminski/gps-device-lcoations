package com.device.registration.domain.event;

import com.device.registration.domain.model.DeviceId;

import java.time.Instant;

public record DeviceRegisteredEvent(
        String deviceId,
        String name,
        String type,
        Instant timestamp
) {

    public static DeviceRegisteredEvent create(
            String name,
            String type
    ) {
        DeviceId generate = DeviceId.generate();
        return new DeviceRegisteredEvent(
                generate.value(),
                name,
                type,
                Instant.now()
        );
    }
}