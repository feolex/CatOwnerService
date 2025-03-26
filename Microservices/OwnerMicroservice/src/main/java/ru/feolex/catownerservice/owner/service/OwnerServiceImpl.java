package com.catownerservice.owner.service;

import com.catownerservice.owner.exception.OwnerNotFoundException;
import com.catownerservice.owner.model.Owner;
import com.catownerservice.owner.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;
    private final KafkaProducerService kafkaProducerService;
    
    @Autowired
    public OwnerServiceImpl(OwnerRepository ownerRepository, KafkaProducerService kafkaProducerService) {
        this.ownerRepository = ownerRepository;
        this.kafkaProducerService = kafkaProducerService;
    }
    
    @Override
    public List<Owner> getAllOwners() {
        return ownerRepository.findAll();
    }
    
    @Override
    public Owner getOwnerById(Long id) {
        return ownerRepository.findById(id)
                .orElseThrow(() -> new OwnerNotFoundException(id));
    }
    
    @Override
    @Transactional
    public Owner createOwner(Owner owner) {
        if (ownerRepository.existsByEmail(owner.getEmail())) {
            throw new IllegalArgumentException("Email already in use: " + owner.getEmail());
        }
        
        Owner savedOwner = ownerRepository.save(owner);
        
        // Publish owner created event
        kafkaProducerService.sendOwnerCreatedEvent(savedOwner);
        
        return savedOwner;
    }
    
    @Override
    @Transactional
    public Owner updateOwner(Long id, Owner ownerDetails) {
        Owner owner = getOwnerById(id);
        
        // Only update email if it's different and not already in use
        if (!owner.getEmail().equals(ownerDetails.getEmail()) && 
                ownerRepository.existsByEmail(ownerDetails.getEmail())) {
            throw new IllegalArgumentException("Email already in use: " + ownerDetails.getEmail());
        }
        
        owner.setFirstName(ownerDetails.getFirstName());
        owner.setLastName(ownerDetails.getLastName());
        owner.setEmail(ownerDetails.getEmail());
        owner.setPhone(ownerDetails.getPhone());
        owner.setAddress(ownerDetails.getAddress());
        
        Owner updatedOwner = ownerRepository.save(owner);
        
        // Publish owner updated event
        kafkaProducerService.sendOwnerUpdatedEvent(updatedOwner);
        
        return updatedOwner;
    }
    
    @Override
    @Transactional
    public void deleteOwner(Long id) {
        Owner owner = getOwnerById(id);
        
        // Publish owner deleted event before actually deleting
        kafkaProducerService.sendOwnerDeletedEvent(owner);
        
        ownerRepository.deleteById(id);
    }
    
    @Override
    public Owner getOwnerByEmail(String email) {
        return ownerRepository.findByEmail(email)
                .orElseThrow(() -> new OwnerNotFoundException("Owner not found with email: " + email));
    }
} 