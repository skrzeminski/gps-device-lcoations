package com.deviceservice.domain.model;

public record Device(
        String deviceId,
        String name,
        DeviceType type
) {

    public static Device create(String id, String name, DeviceType type) {
        return new Device(
                id,
                name,
                type
        );
    }
}
