package models;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import controllers.RouterController.RouteHandler;
import enums.MethodEnum;

public class Route {
    private final MethodEnum method;
    private final Pattern pattern;
    private final RouteHandler handler;

    // Constructor, converting {param} into regex groups
    public Route(MethodEnum method, String pattern, RouteHandler handler) {
        // Convert route patterns like /users/{id} to regex /users/(?<id>[^/]+)
        this.method = method;
        this.pattern = Pattern.compile(pattern.replaceAll("\\{([^/]+)\\}", "(?<$1>[^/]+)"));
        this.handler = handler;
    }

    public Matcher getMatcher(String requestURI) {
        return pattern.matcher(requestURI);
    }

    public MethodEnum getMethod() {
        return method;
    }

    public RouteHandler getHandler() {
        return handler;
    }

    // Matches both the method and the pattern
    public boolean matches(String method, String requestURI) {
        return this.method.name().equals(method) && pattern.matcher(requestURI).matches();
    }

    // Extracts parameters from the URI
    public Map<String, String> extractParameters(String requestURI) {
        // Use a Matcher to check if the requestURI matches the pattern
        Matcher matcher = pattern.matcher(requestURI);
        // Create a new HashMap to store the extracted parameters
        Map<String, String> params = new HashMap<>();

        // If the requestURI matches the pattern
        if (matcher.matches()) {
            // Loop through the named groups in the pattern to extract their values
            for (String name : pattern.pattern().split("\\(\\?<")) {
                // Check if the part contains a named group (denoted by '>')
                if (name.contains(">")) {
                    // Extract the group name (the text before the '>')
                    String groupName = name.substring(0, name.indexOf(">"));
                    // Use the group name to get the corresponding value from the matcher
                    params.put(groupName, matcher.group(groupName));
                }
            }
        }
        // Return the map with the extracted parameters
        return params;
    }
}