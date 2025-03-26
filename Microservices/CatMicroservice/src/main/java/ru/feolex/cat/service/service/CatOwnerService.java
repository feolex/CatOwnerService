package ru.feolex.cat.service.service;

import ru.feolex.cat.service.client.OwnerServiceClient;
import ru.feolex.cat.service.client.model.Owner;
import ru.feolex.cat.service.common.model.Cat;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class CatOwnerService {

    private final CatService catService;
    private final OwnerServiceClient ownerServiceClient;

    @Autowired
    public CatOwnerService(CatService catService, OwnerServiceClient ownerServiceClient) {
        this.catService = catService;
        this.ownerServiceClient = ownerServiceClient;
    }

    public Map<String, Object> getCatWithOwnerDetails(Long catId) {
        Optional<Cat> optionalCat = catService.getCatById(catId);
        
        if (optionalCat.isPresent()) {
            Cat cat = optionalCat.get();
            Map<String, Object> result = new HashMap<>();
            result.put("cat", cat);
            
            try {
                Owner owner = ownerServiceClient.getOwnerById(cat.getOwnerId());
                result.put("owner", owner);
            } catch (FeignException e) {
                // Owner not found or service unavailable
                result.put("owner", "Owner information unavailable");
            }
            
            return result;
        }
        
        throw new RuntimeException("Cat not found with id: " + catId);
    }
    
    public Cat createCatWithOwnerVerification(Cat cat) {
        try {
            // Verify owner exists
            ownerServiceClient.getOwnerById(cat.getOwnerId());
            // If we get here, owner exists, so save the cat
            return catService.saveCat(cat);
        } catch (FeignException e) {
            throw new RuntimeException("Owner not found with id: " + cat.getOwnerId());
        }
    }
} 