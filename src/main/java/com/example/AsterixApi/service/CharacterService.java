package com.example.AsterixApi.service;

import com.example.AsterixApi.dto.CreateCharacterRequest;
import com.example.AsterixApi.repository.CharacterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.AsterixApi.model.Character;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CharacterService {
    private final CharacterRepository characterRepository;
    private final IdService idService;

    public List<Character> findAll() {
        return characterRepository.findAll();
    }
    public Character createCharacter(Character character) {
        return characterRepository.save(character);
    }

    public Character findById (String id){
        return characterRepository.findById(id).get();
    }

    public void deleteCharacter (String id){
        characterRepository.deleteById(id);
    }

    public Character updateCharacter(String id, CreateCharacterRequest characterNew) {
        Character existingCharacter = characterRepository.findById(id).orElseThrow(() -> new RuntimeException("Character not found"));

        Character updatedCharacter = Character.builder()
                .id(existingCharacter.id())
                .name(characterNew.name() != null ? characterNew.name() : existingCharacter.name())
                .age(characterNew.age() != 0 ? characterNew.age() : existingCharacter.age())
                .profession(characterNew.profession() != null ? characterNew.profession() : existingCharacter.profession())
                .build();

        return characterRepository.save(updatedCharacter);
    }
    }
