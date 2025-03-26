package com.catownerservice.owner.model.event;

import java.time.LocalDateTime;

public class CatEvent {
    
    public enum EventType {
        CREATED, UPDATED, DELETED
    }
    
    private String eventId;
    private EventType eventType;
    private Object cat; // Using Object since we don't have the Cat model in this service
    private LocalDateTime timestamp;
    private Long ownerId;
    
    // Getters and Setters
    public String getEventId() {
        return eventId;
    }
    
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
    
    public EventType getEventType() {
        return eventType;
    }
    
    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }
    
    public Object getCat() {
        return cat;
    }
    
    public void setCat(Object cat) {
        this.cat = cat;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    public Long getOwnerId() {
        return ownerId;
    }
    
    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
} 