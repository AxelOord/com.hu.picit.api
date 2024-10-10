package main.java.com.hu.core.util;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

import main.java.com.hu.core.annotation.PathVariable;

public class ArgUtil {
    // TODO: should be moved to a utility class, maybe some design pattern
    public static Object[] resolveMethodArguments(Method method, HttpExchange exchange, Map<String, String> params)
            throws Exception {
        Parameter[] parameters = method.getParameters();
        Object[] args = new Object[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];

            if (parameter.getType().equals(HttpExchange.class)) {
                args[i] = exchange;
            } else if (parameter.isAnnotationPresent(PathVariable.class)) {
                PathVariable pathVariable = parameter.getAnnotation(PathVariable.class);
                String varName = pathVariable.value().isEmpty() ? parameter.getName() : pathVariable.value();
                String value = params.get(varName);
                if (value == null) {
                    throw new IllegalArgumentException("Missing path variable: " + varName);
                }
                args[i] = convertValue(value, parameter.getType());
            } else {
                // Handle other parameter types or throw an exception
                throw new IllegalArgumentException("Unsupported parameter type: " + parameter.getType());
            }
        }

        return args;
    }

    private static Object convertValue(String value, Class<?> targetType) {
        if (targetType.equals(String.class)) {
            return value;
        } else if (targetType.equals(int.class) || targetType.equals(Integer.class)) {
            return Integer.parseInt(value);
        } else if (targetType.equals(long.class) || targetType.equals(Long.class)) {
            return Long.parseLong(value);
        } else if (targetType.equals(double.class) || targetType.equals(Double.class)) {
            return Double.parseDouble(value);
        } else if (targetType.equals(float.class) || targetType.equals(Float.class)) {
            return Float.parseFloat(value);
        } else if (targetType.equals(boolean.class) || targetType.equals(Boolean.class)) {
            return Boolean.parseBoolean(value);
        } else {
            // Handle other types or throw an exception
            throw new IllegalArgumentException("Unsupported parameter type: " + targetType);
        }
    }
}
