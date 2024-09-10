package controllers;

import com.sun.net.httpserver.HttpExchange;

import annotations.HttpRoute;
import enums.MethodEnum;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

public class RouterController extends BaseController {

    public RouterController(List<BaseController> controllers) {
        // Automatically register all controllers by their base path
        for (BaseController controller : controllers) {
            registerAnnotatedRoutes(controller);
        }
    }

    protected void registerAnnotatedRoutes(BaseController controller) {
        Method[] methods = controller.getClass().getDeclaredMethods();

        for (Method method : methods) {
            // Check if any annotations on the method are themselves annotated with
            // @HttpRoute
            for (Annotation annotation : method.getDeclaredAnnotations()) {
                HttpRoute routeInfo = annotation.annotationType().getAnnotation(HttpRoute.class);

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
                    registerRoute(methodEnum, route, (exchange, params)-> {
                        try {
                            method.setAccessible(true);
                            method.invoke(controller, exchange, params);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        }
    }

    // DELETE: This method is not needed
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // BaseController logic handles the request
        super.handle(exchange);
    }
}
