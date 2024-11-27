package main.java.com.hu.picit.api.model.fruit;

import main.java.com.hu.core.model.BaseModel;
import main.java.com.hu.picit.api.model.category.Category;

public class Fruit extends BaseModel {
    private String name;
    private int quantity;
    private Double price;
    private String img;
    private String countryOfOrigin;
    private Category category;

    public Fruit(String name, int quantity, Double price, String img, String countryOfOrigin, Category category) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.img = img;
        this.countryOfOrigin = countryOfOrigin;
        this.category = category;
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

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public Category getCategory() {
        return category;
    }
}