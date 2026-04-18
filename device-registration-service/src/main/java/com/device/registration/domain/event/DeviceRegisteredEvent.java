package com.device.registration.domain.event;

public record DeviceRegisteredEvent(
        String deviceId,
        String name,
        String type
) {

    public static DeviceRegisteredEvent create(
            String id,
            String name,
            String type
    ) {
        return new DeviceRegisteredEvent(
                id,
                name,
                type
        );
    }
}