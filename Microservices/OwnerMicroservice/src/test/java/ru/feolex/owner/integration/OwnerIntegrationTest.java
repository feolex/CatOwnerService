package ru.feolex.owner.integration;

import ru.feolex.owner.model.Owner;
import ru.feolex.owner.repository.OwnerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class OwnerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        ownerRepository.deleteAll();
    }

    @Test
    void testOwnerLifecycle() throws Exception {
        // 1. Create a new owner
        Owner newOwner = new Owner(null, "John Doe", "john@example.com", "555-1234");
        String ownerJson = objectMapper.writeValueAsString(newOwner);
        
        String responseJson = mockMvc.perform(post("/api/owners")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ownerJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andReturn().getResponse().getContentAsString();
        
        Owner createdOwner = objectMapper.readValue(responseJson, Owner.class);
        
        // 2. Verify owner exists
        mockMvc.perform(get("/api/owners/{id}", createdOwner.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdOwner.getId()))
                .andExpect(jsonPath("$.name").value("John Doe"));
        
        // 3. Update the owner
        createdOwner.setName("John Smith");
        createdOwner.setPhone("555-5678");
        
        mockMvc.perform(put("/api/owners/{id}", createdOwner.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createdOwner)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Smith"))
                .andExpect(jsonPath("$.phone").value("555-5678"));
                
        // 4. Delete the owner
        mockMvc.perform(delete("/api/owners/{id}", createdOwner.getId()))
                .andExpect(status().isOk());
                
        // 5. Verify owner no longer exists
        mockMvc.perform(get("/api/owners/{id}", createdOwner.getId()))
                .andExpect(status().isNotFound());
    }
} 
