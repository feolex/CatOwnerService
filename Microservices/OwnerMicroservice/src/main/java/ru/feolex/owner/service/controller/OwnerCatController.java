package com.owner.service.controller;

import com.owner.service.client.model.Cat;
import com.owner.service.service.OwnerCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/owner-cats")
public class OwnerCatController {

    private final OwnerCatService ownerCatService;

    @Autowired
    public OwnerCatController(OwnerCatService ownerCatService) {
        this.ownerCatService = ownerCatService;
    }

    @GetMapping("/{ownerId}")
    public ResponseEntity<Map<String, Object>> getOwnerWithCats(@PathVariable Long ownerId) {
        try {
            Map<String, Object> result = ownerCatService.getOwnerWithCats(ownerId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{ownerId}/cats")
    public ResponseEntity<Cat> addCatToOwner(@PathVariable Long ownerId, @RequestBody Cat cat) {
        try {
            Cat newCat = ownerCatService.addCatToOwner(ownerId, cat);
            return ResponseEntity.status(HttpStatus.CREATED).body(newCat);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{ownerId}")
    public ResponseEntity<Void> deleteOwnerWithCats(@PathVariable Long ownerId) {
        try {
            ownerCatService.deleteOwnerWithCats(ownerId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
} 