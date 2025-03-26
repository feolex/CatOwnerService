package ru.feolex.cat.service.common.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "cats")
public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column
    private String breed;
    
    @Column
    private LocalDate dateOfBirth;
    
    @Column
    private String color;
    
    @Column
    private String gender;
    
    @Column
    private String microchipNumber;
    
    @Column(nullable = false)
    private Long ownerId;

    // Default constructor
    public Cat() {
    }

    // All-args constructor
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

    // Equals, HashCode and ToString methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cat cat = (Cat) o;
        return Objects.equals(id, cat.id) &&
               Objects.equals(name, cat.name) &&
               Objects.equals(breed, cat.breed) &&
               Objects.equals(ownerId, cat.ownerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, breed, ownerId);
    }

    @Override
    public String toString() {
        return "Cat{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", breed='" + breed + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", color='" + color + '\'' +
                ", gender='" + gender + '\'' +
                ", microchipNumber='" + microchipNumber + '\'' +
                ", ownerId=" + ownerId +
                '}';
    }
} 