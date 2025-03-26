package ru.feolex.cat.service;

import ru.feolex.cat.model.Cat;
import ru.feolex.cat.repository.CatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CatServiceTest {

    @Mock
    private CatRepository catRepository;

    @InjectMocks
    private CatService catService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllCats() {
        // Arrange
        Cat cat1 = new Cat(1L, "Whiskers", "Siamese", 3, 1L);
        Cat cat2 = new Cat(2L, "Felix", "Persian", 5, 2L);
        when(catRepository.findAll()).thenReturn(Arrays.asList(cat1, cat2));

        // Act
        List<Cat> cats = catService.findAllCats();

        // Assert
        assertEquals(2, cats.size());
        verify(catRepository, times(1)).findAll();
    }

    @Test
    void testFindCatById() {
        // Arrange
        Long catId = 1L;
        Cat cat = new Cat(catId, "Whiskers", "Siamese", 3, 1L);
        when(catRepository.findById(catId)).thenReturn(Optional.of(cat));

        // Act
        Optional<Cat> result = catService.findCatById(catId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Whiskers", result.get().getName());
        verify(catRepository, times(1)).findById(catId);
    }

    // ... additional test methods ...
} 