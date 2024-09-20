package main.java.com.hu.picit.api.controller;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.*;

import main.java.com.hu.core.annotation.Controller;
import main.java.com.hu.core.annotation.HttpGet;
import main.java.com.hu.core.controller.BaseController;
import main.java.com.hu.core.dto.AutoMapper;
import main.java.com.hu.core.model.Response;
import main.java.com.hu.picit.api.model.fruit.Fruit;
import main.java.com.hu.picit.api.model.fruit.FruitDTO;

@Controller("/api/[controller]")
public class FruitsController extends BaseController {
    private AutoMapper<Fruit, FruitDTO> mapper = new AutoMapper<>();
    private List<Fruit> fruits = new ArrayList<Fruit>();

    // Constructor to initialize the meta and fruits
    public FruitsController() {
        // Initialize the fruits array
        fruits.add(new Fruit("Apple", 10));
        fruits.add(new Fruit("Banana", 20));
        fruits.add(new Fruit("Orange", 15));
    }

    @HttpGet()
    private void getFruits(HttpExchange exchange, Map<String, String> params) throws IOException {
        List<FruitDTO> fruit = mapper.mapToDTOList(fruits, FruitDTO.class);

        Response response = new Response(fruit, "Success");

        sendResponse(exchange, response);
    }

    @HttpGet("/{id}")
    private void getFruit(HttpExchange exchange, Map<String, String> params) throws IOException {
        FruitDTO fruit = mapper.mapToDTO(fruits.get(Integer.parseInt(params.get("id"))), new FruitDTO());

        // Create Response object
        Response response = new Response(fruit, "Success");

        sendResponse(exchange, response);
    }
}