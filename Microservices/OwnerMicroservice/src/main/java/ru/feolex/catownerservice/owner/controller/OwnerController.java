package com.catownerservice.owner.controller;

import com.catownerservice.owner.model.Owner;
import com.catownerservice.owner.service.OwnerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {

    private final OwnerService ownerService;
    
    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }
    
    @GetMapping
    public ResponseEntity<List<Owner>> getAllOwners() {
        return ResponseEntity.ok(ownerService.getAllOwners());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Owner> getOwnerById(@PathVariable Long id) {
        return ResponseEntity.ok(ownerService.getOwnerById(id));
    }
    
    @GetMapping("/email/{email}")
    public ResponseEntity<Owner> getOwnerByEmail(@PathVariable String email) {
        return ResponseEntity.ok(ownerService.getOwnerByEmail(email));
    }
    
    @PostMapping
    public ResponseEntity<Owner> createOwner(@Valid @RequestBody Owner owner) {
        Owner createdOwner = ownerService.createOwner(owner);
        return new ResponseEntity<>(createdOwner, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Owner> updateOwner(@PathVariable Long id, @Valid @RequestBody Owner ownerDetails) {
        return ResponseEntity.ok(ownerService.updateOwner(id, ownerDetails));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOwner(@PathVariable Long id) {
        ownerService.deleteOwner(id);
        return ResponseEntity.noContent().build();
    }
} 