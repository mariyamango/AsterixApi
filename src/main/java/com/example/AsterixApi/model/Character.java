package com.example.AsterixApi.model;

import lombok.Builder;
import lombok.With;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Document("character")
public record Character(@With String id, String name, int age, String profession) {
}
