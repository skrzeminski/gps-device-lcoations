package com.gps.registration.domain.model;


import java.time.Instant;

public record Location(
        String deviceId,
        double latitude,
        double longitude,
        Instant timestamp
) {}