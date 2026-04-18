package com.deviceservice.adapter.out.persistance.repository;

import com.deviceservice.adapter.out.persistance.entity.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceJpaRepository extends JpaRepository<DeviceEntity, String> {
}