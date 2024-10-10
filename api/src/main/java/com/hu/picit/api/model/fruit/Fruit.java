package main.java.com.hu.picit.api.model.fruit;

import main.java.com.hu.core.model.BaseModel;

public class Fruit extends BaseModel {
    private String name;
    private int quantity;

    public Fruit(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }
}