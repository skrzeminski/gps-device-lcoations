package com.device.registration.domain.port.in;

/**
 * Use case responsible for registering a new device in the system.
 * <p>
 * This operation creates a device with the given identifier, name, and type.
 * It may validate input data and ensure that the device does not already exist.
 * </p>
 */
public interface RegisterDeviceUseCase {

    /**
     * Registers a new device.
     *
     * @param id    unique identifier of the device
     * @param name  human-readable device name
     * @param type  type of the device (e.g. ANDROID, IOS, IOT)
     * @return the identifier of the registered device
     *
     * @throws IllegalArgumentException if any parameter is invalid
     */
    String register(String id, String name, String type);
}