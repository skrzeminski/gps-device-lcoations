package com.gpsservice.application.port.in;


import com.gpsservice.domain.model.DeviceLocation;

import java.util.List;

/**
 * Use case for retrieving device location data.
 *
 * <p>Provides access to the latest location of a device
 * as well as the full history of its recorded locations.</p>
 */
public interface GetDeviceLocationUseCase {

    /**
     * Retrieves the most recent location of the given device.
     *
     * @param deviceId unique identifier of the device
     * @return the latest known location of the device
     *
     */
    DeviceLocation getLatestLocation(String deviceId);

    /**
     * Retrieves all recorded locations for the given device.
     *
     * @param deviceId unique identifier of the device
     * @return list of all locations associated with the device (may be empty)
     *
     */
    List<DeviceLocation> getAllByDeviceId(String deviceId);
}