package controllers;

import com.sun.net.httpserver.HttpExchange;
import enums.MethodEnum;
import java.io.IOException;

public class HelloController extends BaseController implements IController {

    public HelloController() {
        // TODO: implement a builder pattern for routes
        registerRoute(MethodEnum.GET, getBasePath(), this::handleHelloWorld);
        registerRoute(MethodEnum.GET, getBasePath() + "/2", this::handleHelloWorld2);
    }

    private void handleHelloWorld(HttpExchange exchange) throws IOException {
        sendResponse(exchange, "Hello, World!");
    }

    private void handleHelloWorld2(HttpExchange exchange) throws IOException {
        sendResponse(exchange, "Hello, Second world!");
    }
}