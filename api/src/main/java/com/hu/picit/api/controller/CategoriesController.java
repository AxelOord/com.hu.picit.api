package main.java.com.hu.picit.api.controller;

import java.util.List;

import main.java.com.hu.core.annotation.Autowired;
import main.java.com.hu.core.annotation.Controller;
import main.java.com.hu.core.annotation.HttpGet;
import main.java.com.hu.core.controller.BaseController;
import main.java.com.hu.picit.api.model.category.CategoryDTO;
import main.java.com.hu.picit.service.CategoryService;

@Controller("/api/[controller]")
public class CategoriesController extends BaseController  {
    @Autowired
    private CategoryService categoryService = new CategoryService();

    public CategoriesController() {
    }

    @HttpGet()
    private List<CategoryDTO> getCategories(){
        return categoryService.getCategories();
    }
}
