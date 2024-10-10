package main.java.com.hu.core.controller;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import main.java.com.hu.core.annotation.HttpMethod;
import main.java.com.hu.core.enums.HttpStatusCodeEnum;
import main.java.com.hu.core.enums.MethodEnum;
import main.java.com.hu.core.model.Route;
import main.java.com.hu.core.util.ArgUtil;
import main.java.com.hu.core.util.ControllerUtil;

public class RouterController implements HttpHandler {
    private static List<Route> routes = new ArrayList<>();

    public RouterController() {
        registerRoutesFromPackage();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestURI = exchange.getRequestURI().toString();
        String requestMethod = exchange.getRequestMethod().toUpperCase();

        boolean patternMatched = false;

        for (Route route : routes) {
            Matcher matcher = route.getMatcher(requestURI);

            // Check if the pattern matches the request URI
            if (matcher.matches()) {
                patternMatched = true;

                // If both method and pattern match
                if (route.matches(requestMethod, requestURI)) {
                    Map<String, String> params = route.extractParameters(requestURI);

                    try {
                        Object[] methodArgs = ArgUtil.resolveMethodArguments(route.getHandlerMethod(), exchange,
                                params);

                        // Invoke the handler method
                        route.getHandlerMethod().setAccessible(true);
                        Object result = route.getHandlerMethod().invoke(route.getInstance(), methodArgs);

                        route.getInstance().sendResponse(exchange, result);

                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                        exchange.sendResponseHeaders(HttpStatusCodeEnum.BAD_REQUEST.getCode(), -1);
                    } catch (Exception e) {
                        e.printStackTrace();
                        exchange.sendResponseHeaders(HttpStatusCodeEnum.INTERNAL_SERVER_ERROR.getCode(), -1);
                    }
                    return;
                }
            }
        }

        if (patternMatched) {
            // The pattern matched but the method did not
            exchange.sendResponseHeaders(HttpStatusCodeEnum.NOT_ALLOWED.getCode(), -1);
        } else {
            exchange.sendResponseHeaders(HttpStatusCodeEnum.NOT_FOUND.getCode(), -1);
        }
    }

    public void registerRoutesFromPackage() {
        try {
            // Find all controller classes in the controllers package
            List<Class<?>> controllers = ControllerUtil.findControllers("main.java.com.hu.picit.api.controller");

            for (Class<?> controllerClass : controllers) {
                // Create an instance of the controller
                BaseController controllerInstance = (BaseController) controllerClass.getDeclaredConstructor()
                        .newInstance();

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
            // Check if any annotations on the method are themselves annotated with
            // @HttpMethod
            for (Annotation annotation : method.getDeclaredAnnotations()) {
                HttpMethod routeInfo = annotation.annotationType().getAnnotation(HttpMethod.class);

                if (routeInfo != null) {
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

                    registerRoute(methodEnum, route, controller, method);
                }
            }
        }
    }

    protected void registerRoute(MethodEnum method, String pattern, BaseController controller, Method handler) {
        routes.add(new Route(method, pattern, handler, controller));
    }
}
