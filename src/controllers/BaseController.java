package controllers;

import models.Response;

import com.sun.net.httpserver.HttpExchange;

import annotations.Controller;
import enums.HttpStatusCodeEnum;

import java.io.IOException;
import java.io.OutputStream;

public abstract class BaseController {
    public void sendResponse(HttpExchange exchange, Response response) throws IOException {
        try {
            String json = response.toJson();
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(response.getStatus(), json.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(json.getBytes());
            }
        } catch (IOException e) {
            // TODO: better error handling
            e.printStackTrace();
            exchange.sendResponseHeaders(HttpStatusCodeEnum.BAD_REQUEST.getCode(), 0); // Internal Server Error
        } finally {
            exchange.close();
        }
    }

    public String getBasePath() {
        String className = this.getClass().getSimpleName();

        // Check if the class is annotated with @Controller
        if (this.getClass().isAnnotationPresent(Controller.class)) {
            Controller controllerAnnotation = this.getClass().getAnnotation(Controller.class);
            String basePath = controllerAnnotation.value();

            // If basePath contains the [controller] placeholder, replace it
            if (basePath.contains("[controller]")) {
                // Remove "Controller" suffix from the class name
                if (className.endsWith("Controller")) {
                    className = className.replace("Controller", "");
                }

                // Replace [controller] with the resource name
                basePath = basePath.replace("[controller]", className.toLowerCase());
            }

            return basePath;
        }

        // Default fallback in case there's no @Controller annotation, which should never happen
        return "/api/" + className.toLowerCase();
    }
}