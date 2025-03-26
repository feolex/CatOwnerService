package com.owner.service.client;

import com.owner.service.client.model.Cat;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "cat-service")
public interface CatServiceClient {
    
    @GetMapping("/api/cats/owner/{ownerId}")
    List<Cat> getCatsByOwnerId(@PathVariable("ownerId") Long ownerId);
    
    @PostMapping("/api/cats")
    Cat createCat(@RequestBody Cat cat);
    
    @PutMapping("/api/cats/{id}")
    Cat updateCat(@PathVariable("id") Long id, @RequestBody Cat cat);
    
    @DeleteMapping("/api/cats/{id}")
    void deleteCat(@PathVariable("id") Long id);
} 
