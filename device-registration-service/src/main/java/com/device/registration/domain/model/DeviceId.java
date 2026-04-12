package com.device.registration.domain.model;

import java.util.UUID;

public record DeviceId(String value) {
    public static DeviceId generate() {
        return new DeviceId(UUID.randomUUID().toString());
    }
}