package main.java.com.hu.picit.api.model.fruit;

import main.java.com.hu.core.model.BaseModel;

public class Fruit extends BaseModel {
    private String name;
    private int quantity;
    private Double price;
    private String img;

    public Fruit(String name, int quantity, Double price, String img) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    public String getImg() {
        return img;
    }
}