package com.gpsservice.exception;

public class DeviceValidationException extends RuntimeException {

    private final String code;

    public DeviceValidationException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}