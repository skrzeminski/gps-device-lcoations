package com.deviceservice.adapter.in.rest.dto;

import com.deviceservice.domain.model.DeviceType;

public record RegisterDeviceRequest(String deviceId, String name, DeviceType type) {
}