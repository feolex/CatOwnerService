package com.catownerservice.owner.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Value("${app.kafka.owner-created-topic}")
    private String ownerCreatedTopic;

    @Value("${app.kafka.owner-updated-topic}")
    private String ownerUpdatedTopic;

    @Value("${app.kafka.owner-deleted-topic}")
    private String ownerDeletedTopic;
    
    @Bean
    public NewTopic ownerCreatedTopic() {
        return TopicBuilder.name(ownerCreatedTopic)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic ownerUpdatedTopic() {
        return TopicBuilder.name(ownerUpdatedTopic)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic ownerDeletedTopic() {
        return TopicBuilder.name(ownerDeletedTopic)
                .partitions(3)
                .replicas(1)
                .build();
    }
} 