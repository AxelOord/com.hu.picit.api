package controllers;

import com.sun.net.httpserver.HttpHandler;

import enums.MethodEnum;
import models.Route;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

public abstract class BaseController implements HttpHandler, IController {
    private static List<Route> routes = new ArrayList<>();

    protected void registerRoute(MethodEnum method, String pattern, RouteHandler handler) {
        routes.add(new Route(method, pattern, handler));
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestURI = exchange.getRequestURI().toString();
        String method = exchange.getRequestMethod().toUpperCase();

        boolean patternMatched = false;

        for (Route route : routes) {
            Matcher matcher = route.getMatcher(requestURI);

            // Check if the pattern matches the request URI
            if (matcher.matches()) {
                patternMatched = true;

                // If both method and pattern match
                if (route.matches(method, requestURI)) {
                    // Extract parameters from the URI
                    Map<String, String> params = route.extractParameters(requestURI);

                    // Pass the parameters to the handler
                    route.getHandler().handle(exchange, params);
                    return;
                }
            }
        }

        // Return appropriate status codes
        if (patternMatched) {
            exchange.sendResponseHeaders(405, -1); // Method Not Allowed
        } else {
            exchange.sendResponseHeaders(404, -1); // Not Found
        }
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
        void handle(HttpExchange exchange, Map<String, String> params) throws IOException;
    }
}