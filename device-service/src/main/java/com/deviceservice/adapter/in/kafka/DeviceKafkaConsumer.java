package com.deviceservice.adapter.in.kafka;

import com.deviceservice.adapter.in.rest.dto.RegisterDeviceRequest;
import com.deviceservice.application.exception.DeviceAlreadyExistException;
import com.deviceservice.application.port.in.RegisterDeviceUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.util.List;


@Component
public class DeviceKafkaConsumer {

    private final Logger logger = LoggerFactory.getLogger(DeviceKafkaConsumer.class);
    private final RegisterDeviceUseCase registerDeviceUseCase;
    private final ObjectMapper objectMapper;

    public DeviceKafkaConsumer(RegisterDeviceUseCase registerDeviceUseCase,
                               ObjectMapper objectMapper) {
        this.registerDeviceUseCase = registerDeviceUseCase;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(
            topics = "${kafka.topics.device}",
            groupId = "${kafka.consumer.group-id}",
            concurrency = "5"
    )
    public void consume(String message){
        try {
            var req = objectMapper.readValue(message, RegisterDeviceRequest.class);
            registerDeviceUseCase.register(req);
        } catch (DeviceAlreadyExistException e) {
            logger.warn("Duplicate devices found: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error during device registration", e);
            throw e;
        }
    }

}