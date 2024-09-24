package com.example.AsterixApi.controller;

import com.example.AsterixApi.AsterixApiApplication;
import com.example.AsterixApi.repository.CharacterRepository;
import com.example.AsterixApi.model.Character;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = AsterixApiApplication.class)
class CharacterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CharacterRepository repo;

    @Test
    @DirtiesContext
    void findAll() throws Exception {
        //GIVEN
        Character characterOne = new Character("1", "First", 55, "Baker");
        Character characterTwo = new Character("2", "Second", 35, "Grass cutter");

        when(repo.findAll()).thenReturn(List.of(characterOne, characterTwo));

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/asterix/characters"))
                //THEN
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                [{
                  "id": "1",
                  "name": "First",
                  "age": 55,
                  "profession": "Baker"
                },
                {"id": "2",
                  "name": "Second",
                  "age": 35,
                  "profession": "Grass cutter"
                }
                ]
                """));
    }


    @Test
    @DirtiesContext
    void getAllById() {
    }

    @Test
    @DirtiesContext
    void delete() {
    }

    @Test
    @DirtiesContext
    void save() {
    }

    @Test
    @DirtiesContext
    void updateCharacter() {
    }
}
