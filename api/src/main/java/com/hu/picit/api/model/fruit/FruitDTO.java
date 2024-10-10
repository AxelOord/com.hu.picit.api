package main.java.com.hu.picit.api.model.fruit;

import main.java.com.hu.core.dto.DTO;

public record FruitDTO(int id, String name, int quantity) implements DTO {
}
