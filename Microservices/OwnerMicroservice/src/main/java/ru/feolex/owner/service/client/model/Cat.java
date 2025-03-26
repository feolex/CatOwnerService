package com.owner.service.client.model;

import java.time.LocalDate;

public class Cat {
    private Long id;
    private String name;
    private String breed;
    private LocalDate dateOfBirth;
    private String color;
    private String gender;
    private String microchipNumber;
    private Long ownerId;
    
    // Constructors
    public Cat() {
    }
    
    public Cat(Long id, String name, String breed, LocalDate dateOfBirth, 
               String color, String gender, String microchipNumber, Long ownerId) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.dateOfBirth = dateOfBirth;
        this.color = color;
        this.gender = gender;
        this.microchipNumber = microchipNumber;
        this.ownerId = ownerId;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getBreed() {
        return breed;
    }
    
    public void setBreed(String breed) {
        this.breed = breed;
    }
    
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public String getColor() {
        return color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    public String getGender() {
        return gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public String getMicrochipNumber() {
        return microchipNumber;
    }
    
    public void setMicrochipNumber(String microchipNumber) {
        this.microchipNumber = microchipNumber;
    }
    
    public Long getOwnerId() {
        return ownerId;
    }
    
    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
} 