package ru.feolex.cat.service.service;

import ru.feolex.cat.service.common.model.Cat;
import ru.feolex.cat.service.dao.repository.CatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CatService {
    
    private final CatRepository catRepository;
    
    @Autowired
    public CatService(CatRepository catRepository) {
        this.catRepository = catRepository;
    }
    
    public List<Cat> getAllCats() {
        return catRepository.findAll();
    }
    
    public Optional<Cat> getCatById(Long id) {
        return catRepository.findById(id);
    }
    
    public List<Cat> getCatsByOwnerId(Long ownerId) {
        return catRepository.findByOwnerId(ownerId);
    }
    
    public List<Cat> getCatsByName(String name) {
        return catRepository.findByName(name);
    }
    
    public List<Cat> getCatsByBreed(String breed) {
        return catRepository.findByBreed(breed);
    }
    
    @Transactional
    public Cat saveCat(Cat cat) {
        return catRepository.save(cat);
    }
    
    @Transactional
    public Cat updateCat(Cat cat) {
        if (cat.getId() != null && catRepository.existsById(cat.getId())) {
            return catRepository.save(cat);
        }
        throw new NoSuchElementException("Cat not found with id: " + cat.getId());
    }
    
    @Transactional
    public void deleteCat(Long id) {
        if (catRepository.existsById(id)) {
            catRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("Cat not found with id: " + id);
        }
    }
} 