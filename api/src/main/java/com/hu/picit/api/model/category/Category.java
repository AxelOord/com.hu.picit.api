package main.java.com.hu.picit.api.model.category;

import main.java.com.hu.core.model.BaseModel;

public class Category extends BaseModel {
    private String name;

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}