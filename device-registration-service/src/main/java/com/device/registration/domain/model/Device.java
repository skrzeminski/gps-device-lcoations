package com.device.registration.domain.model;

public record Device(
        DeviceId id,
        String name,
        String type
) {}