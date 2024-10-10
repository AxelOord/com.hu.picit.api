package main.java.com.hu.core.model;
import main.java.com.hu.core.enums.HttpStatusCodeEnum;

public class Response<T> {
    private String message;
    private HttpStatusCodeEnum status;
    private T data;

    public Response(String message) {
        this(HttpStatusCodeEnum.OK, message);
    }

    public Response(HttpStatusCodeEnum status, String message) {
        this.status = status;
        this.message = message;
    }

    // Getters and Setters
    public HttpStatusCodeEnum getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Response<T> setData(T data) {
        this.data = data;
        return this;
    }

    public T getData() {
        return data;
    }
}