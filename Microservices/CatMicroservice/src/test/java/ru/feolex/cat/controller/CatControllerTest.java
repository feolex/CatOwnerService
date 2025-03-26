package ru.feolex.cat.controller;

import ru.feolex.cat.model.Cat;
import ru.feolex.cat.service.CatService;
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

@WebMvcTest(CatController.class)
class CatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CatService catService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllCats() throws Exception {
        // Arrange
        Cat cat1 = new Cat(1L, "Whiskers", "Siamese", 3, 1L);
        Cat cat2 = new Cat(2L, "Felix", "Persian", 5, 2L);
        when(catService.findAllCats()).thenReturn(Arrays.asList(cat1, cat2));

        // Act & Assert
        mockMvc.perform(get("/api/cats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Whiskers"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Felix"));
    }

    @Test
    void testGetCatById() throws Exception {
        // Arrange
        Long catId = 1L;
        Cat cat = new Cat(catId, "Whiskers", "Siamese", 3, 1L);
        when(catService.findCatById(catId)).thenReturn(Optional.of(cat));

        // Act & Assert
        mockMvc.perform(get("/api/cats/{id}", catId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Whiskers"));
    }

    @Test
    void testCreateCat() throws Exception {
        // Arrange
        Cat newCat = new Cat(null, "Mittens", "Tabby", 2, 3L);
        Cat savedCat = new Cat(3L, "Mittens", "Tabby", 2, 3L);
        when(catService.saveCat(any(Cat.class))).thenReturn(savedCat);

        // Act & Assert
        mockMvc.perform(post("/api/cats")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newCat)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.name").value("Mittens"));
    }

} 
