package com.gps.registration.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Value("${app.kafka.topics.kafka-topic}")
    private String kafkaTopic;

    @Bean
    public NewTopic deviceRegisteredTopic() {
        return TopicBuilder.name(kafkaTopic)
                .partitions(5)
                .replicas(1)
                .build();
    }

    public String getKafkaTopic() {
        return kafkaTopic;
    }

}