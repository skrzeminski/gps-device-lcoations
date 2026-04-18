package com.deviceservice.application.exception;

public class DeviceAlreadyExistException extends RuntimeException {
    public DeviceAlreadyExistException(String message) {
        super(message);
    }
}
