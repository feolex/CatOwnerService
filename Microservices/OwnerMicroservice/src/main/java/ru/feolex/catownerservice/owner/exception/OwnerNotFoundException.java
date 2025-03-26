package com.catownerservice.owner.exception;

public class OwnerNotFoundException extends RuntimeException {
    public OwnerNotFoundException(String message) {
        super(message);
    }
    
    public OwnerNotFoundException(Long id) {
        super("Owner not found with id: " + id);
    }
} 