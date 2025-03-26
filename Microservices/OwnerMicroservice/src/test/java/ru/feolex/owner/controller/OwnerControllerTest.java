package ru.feolex.owner.controller;

import ru.feolex.owner.model.Owner;
import ru.feolex.owner.service.OwnerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OwnerController.class)
class OwnerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OwnerService ownerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllOwners() throws Exception {
        // Arrange
        Owner owner1 = new Owner(1L, "John Doe", "john@example.com", "555-1234");
        Owner owner2 = new Owner(2L, "Jane Smith", "jane@example.com", "555-5678");
        when(ownerService.findAllOwners()).thenReturn(Arrays.asList(owner1, owner2));

        // Act & Assert
        mockMvc.perform(get("/api/owners"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Jane Smith"));
    }

    @Test
    void testGetOwnerById() throws Exception {
        // Arrange
        Long ownerId = 1L;
        Owner owner = new Owner(ownerId, "John Doe", "john@example.com", "555-1234");
        when(ownerService.findOwnerById(ownerId)).thenReturn(Optional.of(owner));

        // Act & Assert
        mockMvc.perform(get("/api/owners/{id}", ownerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void testCreateOwner() throws Exception {
        // Arrange
        Owner newOwner = new Owner(null, "Sarah Connor", "sarah@example.com", "555-9999");
        Owner savedOwner = new Owner(3L, "Sarah Connor", "sarah@example.com", "555-9999");
        when(ownerService.saveOwner(any(Owner.class))).thenReturn(savedOwner);

        // Act & Assert
        mockMvc.perform(post("/api/owners")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newOwner)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.name").value("Sarah Connor"));
    }


} 
