package com.device.registration.adapter.out.kafka;

import com.device.registration.config.KafkaConfig;
import com.device.registration.domain.event.DeviceRegisteredEvent;
import com.device.registration.port.out.DeviceEventPublisherPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class KafkaDeviceEventPublisher implements DeviceEventPublisherPort {

    private final Logger logger = LoggerFactory.getLogger(KafkaDeviceEventPublisher.class);

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaConfig kafkaConfig;
    private final ObjectMapper objectMapper;

    public KafkaDeviceEventPublisher(KafkaTemplate<String, String> kafkaTemplate, KafkaConfig kafkaConfig, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaConfig = kafkaConfig;
        this.objectMapper = objectMapper;
    }

    @Override
    public void publish(DeviceRegisteredEvent event) {
        var payload = objectMapper.writeValueAsString(event);
        kafkaTemplate.send(kafkaConfig.getKafkaTopic(), event.deviceId(), payload)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        logger.error("Kafka publish failed for key={}", event.deviceId(), ex);
                        return;
                    }
                    var metadata = result.getRecordMetadata();
                    logger.info("Kafka sent topic={}, partition={}, offset={}",
                            metadata.topic(),
                            metadata.partition(),
                            metadata.offset());
                });
    }
}