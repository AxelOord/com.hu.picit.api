package main.java.com.hu.core.util;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import main.java.com.hu.core.annotation.Controller;
import main.java.com.hu.core.controller.BaseController;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.JarURLConnection;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ControllerUtil {

    public static List<BaseController<?>> findControllers(String packageName) throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException, InvocationTargetException, IllegalArgumentException, NoSuchMethodException, SecurityException {
        List<BaseController<?>> controllers = new ArrayList<>();
        String path = packageName.replace('.', '/');
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        // Get all resources (directories and jar files) that match the package name
        Enumeration<URL> resources = classLoader.getResources(path);

        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();

            if (resource.getProtocol().equals("file")) {
                // Scanning classes from a directory
                String filePath = URLDecoder.decode(resource.getFile(), StandardCharsets.UTF_8);
                File directory = new File(filePath);
                if (directory.exists()) {
                    findClassesInDirectory(packageName, directory, controllers);
                }
            } else if (resource.getProtocol().equals("jar")) {
                // Scanning classes from a jar file
                JarURLConnection jarConn = (JarURLConnection) resource.openConnection();
                try (JarFile jarFile = jarConn.getJarFile()) {
                    findClassesInJar(packageName, jarFile, controllers);
                }
            }
        }

        return controllers;
    }

    private static void findClassesInDirectory(String packageName, File directory, List<BaseController<?>> controllers)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, IllegalArgumentException, NoSuchMethodException, SecurityException {
        if (!directory.exists())
            return;

        File[] files = directory.listFiles();
        if (files == null)
            return;

        for (File file : files) {
            if (file.isDirectory()) {
                // Recursively scan sub-packages
                findClassesInDirectory(packageName + "." + file.getName(), file, controllers);
            } else if (file.getName().endsWith(".class")) {
                // Load the class
                String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                Class<?> clazz = Class.forName(className);

                // Check if the class is annotated with @Controller and extends BaseController
                if (clazz.isAnnotationPresent(Controller.class) && BaseController.class.isAssignableFrom(clazz)) {
                    BaseController<?> controllerInstance = (BaseController<?>) clazz.getDeclaredConstructor().newInstance();
                    controllers.add(controllerInstance);
                }
            }
        }
    }

    private static void findClassesInJar(String packageName, JarFile jarFile, List<BaseController<?>> controllers)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Enumeration<JarEntry> entries = jarFile.entries();
        String packagePath = packageName.replace('.', '/');

        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();

            if (entry.isDirectory()) {
                continue;
            }

            String entryName = entry.getName();
            if (entryName.startsWith(packagePath) && entryName.endsWith(".class")) {
                // Convert to class name
                String className = entryName.replace('/', '.').substring(0, entryName.length() - 6);
                Class<?> clazz = Class.forName(className);

                // Check if the class is annotated with @Controller and extends BaseController
                if (clazz.isAnnotationPresent(Controller.class) && BaseController.class.isAssignableFrom(clazz)) {
                    BaseController<?> controllerInstance = (BaseController<?>) clazz.getDeclaredConstructor().newInstance();
                    controllers.add(controllerInstance);
                }
            }
        }
    }
}