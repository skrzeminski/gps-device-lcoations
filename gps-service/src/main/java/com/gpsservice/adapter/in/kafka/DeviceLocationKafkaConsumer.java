package com.gpsservice.adapter.in.kafka;


import com.gpsservice.adapter.in.rest.dto.RegisterDeviceLocationRequest;
import com.gpsservice.application.port.in.RegisterDeviceLocationUseCase;
import com.gpsservice.exception.DeviceAlreadyExistException;
import com.gpsservice.exception.DeviceNotFoundException;
import com.gpsservice.exception.DeviceValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;


@Component
public class DeviceLocationKafkaConsumer {

    private final Logger logger = LoggerFactory.getLogger(DeviceLocationKafkaConsumer.class);
    private final RegisterDeviceLocationUseCase registerDeviceLocationUseCase;
    private final ObjectMapper objectMapper;

    public DeviceLocationKafkaConsumer(RegisterDeviceLocationUseCase registerDeviceLocationUseCase,
                                       ObjectMapper objectMapper) {
        this.registerDeviceLocationUseCase = registerDeviceLocationUseCase;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(
            topics = "${kafka.topics.locations}",
            groupId = "${kafka.consumer.group-id}",
            concurrency = "5"
    )
    public void consume(String message) {
        var request = objectMapper.readValue(message, RegisterDeviceLocationRequest.class);
        try {
            registerDeviceLocationUseCase.register(request);
        } catch (DeviceAlreadyExistException e) {
            logger.warn("Duplicate devices found: {}", e.getMessage());
        } catch (DeviceNotFoundException e) {
            logger.warn("Device not found: {}", e.getMessage());
        } catch (DeviceValidationException e) {
            logger.warn("Event not valid due to: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error during device registration", e);
            throw e;
        }
    }

}