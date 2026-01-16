package main.java.com.hu.picit.api.controller;
import java.util.*;

import main.java.com.hu.core.annotation.*;
import main.java.com.hu.core.controller.BaseController;
import main.java.com.hu.picit.api.model.fruit.*;
import main.java.com.hu.picit.service.FruitService;

@Controller("/api/[controller]")
public class FruitsController extends BaseController<FruitDTO> {
    @Autowired
    private FruitService fruitService = new FruitService();
    
    public FruitsController() {
    }

    @HttpGet("/recommended")
    private List<FruitDTO> getRecommendedFruits(){
        return fruitService.getRecommendedFruits();
    }

    @HttpGet("/category/{category}")
    private List<FruitDTO> getFruitsByCategory(@PathVariable("category") int category){
        return fruitService.getFruitsByCategory(category);
    }

    @Override
    @HttpGet("/{id}")
    protected FruitDTO handleGetById(@PathVariable("id") int id) {
        return fruitService.getFruit(id);
    }

    @Override
    @HttpGet()
    protected List<FruitDTO> handleGetAll() {
        return fruitService.getFruits();
    }
}