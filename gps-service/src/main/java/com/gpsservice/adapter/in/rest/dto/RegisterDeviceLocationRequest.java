package com.gpsservice.adapter.in.rest.dto;

import java.time.Instant;

public record RegisterDeviceLocationRequest(
        String deviceId,
        double latitude,
        double longitude,
        Instant timestamp
) {
}
