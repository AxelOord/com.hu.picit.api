package controllers;

public interface IController {
    // TODO: implement convert to plural, for rest naming conventions
    default String getBasePath() {
        String className = this.getClass().getSimpleName();
        // Remove "Controller" suffix and convert to lowercase
        if (className.endsWith("Controller")) {
            className = className.substring(0, className.length() - "Controller".length());
        }
        return "/api/" + className.toLowerCase();
    }
}
