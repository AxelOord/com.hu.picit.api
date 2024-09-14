package controllers;

import com.sun.net.httpserver.HttpExchange;

import annotations.*;
import models.*;

import java.io.IOException;
import java.util.*;

import dto.AutoMapper;
import dto.FruitDTO;

@Controller("/api/[controller]")
public class ResourcesController extends BaseController {
    //private List<String> meta;
    private AutoMapper<Fruit, FruitDTO> mapper = new AutoMapper<>();
    private List<Fruit> fruits = new ArrayList<Fruit>();

    // Constructor to initialize the meta and fruits
    public ResourcesController() {
        //meta = Arrays.asList("meta1", "meta2");

        // Initialize the fruits array
        fruits.add(new Fruit("Apple", 10));
        fruits.add(new Fruit("Banana", 20));
        fruits.add(new Fruit("Orange", 15));
    }

    @HttpGet()
    private void getResources(HttpExchange exchange, Map<String, String> params) throws IOException {
        List<FruitDTO> fruit = mapper.mapToDTOList(fruits, FruitDTO.class);

        Response response = new Response(fruit, "Success");

        sendResponse(exchange, response);
    }

    @HttpGet("/{id}")
    private void getresource(HttpExchange exchange, Map<String, String> params) throws IOException {
        FruitDTO fruit = mapper.mapToDTO(fruits.get(Integer.parseInt(params.get("id"))), new FruitDTO());

        // Create Response object
        Response response = new Response(fruit, "Success");

        sendResponse(exchange, response);
    }
}