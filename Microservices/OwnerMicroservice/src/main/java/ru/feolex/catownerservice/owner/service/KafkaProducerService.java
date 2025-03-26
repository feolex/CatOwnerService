package com.catownerservice.owner.service;

import com.catownerservice.owner.model.Owner;
import com.catownerservice.owner.model.event.OwnerEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaProducerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);
    
    private final KafkaTemplate<String, Object> kafkaTemplate;
    
    @Value("${app.kafka.owner-created-topic}")
    private String ownerCreatedTopic;
    
    @Value("${app.kafka.owner-updated-topic}")
    private String ownerUpdatedTopic;
    
    @Value("${app.kafka.owner-deleted-topic}")
    private String ownerDeletedTopic;
    
    @Autowired
    public KafkaProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    
    public void sendOwnerCreatedEvent(Owner owner) {
        OwnerEvent event = new OwnerEvent(OwnerEvent.EventType.CREATED, owner);
        sendOwnerEvent(ownerCreatedTopic, String.valueOf(owner.getId()), event);
    }
    
    public void sendOwnerUpdatedEvent(Owner owner) {
        OwnerEvent event = new OwnerEvent(OwnerEvent.EventType.UPDATED, owner);
        sendOwnerEvent(ownerUpdatedTopic, String.valueOf(owner.getId()), event);
    }
    
    public void sendOwnerDeletedEvent(Owner owner) {
        OwnerEvent event = new OwnerEvent(OwnerEvent.EventType.DELETED, owner);
        sendOwnerEvent(ownerDeletedTopic, String.valueOf(owner.getId()), event);
    }
    
    private void sendOwnerEvent(String topic, String key, OwnerEvent event) {
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, key, event);
        
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                logger.info("Sent event=[{}] with key=[{}] to topic=[{}] with offset=[{}]",
                        event, key, topic, result.getRecordMetadata().offset());
            } else {
                logger.error("Unable to send event=[{}] with key=[{}] to topic=[{}] due to : {}",
                        event, key, topic, ex.getMessage());
            }
        });
    }
} 