package ru.feolex.cat.integration;

import ru.feolex.cat.model.Cat;
import ru.feolex.cat.repository.CatRepository;
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
class CatIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CatRepository catRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        catRepository.deleteAll();
    }

    @Test
    void testCatLifecycle() throws Exception {
        // 1. Create a new cat
        Cat newCat = new Cat(null, "Whiskers", "Siamese", 3, 1L);
        String catJson = objectMapper.writeValueAsString(newCat);
        
        String responseJson = mockMvc.perform(post("/api/cats")
                .contentType(MediaType.APPLICATION_JSON)
                .content(catJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Whiskers"))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andReturn().getResponse().getContentAsString();
        
        Cat createdCat = objectMapper.readValue(responseJson, Cat.class);
        
        // 2. Verify cat exists
        mockMvc.perform(get("/api/cats/{id}", createdCat.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdCat.getId()))
                .andExpect(jsonPath("$.name").value("Whiskers"));
        
        // 3. Update the cat
        createdCat.setName("Whiskers Jr.");
        createdCat.setAge(4);
        
        mockMvc.perform(put("/api/cats/{id}", createdCat.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createdCat)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Whiskers Jr."))
                .andExpect(jsonPath("$.age").value(4));
                
        // 4. Delete the cat
        mockMvc.perform(delete("/api/cats/{id}", createdCat.getId()))
                .andExpect(status().isOk());
                
        // 5. Verify cat no longer exists
        mockMvc.perform(get("/api/cats/{id}", createdCat.getId()))
                .andExpect(status().isNotFound());
    }
}
