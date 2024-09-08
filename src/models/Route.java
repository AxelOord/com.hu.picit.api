package models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import controllers.BaseController.RouteHandler;
import enums.MethodEnum;;

public class Route {
    private final MethodEnum method;
    private final Pattern pattern;
    private final RouteHandler handler;

    public Route(MethodEnum method, String pattern, RouteHandler handler) {
        this.method = method;
        this.pattern = Pattern.compile(pattern);
        this.handler = handler;
    }

    public boolean matches(String method, String requestURI) {
        return this.method.name().equals(method) && pattern.matcher(requestURI).matches();
    }

    public Matcher getMatcher(String requestURI) {
        return pattern.matcher(requestURI);
    }
    public String getPattern() {
        return pattern.pattern();
    }

    public MethodEnum getMethod() {
        return method;
    }

    public RouteHandler getHandler() {
        return handler;
    }
}