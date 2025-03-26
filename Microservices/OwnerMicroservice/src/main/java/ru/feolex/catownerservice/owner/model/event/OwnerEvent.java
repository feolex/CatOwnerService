package com.catownerservice.owner.model.event;

import com.catownerservice.owner.model.Owner;

import java.time.LocalDateTime;

public class OwnerEvent {
    
    public enum EventType {
        CREATED, UPDATED, DELETED
    }
    
    private String eventId;
    private EventType eventType;
    private Owner owner;
    private LocalDateTime timestamp;
    
    public OwnerEvent() {
    }
    
    public OwnerEvent(EventType eventType, Owner owner) {
        this.eventId = java.util.UUID.randomUUID().toString();
        this.eventType = eventType;
        this.owner = owner;
        this.timestamp = LocalDateTime.now();
    }
    
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
    
    public Owner getOwner() {
        return owner;
    }
    
    public void setOwner(Owner owner) {
        this.owner = owner;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    @Override
    public String toString() {
        return "OwnerEvent{" +
                "eventId='" + eventId + '\'' +
                ", eventType=" + eventType +
                ", owner=" + owner +
                ", timestamp=" + timestamp +
                '}';
    }
} 