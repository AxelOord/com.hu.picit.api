package main.java.com.hu.picit.api.model.location;

import main.java.com.hu.core.model.BaseModel;

public class Location extends BaseModel {
    private String name;

    public Location(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}