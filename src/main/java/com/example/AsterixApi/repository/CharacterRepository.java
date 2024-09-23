package com.example.AsterixApi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.example.AsterixApi.model.Character;

import java.util.List;

@Repository
public interface CharacterRepository extends MongoRepository<Character, String> {
}
