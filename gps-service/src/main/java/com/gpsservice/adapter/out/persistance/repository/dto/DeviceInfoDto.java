package com.gpsservice.adapter.out.persistance.repository.dto;

public record DeviceInfoDto(
        String deviceId,
        String name,
        String type
) {

    public static DeviceInfoDto create(String id, String name, String type) {
        return new DeviceInfoDto(
                id,
                name,
                type
        );
    }
}