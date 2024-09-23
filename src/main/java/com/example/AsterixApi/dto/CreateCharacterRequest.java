package com.example.AsterixApi.dto;


import com.example.AsterixApi.model.Character;

public record CreateCharacterRequest(String name, int age, String profession) {

    public Character toModel(String id){
        return Character.builder()
                .id(id)
                .name(name)
                .age(age)
                .profession(profession)
                .build();
    }

}
