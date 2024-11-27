package main.java.com.hu.picit.api.model.category;

import java.util.function.Function;

public class CategoryDTOMapper implements Function<Category, CategoryDTO> {
    @Override
    public CategoryDTO apply(Category category) {
        return new CategoryDTO(category.getId(), category.getName(), category.getImg());
    }
}