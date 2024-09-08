package controllers;

import com.sun.net.httpserver.HttpHandler;

import enums.MethodEnum;
import models.Route;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class BaseController implements HttpHandler {
    private static List<Route> routes = new ArrayList<>();

    protected void registerRoute(MethodEnum method, String pattern, RouteHandler handler) {
        routes.add(new Route(method, pattern, handler));
    }

    // Handle the request
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestURI = exchange.getRequestURI().toString();
        String method = exchange.getRequestMethod().toUpperCase();

        for (Route route : routes) {
            Pattern regexPattern = Pattern.compile(route.getPattern());
            Matcher matcher = regexPattern.matcher(requestURI);

            // TODO: 
            // Implement route parameters
            // Check if pattern matches the request URI
            // Check if the method matches the request method
            // If method doesn't match, check other routes with the same pattern
            // If pattern mathces but no route has matching method, return 405
            
            // Should also proboably be in the route class
            if (matcher.matches()) {
                if (!method.equals(route.getMethod().toString())) {
                    exchange.sendResponseHeaders(405, -1);
                    return;
                }
                
                route.getHandler().handle(exchange);
                return;
            }
        }

        exchange.sendResponseHeaders(404, -1);
    }

    // FIXME: should probably have a better name
    public void registerRoutesWithBasePath(String basePath) {
        // Create a new list to hold the updated routes, prevents concurrent modification
        List<Route> newRoutes = new ArrayList<>();

        for (Route route : routes) {
            String updatedPattern = basePath + route.getPattern();
            newRoutes.add(new Route(route.getMethod(), updatedPattern, route.getHandler()));
        }

        // Add new routes to the routes list after iteration
        routes.addAll(newRoutes);
    }

    // TODO: implement error handling, and different response codes and maybe handling of types
    // TODO: implement a way to send JSON responses
    public void sendResponse(HttpExchange exchange, String response) throws IOException {
        exchange.sendResponseHeaders(200, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    @FunctionalInterface
    public interface RouteHandler {
        void handle(HttpExchange exchange) throws IOException;
    }
}