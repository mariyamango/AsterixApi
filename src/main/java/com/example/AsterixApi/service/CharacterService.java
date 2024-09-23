package com.example.AsterixApi.service;

import com.example.AsterixApi.repository.CharacterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.AsterixApi.model.Character;

@Service
@RequiredArgsConstructor
public class CharacterService {
    private final CharacterRepository characterRepository;
    private final IdService idService;

    public Character createCharacter(Character character) {
        return characterRepository.save(character);
    }

    public Character findById (String id){
        return characterRepository.findById(id).get();
    }

    public void deleteCharacter (String id){
        characterRepository.deleteById(id);
    }
}
