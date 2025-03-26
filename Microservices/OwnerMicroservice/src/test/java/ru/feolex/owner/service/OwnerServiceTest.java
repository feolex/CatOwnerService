package ru.feolex.owner.service;

import ru.feolex.owner.model.Owner;
import ru.feolex.owner.repository.OwnerRepository;
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

class OwnerServiceTest {

    @Mock
    private OwnerRepository ownerRepository;

    @InjectMocks
    private OwnerService ownerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllOwners() {
        // Arrange
        Owner owner1 = new Owner(1L, "John Doe", "john@example.com", "555-1234");
        Owner owner2 = new Owner(2L, "Jane Smith", "jane@example.com", "555-5678");
        when(ownerRepository.findAll()).thenReturn(Arrays.asList(owner1, owner2));

        // Act
        List<Owner> owners = ownerService.findAllOwners();

        // Assert
        assertEquals(2, owners.size());
        verify(ownerRepository, times(1)).findAll();
    }

    @Test
    void testFindOwnerById() {
        // Arrange
        Long ownerId = 1L;
        Owner owner = new Owner(ownerId, "John Doe", "john@example.com", "555-1234");
        when(ownerRepository.findById(ownerId)).thenReturn(Optional.of(owner));

        // Act
        Optional<Owner> result = ownerService.findOwnerById(ownerId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getName());
        verify(ownerRepository, times(1)).findById(ownerId);
    }

} 
