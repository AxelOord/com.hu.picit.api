package models;

public abstract class BaseModel {
    static int idCounter = 0;
    private int id;

    public BaseModel() {
        this.id = idCounter++;
    }

    public int getId() {
        return id;
    }
}
