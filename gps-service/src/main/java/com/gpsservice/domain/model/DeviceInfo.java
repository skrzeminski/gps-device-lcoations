package com.gpsservice.domain.model;

public record DeviceInfo(
        String deviceId,
        String name,
        DeviceType type
) {

    public static DeviceInfo create(String id, String name, DeviceType type) {
        return new DeviceInfo(
                id,
                name,
                type
        );
    }
}