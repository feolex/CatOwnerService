package ru.feolex.cat.service.controller;

import ru.feolex.cat.service.common.model.Cat;
import ru.feolex.cat.service.service.CatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/cats")
public class CatController {
    
    private final CatService catService;
    
    @Autowired
    public CatController(CatService catService) {
        this.catService = catService;
    }
    
    @GetMapping
    public ResponseEntity<List<Cat>> getAllCats() {
        return ResponseEntity.ok(catService.getAllCats());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Cat> getCatById(@PathVariable Long id) {
        Optional<Cat> cat = catService.getCatById(id);
        return cat.map(ResponseEntity::ok)
                 .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<Cat>> getCatsByOwnerId(@PathVariable Long ownerId) {
        return ResponseEntity.ok(catService.getCatsByOwnerId(ownerId));
    }
    
    @PostMapping
    public ResponseEntity<Cat> createCat(@RequestBody Cat cat) {
        return ResponseEntity.status(HttpStatus.CREATED).body(catService.saveCat(cat));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Cat> updateCat(@PathVariable Long id, @RequestBody Cat cat) {
        try {
            cat.setId(id);
            return ResponseEntity.ok(catService.updateCat(cat));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCat(@PathVariable Long id) {
        try {
            catService.deleteCat(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
} 