package main.java.com.hu.picit.api.controller;
import java.util.*;

import main.java.com.hu.core.annotation.*;
import main.java.com.hu.core.controller.BaseController;
import main.java.com.hu.picit.api.model.fruit.*;
import main.java.com.hu.picit.service.FruitService;

@Controller("/api/[controller]")
public class FruitsController extends BaseController {
    @Autowired
    private FruitService fruitService = new FruitService();
    
    public FruitsController() {
    }

    @HttpGet()
    private List<FruitDTO> getFruits(){
        return fruitService.getFruits();
    }

    @HttpGet("/recommended")
    private List<FruitDTO> getRecommendedFruits(){
        return fruitService.getRecommendedFruits();
    }

    @HttpGet("/{id}")
    private FruitDTO getFruit(@PathVariable("id") int id){
        FruitDTO fruit = fruitService.getFruit(id);
        
        if(fruit == null) {
            return null;
        }

        return fruit;
    }
}