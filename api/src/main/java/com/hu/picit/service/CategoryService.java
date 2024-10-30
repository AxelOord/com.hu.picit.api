package main.java.com.hu.picit.service;

import java.util.ArrayList;
import java.util.List;

import main.java.com.hu.picit.api.model.category.Category;
import main.java.com.hu.picit.api.model.category.CategoryDTO;
import main.java.com.hu.picit.api.model.category.CategoryDTOMapper;

public class CategoryService {
    private static List<Category> categories = new ArrayList<Category>();
    private CategoryDTOMapper categoryDTOMapper = new CategoryDTOMapper();

    static {
        categories.add(new Category("Citrusvruchten"));
        categories.add(new Category("Tropisch"));
        categories.add(new Category("Bessen"));
        categories.add(new Category("Steen vruchten"));
        categories.add(new Category("Meloenen"));
    }

    public List<CategoryDTO> getCategories() {
        return categories.stream().map(c -> categoryDTOMapper.apply(c)).toList();
    }

    public CategoryDTO getCategory(int id) {
        return categories.stream()
                .filter(c -> c.getId() == id)
                .map(c -> categoryDTOMapper.apply(c))
                .findFirst()
                .orElse(null);
    }
}
