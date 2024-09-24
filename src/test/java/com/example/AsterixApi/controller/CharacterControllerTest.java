package com.example.AsterixApi.controller;

import com.example.AsterixApi.AsterixApiApplication;
import com.example.AsterixApi.model.Character;
import com.example.AsterixApi.repository.CharacterRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = AsterixApiApplication.class)
class CharacterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CharacterRepository characterRepository;

    @Test
    @DirtiesContext
    void findAll() throws Exception {
        //GIVEN
        Character characterOne = new Character("1", "First", 55, "Baker");
        Character characterTwo = new Character("2", "Second", 35, "Grass cutter");

        when(characterRepository.findAll()).thenReturn(List.of(characterOne, characterTwo));

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

        verify(characterRepository, times(1)).findAll();
    }


    @Test
    @DirtiesContext
    void getAllById() throws Exception {
        //GIVEN
        String idToFind = "1";
        Character characterOne = new Character(idToFind, "First", 55, "Baker");

        when(characterRepository.findById(idToFind)).thenReturn(Optional.of(characterOne));

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/asterix/characters/" + idToFind))
                //THEN
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                {
                  "id": "1",
                  "name": "First",
                  "age": 55,
                  "profession": "Baker"
                }
                """));

        verify(characterRepository, times(1)).findById(idToFind);
    }

    @Test
    @DirtiesContext
    void delete() throws Exception {
        //GIVEN
        String idToDelete = "1";
        Character characterOne = new Character(idToDelete, "First", 55, "Baker");

        when(characterRepository.findById(idToDelete)).thenReturn(Optional.of(characterOne));

        doNothing().when(characterRepository).deleteById(idToDelete);

        when(characterRepository.existsById(idToDelete)).thenReturn(false);

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.delete("/asterix/characters/" + idToDelete))
                //THEN
                .andExpect(MockMvcResultMatchers.status().isOk());

        assertFalse(characterRepository.existsById(idToDelete));

        verify(characterRepository, times(1)).deleteById(idToDelete);
        verify(characterRepository, times(1)).existsById(idToDelete);
    }


    @Test
    @DirtiesContext
    void save() throws Exception {
        //GIVEN
        Character savedCharacter = new Character("1", "First", 55, "Baker");

        when(characterRepository.save(any(Character.class))).thenReturn(savedCharacter);

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/asterix/characters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                  "name": "First",
                  "age": 55,
                  "profession": "Baker"
                }
                """))
                //THEN
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                {
                  "name": "First",
                  "age": 55,
                  "profession": "Baker"
                }
                """));

        verify(characterRepository, times(1)).save(any(Character.class));
    }

    @Test
    @DirtiesContext
    void updateCharacter() throws Exception {
        //GIVEN
        String idToEdit = "1";
        Character existingCharacter = new Character(idToEdit, "First", 55, "Baker");
        Character updatedCharacter = new Character(idToEdit, "FirstEdited", 55, "Software-developer");

        when(characterRepository.findById(idToEdit)).thenReturn(Optional.of(existingCharacter));
        when(characterRepository.save(any(Character.class))).thenReturn(updatedCharacter);

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.put("/asterix/characters/" + idToEdit)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                  "name": "FirstEdited",
                  "age": 55,
                  "profession": "Software-developer"
                }
                """))
                //THEN
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                {
                  "name": "FirstEdited",
                  "age": 55,
                  "profession": "Software-developer"
                }
                """));

        verify(characterRepository, times(1)).findById(idToEdit);
        verify(characterRepository, times(1)).save(any(Character.class));
    }
}
