package main.java.com.hu.picit.api.model.fruit;

import main.java.com.hu.core.dto.BaseDTO;

public class FruitDTO extends BaseDTO {
    private String name;
    private int quantity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
