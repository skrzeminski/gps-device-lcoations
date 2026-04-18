package com.deviceservice.application.port.in;


import com.deviceservice.adapter.in.rest.dto.RegisterDeviceRequest;

/**
 * Use case responsible for registering a new device in the system.
 *
 * <p>Creates a new device based on the provided request data.
 * The device must have a unique identifier.</p>
 *
 */
public interface RegisterDeviceUseCase {

    String register(RegisterDeviceRequest registerRequest);
}