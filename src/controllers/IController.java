package controllers;

public interface IController {
    // Utility method to convert singular to plural
    default String plural(String word) {
        // Basic pluralization rules
        if (word.endsWith("y") && !word.endsWith("ay") && !word.endsWith("ey") && !word.endsWith("oy")
                && !word.endsWith("uy")) {
            // Convert "y" to "ies"
            return word.substring(0, word.length() - 1) + "ies";
        } else {
            // Default case, just add "s"
            return word + "s";
        }
    }

    default String getBasePath() {
        String className = this.getClass().getSimpleName();

        // Remove "Controller" suffix
        if (className.endsWith("Controller")) {
            className = className.substring(0, className.length() - "Controller".length());
        }

        // Convert to plural form and return the API path
        return "/api/" + plural(className.toLowerCase());
    }
}
