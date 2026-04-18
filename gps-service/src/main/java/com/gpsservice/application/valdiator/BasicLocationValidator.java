package com.gpsservice.application.valdiator;

import com.gpsservice.domain.model.DeviceInfo;
import com.gpsservice.domain.model.DeviceLocation;
import com.gpsservice.exception.DeviceNotFoundException;
import com.gpsservice.exception.DeviceValidationException;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class BasicLocationValidator implements LocationValidator {

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(DeviceLocation location, DeviceInfo deviceInfo, DeviceLocation previous) {
        validateDeviceExists(deviceInfo);
        validateCoordinates(location);
        validateTimestamp(location, previous);
    }

    private void validateDeviceExists(DeviceInfo deviceInfo) {
        if (deviceInfo == null) {
            throw new DeviceNotFoundException("Device not found");
        }
    }

    private void validateCoordinates(DeviceLocation location) {
        if (location.latitude() < -90 || location.latitude() > 90) {
            throw new DeviceValidationException("INVALID_LATITUDE", "Latitude out of range");
        }
        if (location.longitude() < -180 || location.longitude() > 180) {
            throw new DeviceValidationException("INVALID_LONGITUDE", "Longitude out of range");
        }
    }

    private void validateTimestamp(DeviceLocation location, DeviceLocation previous) {
        var now = Instant.now();
        if (location.timestamp().isAfter(now)) {
            throw new DeviceValidationException("FUTURE_TIMESTAMP", "Future timestamp not allowed");
        }
        if (previous == null) {
            return;
        }
        if (location.timestamp().equals(previous.timestamp())) {
            throw new DeviceValidationException("DUPLICATE_EVENT", "Duplicate GPS event");
        }
        if (location.timestamp().isBefore(previous.timestamp())) {
            throw new DeviceValidationException("OUT_OF_ORDER", "Out of order timestamp");
        }
    }
}