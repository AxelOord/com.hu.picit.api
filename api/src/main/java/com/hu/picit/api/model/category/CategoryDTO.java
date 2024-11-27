package main.java.com.hu.picit.api.model.category;

import main.java.com.hu.core.dto.DTO;

public record CategoryDTO(int id, String name, String img) implements DTO {
}
