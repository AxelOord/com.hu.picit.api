package services;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import annotations.Controller;

public class ControllerService {
    public static List<Class<?>> findControllers(String packageName) throws ClassNotFoundException {
        // Construct the package path
        List<Class<?>> controllerClasses = new ArrayList<>();
        String path = packageName.replace('.', '/');
        URL packageUrl = Thread.currentThread().getContextClassLoader().getResource(path);

        if (packageUrl != null) {
            // Get the directory containing the package
            File directory = new File(packageUrl.getFile());
            if (directory.exists()) {
                // Iterate through the files in the directory
                for (String file : directory.list()) {
                    if (file.endsWith(".class")) {
                        // Construct the class name
                        String className = packageName + '.' + file.substring(0, file.length() - 6);
                        Class<?> clazz = Class.forName(className);

                        // Check if the class is annotated with @Controller
                        if (clazz.isAnnotationPresent(Controller.class)) {
                            controllerClasses.add(clazz);
                        }
                    }
                }
            }
        }

        return controllerClasses;
    }
}