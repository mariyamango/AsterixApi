package com.example.AsterixApi.service;

import com.example.AsterixApi.dto.CreateCharacterRequest;
import com.example.AsterixApi.repository.CharacterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import  com.example.AsterixApi.model.Character;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CharacterServiceTest {

    @Mock
    private CharacterRepository characterRepository;

    @Mock
    private IdService IdService;

    @Mock
    private CharacterService characterService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        characterService = new CharacterService(characterRepository, IdService);
    }



    @Test
    void testFindAll() {
        List<Character> mockCharacters = Arrays.asList( new Character("1","Asterix", 55, "Gallic warrior"), new Character("2","Obelix", 40, "Sculptor") );
        when(characterRepository.findAll()).thenReturn(mockCharacters);

        List<Character> result = characterService.findAll();
         assertEquals(2, result.size());

         assertEquals("Asterix", result.get(0).name());
         assertEquals("Obelix", result.get(1).name());

         verify(characterRepository, times(1)).findAll();
    }
    @Test
    void findById() {
        Character mockCharacter = new Character("3","Bob", 55, "Gallic warrior");
        when(characterRepository.findById("3")).thenReturn(Optional.of(mockCharacter));

        Character result = characterService.findById("3");
        assertEquals("3", result.id());

        verify(characterRepository, times(1)).findById(anyString());
    }

    @Test
    void updateCharacter() {
       Character existingCharacter = new Character("2", "Obelix", 35, "Sculptor");
       CreateCharacterRequest updatingCharacter = new CreateCharacterRequest("Bob", 35, "Sculptor");

        when(characterRepository.findById("2")).thenReturn(Optional.of(existingCharacter));
        when(characterRepository.save(any(Character.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Character savedCharacter = characterService.updateCharacter("2", updatingCharacter);

        assertEquals(savedCharacter.name(), updatingCharacter.name());

        verify(characterRepository, times(1)).save(any(Character.class));
    }
    @Test
    void createCharacter() {
        Character newCharacter = new Character("5", "NewChar", 33, "something");

        when(characterRepository.save(any(Character.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(IdService.randomId()).thenReturn("1");

        Character savedCharacter = characterService.createCharacter(newCharacter);

        assertEquals(savedCharacter.name(), newCharacter.name());

        verify(characterRepository, times(1)).save(any(Character.class));
    }

    @Test
    void deleteCharacter() {
        List<Character> mockCharacters = Arrays.asList(
               new Character("1", "Asterix", 55, "Gallic warrior"),
               new Character("2", "Obelix", 40, "Sculptor")
        );
         CharacterService characterService = new CharacterService(characterRepository, new IdService());

            characterRepository.saveAll(mockCharacters);
            characterService.deleteCharacter("2");

        assertEquals(Optional.empty(), characterRepository.findById("2"));

        verify(characterRepository, times(1)).deleteById(anyString());
    }}
