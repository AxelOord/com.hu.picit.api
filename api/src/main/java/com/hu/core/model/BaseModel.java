package main.java.com.hu.core.model;

public abstract class BaseModel {
    // need to find a better way to generate ids
    static int idCounter = 0;
    private int id;

    public BaseModel() {
        this.id = idCounter++;
    }

    public int getId() {
        return id;
    }
}
