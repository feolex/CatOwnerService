package ru.feolex.cat.service.controller;

import ru.feolex.cat.service.common.model.Cat;
import ru.feolex.cat.service.service.CatOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/cat-owners")
public class CatOwnerController {

    private final CatOwnerService catOwnerService;

    @Autowired
    public CatOwnerController(CatOwnerService catOwnerService) {
        this.catOwnerService = catOwnerService;
    }

    @GetMapping("/{catId}")
    public ResponseEntity<Map<String, Object>> getCatWithOwnerDetails(@PathVariable Long catId) {
        try {
            Map<String, Object> result = catOwnerService.getCatWithOwnerDetails(catId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/verified")
    public ResponseEntity<Cat> createCatWithOwnerVerification(@RequestBody Cat cat) {
        try {
            Cat newCat = catOwnerService.createCatWithOwnerVerification(cat);
            return ResponseEntity.status(HttpStatus.CREATED).body(newCat);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
} 
