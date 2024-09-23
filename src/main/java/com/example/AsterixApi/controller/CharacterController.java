package com.example.AsterixApi.controller;

import com.example.AsterixApi.dto.CreateCharacterRequest;
import com.example.AsterixApi.model.Character;
import com.example.AsterixApi.repository.CharacterRepository;
import com.example.AsterixApi.service.CharacterService;
import com.example.AsterixApi.service.IdService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/asterix/characters")
@AllArgsConstructor
public class CharacterController {

    private final CharacterRepository characterRepository;
    private final CharacterService characterService;
    private final IdService idService;

    @GetMapping
    public List<Character> findAll(){
        List<Character> list = characterRepository.findAll();
        return list;
    }

    @GetMapping("/{id}")
    public Character getAllById(@PathVariable String id){
        return characterService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){
        characterService.deleteCharacter(id);
    }

    @PostMapping
    public Character save(@RequestBody CreateCharacterRequest request) {
        Character saved = characterService.createCharacter(request.toModel(idService.randomId()));
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
