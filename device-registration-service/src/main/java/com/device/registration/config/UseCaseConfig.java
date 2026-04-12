package com.device.registration.config;

import com.device.registration.application.service.RegisterDeviceService;
import com.device.registration.port.in.RegisterDeviceUseCase;
import com.device.registration.port.out.DeviceEventPublisherPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public RegisterDeviceUseCase registerDeviceUseCase(
            DeviceEventPublisherPort publisher
    ) {
        return new RegisterDeviceService(publisher);
    }
}