package com.owner.service.service;

import com.owner.service.client.CatServiceClient;
import com.owner.service.client.model.Cat;
import com.owner.service.common.model.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OwnerCatService {

    private final OwnerService ownerService;
    private final CatServiceClient catServiceClient;

    @Autowired
    public OwnerCatService(OwnerService ownerService, CatServiceClient catServiceClient) {
        this.ownerService = ownerService;
        this.catServiceClient = catServiceClient;
    }

    public Map<String, Object> getOwnerWithCats(Long ownerId) {
        Optional<Owner> owner = ownerService.getOwnerById(ownerId);
        
        if (owner.isPresent()) {
            List<Cat> cats = catServiceClient.getCatsByOwnerId(ownerId);
            
            return Map.of(
                "owner", owner.get(),
                "cats", cats
            );
        }
        
        throw new RuntimeException("Owner not found with id: " + ownerId);
    }
    
    public Cat addCatToOwner(Long ownerId, Cat cat) {
        // Check if owner exists
        if (ownerService.getOwnerById(ownerId).isPresent()) {
            cat.setOwnerId(ownerId);
            return catServiceClient.createCat(cat);
        }
        
        throw new RuntimeException("Owner not found with id: " + ownerId);
    }
    
    public void deleteOwnerWithCats(Long ownerId) {
        // Get all cats for the owner
        List<Cat> cats = catServiceClient.getCatsByOwnerId(ownerId);
        
        // Delete all cats
        for (Cat cat : cats) {
            catServiceClient.deleteCat(cat.getId());
        }
        
        // Delete the owner
        ownerService.deleteOwner(ownerId);
    }
} 