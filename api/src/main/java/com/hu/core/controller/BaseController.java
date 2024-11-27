package main.java.com.hu.core.controller;

import com.sun.net.httpserver.HttpExchange;

import main.java.com.hu.core.annotation.Controller;
import main.java.com.hu.core.enums.HttpStatusCodeEnum;
import main.java.com.hu.core.model.Response;
import main.java.com.hu.core.util.JsonUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public abstract class BaseController<T> {
    public void sendResponse(HttpExchange exchange, T result) throws IOException {
        try {
            if (result == null) {
                // Send a 404 Not Found response if result is null
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(HttpStatusCodeEnum.NOT_FOUND.getCode(), -1);
                return;
            }

            Response<T> response = new Response<T>("Success").setData(result);

            String json = JsonUtil.toJson(response);
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(response.getStatus().getCode(), json.getBytes(StandardCharsets.UTF_8).length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(json.getBytes(StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(HttpStatusCodeEnum.BAD_REQUEST.getCode(), -1); // Bad Request
        } finally {
            exchange.close();
        }
    }

    public String getBasePath() {
        String className = this.getClass().getSimpleName();

        if (this.getClass().isAnnotationPresent(Controller.class)) {
            Controller controllerAnnotation = this.getClass().getAnnotation(Controller.class);
            String basePath = controllerAnnotation.value();

            if (basePath.contains("[controller]")) {
                if (className.endsWith("Controller")) {
                    className = className.replace("Controller", "");
                }

                basePath = basePath.replace("[controller]", className.toLowerCase());
            }

            return basePath;
        }

        // Default fallback in case there's no @Controller annotation, which should
        // never happen
        return "/api/" + className.toLowerCase();
    }
}