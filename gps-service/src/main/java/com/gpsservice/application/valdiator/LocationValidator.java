package com.gpsservice.application.valdiator;

import com.gpsservice.domain.model.DeviceInfo;
import com.gpsservice.domain.model.DeviceLocation;
import com.gpsservice.exception.DeviceNotFoundException;
import com.gpsservice.exception.DeviceValidationException;

/**
 * Validator responsible for enforcing business rules on device location data.
 * <p>
 * This component ensures that incoming location updates are consistent,
 * valid, and compliant with domain constraints before they are persisted.
 * </p>
 */
public interface LocationValidator {

    /**
     * Validates a device location update against business rules.
     *
     * <p>This method ensures that the incoming location data is valid,
     * the device exists in the system, and that the event respects
     * temporal consistency rules.</p>
     *
     * @param location    incoming device location to validate
     * @param deviceInfo  information about the device (must exist in system)
     * @param previous    previous known location of the device (if any)
     *
     * @throws DeviceNotFoundException
     *         when the device does not exist in the system
     *
     * @throws DeviceValidationException
     *         when business rules are violated, such as:
     *         <ul>
     *             <li>latitude/longitude out of valid range</li>
     *             <li>timestamp in the future</li>
     *             <li>duplicate GPS event (same timestamp)</li>
     *             <li>out-of-order timestamp (older than previous)</li>
     *         </ul>
     */
    void validate(DeviceLocation location,
                  DeviceInfo deviceInfo,
                  DeviceLocation previous);
}