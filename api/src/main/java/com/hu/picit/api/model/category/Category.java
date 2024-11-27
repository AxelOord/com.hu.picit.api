package main.java.com.hu.picit.api.model.category;

import main.java.com.hu.core.model.BaseModel;

public class Category extends BaseModel {
    private String name;
    private String img;

    public Category(String name, String img) {
        this.name = name;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public String getImg() {
        return img;
    }
}