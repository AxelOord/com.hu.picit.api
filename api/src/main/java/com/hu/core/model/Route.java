package main.java.com.hu.core.model;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.java.com.hu.core.controller.BaseController;
import java.lang.reflect.Method;
import main.java.com.hu.core.enums.MethodEnum;

public class Route<T> {
    private final MethodEnum method;
    private final Pattern pattern;
    private final Method handlerMethod;
    private final BaseController<T> instance;

    // Constructor, converting {param} into regex groups
    public Route(MethodEnum method, String pattern, Method handler, BaseController<T> controller) {
        // Convert route patterns like /users/{id} to regex /users/(?<id>[^/]+)
        this.method = method;
        this.pattern = Pattern.compile(pattern.replaceAll("\\{([^/]+)\\}", "(?<$1>[^/]+)"));
        this.handlerMethod = handler;
        this.instance = controller;
    }

    public BaseController<T> getInstance() {
        return instance;
    }

    public Matcher getMatcher(String requestURI) {
        return pattern.matcher(requestURI);
    }

    public MethodEnum getHttpMethod() {
        return method;
    }

    public Method getHandlerMethod() {
        return handlerMethod;
    }

    public boolean matches(String method, String requestURI) {
        return this.method.name().equals(method) && pattern.matcher(requestURI).matches();
    }

    // Extracts parameters from the URI
    public Map<String, String> extractParameters(String requestURI) {
        Matcher matcher = pattern.matcher(requestURI);
        Map<String, String> params = new HashMap<>();

        // If the requestURI matches the pattern
        if (matcher.matches()) {
            // Loop through the named groups in the pattern to extract their values
            for (String name : pattern.pattern().split("\\(\\?<")) {
                if (name.contains(">")) {
                    // Extract the group name (the text before the '>')
                    String groupName = name.substring(0, name.indexOf(">"));
                    params.put(groupName, matcher.group(groupName));
                }
            }
        }

        return params;
    }
}