package com.deviceservice.adapter.out.persistance.entity;

import com.deviceservice.domain.model.DeviceType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "devices")
public class DeviceEntity {

    @Id
    private String id;

    private String name;

    @Enumerated(EnumType.STRING)
    private DeviceType type;

}