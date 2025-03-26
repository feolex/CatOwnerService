package ru.feolex.cat.service.client;

import ru.feolex.cat.service.client.model.Owner;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "owner-service")
public interface OwnerServiceClient {
    
    @GetMapping("/api/owners/{id}")
    Owner getOwnerById(@PathVariable("id") Long id);
} 
