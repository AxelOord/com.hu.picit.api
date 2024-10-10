package main.java.com.hu.picit.service;

import java.util.ArrayList;
import java.util.List;

import main.java.com.hu.picit.api.model.fruit.Fruit;
import main.java.com.hu.picit.api.model.fruit.FruitDTO;
import main.java.com.hu.picit.api.model.fruit.FruitDTOMapper;

public class FruitService {
    private static List<Fruit> fruits = new ArrayList<Fruit>();
    private FruitDTOMapper fruitDTOMapper = new FruitDTOMapper();

    static {
        // Initialize the fruits array
        fruits.add(new Fruit("Apple", 10));
        fruits.add(new Fruit("Banana", 20));
        fruits.add(new Fruit("Orange", 15));
    }

    public List<FruitDTO> getFruits() {
        return fruits.stream().map(f -> fruitDTOMapper.apply(f)).toList();
    }

    public FruitDTO getFruit(int id) {
        return fruits.stream()
                .filter(f -> f.getId() == id)
                .map(f -> fruitDTOMapper.apply(f))
                .findFirst()
                .orElse(null);
    }
}
