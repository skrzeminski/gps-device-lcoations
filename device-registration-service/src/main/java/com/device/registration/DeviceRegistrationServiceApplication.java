package com.device.registration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class DeviceRegistrationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeviceRegistrationServiceApplication.class, args);
    }

}
