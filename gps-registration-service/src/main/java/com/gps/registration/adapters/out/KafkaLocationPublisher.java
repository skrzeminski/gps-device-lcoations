package com.gps.registration.adapters.out;

import com.gps.registration.config.KafkaConfig;
import com.gps.registration.domain.model.Location;
import com.gps.registration.domain.port.out.LocationEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class KafkaLocationPublisher implements LocationEventPublisher {

    private final Logger logger = LoggerFactory.getLogger(KafkaLocationPublisher.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaConfig kafkaConfig;
    private final ObjectMapper objectMapper;

    public KafkaLocationPublisher(KafkaTemplate<String, String> kafkaTemplate, KafkaConfig kafkaConfig, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaConfig = kafkaConfig;
        this.objectMapper = objectMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void publish(Location event) {
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