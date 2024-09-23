package com.example.AsterixApi.controller;

import com.example.AsterixApi.model.Character;
import com.example.AsterixApi.repository.CharacterRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/asterix/characters")
@AllArgsConstructor
public class CharacterController {

    private final CharacterRepository characterRepository;

    @GetMapping
    public List<Character> findAll(){
        List<Character> list = characterRepository.findAll();
        return list;
    }

    @PostMapping
    public Character save(@RequestBody Character character) {
        Character saved = characterRepository.save(character);
        return saved;
    }

    @PutMapping("/{id}")
    public Character updateCharacter(@PathVariable String id, @RequestBody Character characterNew) {
        Character existingCharacter = characterRepository.findById(id).get();

        Character updatedCharacter = new Character(
                characterNew.id() != null ? characterNew.id() : existingCharacter.id(),
                characterNew.name() != null ? characterNew.name() : existingCharacter.name(),
                characterNew.age() != 0 ? characterNew.age() : existingCharacter.age(),
                characterNew.profession() != null ? characterNew.profession() : existingCharacter.profession()
        );

        characterRepository.save(updatedCharacter);

        return updatedCharacter;
    }
}
