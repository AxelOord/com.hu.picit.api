package controllers;

import annotations.HttpMethod;
import enums.MethodEnum;
import models.Route;
import services.ControllerService;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.util.regex.Matcher;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

public class RouterController implements HttpHandler {
    private static List<Route> routes = new ArrayList<>();

    public RouterController() {
        registerRoutesFromPackage();
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

    public void registerRoutesFromPackage() {
        try {
            // Find all controller classes in the controllers package
            List<Class<?>> controllers = ControllerService.findControllers("controllers");

            for (Class<?> controllerClass : controllers) {
                // Create an instance of the controller
                BaseController controllerInstance = (BaseController) controllerClass.getDeclaredConstructor().newInstance();

                // Register routes for the controller instance
                registerAnnotatedRoutes(controllerInstance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void registerAnnotatedRoutes(BaseController controller) {
        Method[] methods = controller.getClass().getDeclaredMethods();

        for (Method method : methods) {
            // Check if any annotations on the method are themselves annotated with @HttpMethod
            for (Annotation annotation : method.getDeclaredAnnotations()) {
                HttpMethod routeInfo = annotation.annotationType().getAnnotation(HttpMethod.class);

                if (routeInfo != null) {
                    // Extract the HTTP method (GET, POST, etc.) and route path
                    MethodEnum methodEnum = routeInfo.method();
                    String path = "";

                    try {
                        // Get the 'value' (route path) of the annotation
                        Method valueMethod = annotation.annotationType().getMethod("value");
                        path = (String) valueMethod.invoke(annotation);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // If no path is provided in the annotation, use the base path
                    String route = path.isEmpty() ? controller.getBasePath() : controller.getBasePath() + path;

                    // Register the route dynamically
                    registerRoute(methodEnum, route, (exchange, params) -> {
                        try {
                            method.setAccessible(true);
                            method.invoke(controller, exchange, params);
                        } catch (Exception e) {
                            exchange.sendResponseHeaders(500, -1);
                            e.printStackTrace();
                        }
                    });
                }
            }
        }
    }

    protected void registerRoute(MethodEnum method, String pattern, RouteHandler handler) {
        routes.add(new Route(method, pattern, handler));
    }

    @FunctionalInterface
    public interface RouteHandler {
        void handle(HttpExchange exchange, Map<String, String> params) throws IOException;
    }
}
