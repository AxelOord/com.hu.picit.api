package main.java.com.hu.picit.service;

import java.util.ArrayList;
import java.util.List;

import main.java.com.hu.picit.api.model.category.Category;
import main.java.com.hu.picit.api.model.fruit.Fruit;
import main.java.com.hu.picit.api.model.fruit.FruitDTO;
import main.java.com.hu.picit.api.model.fruit.FruitDTOMapper;

public class FruitService {
    private static List<Fruit> fruits = new ArrayList<Fruit>();
    private FruitDTOMapper fruitDTOMapper = new FruitDTOMapper();

    static {
        List<Category> categorylist = CategoryService.getCategoriesList();

        fruits.add(new Fruit("Apple", 10, 2.99, "Apple", "Netherlands", categorylist.get(0)));
        fruits.add(new Fruit("Banana", 20, 1.49, "Banana", "Netherlands", categorylist.get(1)));
        fruits.add(new Fruit("Orange", 15, 1.99, "Orange", "Netherlands", categorylist.get(0)));
        fruits.add(new Fruit("Grapes", 18, 3.49, "Grape", "Netherlands", categorylist.get(3)));
        fruits.add(new Fruit("Pineapple", 25, 2.59, "Pineapple", "Netherlands", categorylist.get(1)));
        fruits.add(new Fruit("Mango", 30, 3.99, "Mango", "Netherlands", categorylist.get(1)));
        fruits.add(new Fruit("Strawberry", 35, 4.99, "Strawberry", "Netherlands", categorylist.get(1)));
        fruits.add(new Fruit("Blueberry", 40, 5.49, "Berry", "Netherlands", categorylist.get(1)));
        fruits.add(new Fruit("Watermelon", 12, 1.89, "https://example.com/img/watermelon.jpg", "Netherlands", categorylist.get(1)));
        fruits.add(new Fruit("Peach", 28, 3.29, "https://example.com/img/peach.jpg", "Netherlands", categorylist.get(1)));
        fruits.add(new Fruit("Kiwi", 32, 2.79, "https://example.com/img/kiwi.jpg", "Netherlands", categorylist.get(1)));
        fruits.add(new Fruit("Cherry", 50, 6.49, "https://example.com/img/cherry.jpg", "Netherlands", categorylist.get(1)));
        fruits.add(new Fruit("Papaya", 22, 3.59, "https://example.com/img/papaya.jpg", "Netherlands", categorylist.get(1)));
        fruits.add(new Fruit("Coconut", 15, 2.19, "https://example.com/img/coconut.jpg", "Netherlands", categorylist.get(1)));
        fruits.add(new Fruit("Plum", 27, 2.49, "https://example.com/img/plum.jpg", "Netherlands", categorylist.get(1)));
        fruits.add(new Fruit("Lemon", 12, 1.29, "https://example.com/img/lemon.jpg", "Netherlands", categorylist.get(1)));
        fruits.add(new Fruit("Pomegranate", 45, 4.39, "https://example.com/img/pomegranate.jpg", "Netherlands", categorylist.get(1)));
        fruits.add(new Fruit("Lychee", 38, 5.29, "https://example.com/img/lychee.jpg", "Netherlands", categorylist.get(1)));
        fruits.add(new Fruit("Raspberry", 42, 5.99, "https://example.com/img/raspberry.jpg", "Netherlands", categorylist.get(1)));
        fruits.add(new Fruit("Blackberry", 44, 5.79, "https://example.com/img/blackberry.jpg", "Netherlands", categorylist.get(1)));
    }

    public List<FruitDTO> getFruits() {
        return fruits.stream().map(f -> fruitDTOMapper.apply(f)).toList();
    }

    public List<FruitDTO> getRecommendedFruits() {
        return fruits.stream()
                .limit(6)
                .map(fruitDTOMapper::apply)
                .toList();
    }

    public FruitDTO getFruit(int id) {
        return fruits.stream()
                .filter(f -> f.getId() == id)
                .map(f -> fruitDTOMapper.apply(f))
                .findFirst()
                .orElse(null);
    }

    public List<FruitDTO> getFruitsByCategory(int categoryId) {
        return fruits.stream()
                .filter(f -> f.getCategory().getId() == categoryId)
                .map(fruitDTOMapper::apply)
                .toList();
    }
}
