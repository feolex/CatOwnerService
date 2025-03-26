package com.catownerservice.owner.service;

import com.catownerservice.owner.model.event.CatEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);
    
    private final OwnerService ownerService;
    
    @Autowired
    public KafkaConsumerService(OwnerService ownerService) {
        this.ownerService = ownerService;
    }
    
    @KafkaListener(topics = "${app.kafka.cat-topics.cat-created-topic}",
                  groupId = "${spring.kafka.consumer.group-id}")
    public void consumeCatCreatedEvent(CatEvent event) {
        logger.info("Received cat created event: {}", event);
        
        // Process the cat event - for example, updating owner statistics
        if (event.getOwnerId() != null) {
            try {
                // Example: Update owner's cats count or other statistics
                logger.info("Processing cat created event for owner ID: {}", event.getOwnerId());
            } catch (Exception e) {
                logger.error("Error processing cat created event for owner ID: {}", event.getOwnerId(), e);
            }
        }
    }
} 