package com.gpsservice.adapter.out.persistance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Table(
        name = "device_locations",
        indexes = {
                @Index(name = "idx_device_timestamp", columnList = "deviceId, timestamp")
        }
)
public class DeviceLocationEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private UUID deviceId;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @Column(nullable = false)
    private Instant timestamp;


    public DeviceLocationEntity(String deviceId,
                                double latitude,
                                double longitude,
                                Instant timestamp) {
        this.deviceId = UUID.fromString(deviceId);
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
    }
}