package com.gpsservice.domain.model;

import java.time.Instant;

public record DeviceLocation(String deviceId, double latitude, double longitude, Instant timestamp
) {

    public static DeviceLocation create(String deviceId, double latitude, double longitude, Instant timestamp) {
        return new DeviceLocation(deviceId, latitude, longitude, timestamp);
    }
}