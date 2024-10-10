package main.java.com.hu.core.util;

import java.lang.reflect.*;
import java.util.*;

import main.java.com.hu.core.dto.DTO;
import main.java.com.hu.core.model.Response;

public class JsonUtil {

    // Serialize the Response object to JSON
    public static String toJson(Response<?> response) {
        StringBuilder json = new StringBuilder();
        json.append("{");

        json.append("\"message\": \"").append(escapeJsonString(response.getMessage())).append("\",");
        json.append("\"status\": ").append(response.getStatus().getCode()).append(",");

        Object data = response.getData();
        if (data != null) {
            json.append("\"data\": ");
            json.append(serializeObject(data));
        } else {
            json.append("\"data\": null");
        }

        json.append("}");
        return json.toString();
    }

    // Deserialize JSON to a Response object
    public static <T> Response<T> fromJson(String jsonString, Class<T> dataType) throws Exception {
        Response<T> response = new Response<>("Parsed Message");

        return response;
    }

    private static String serializeObject(Object obj) {
        if (obj instanceof List<?>) {
            return serializeList((List<?>) obj);
        } else if (obj instanceof DTO) {
            return serializeDTO((DTO) obj);
        } else {
            return "null";
        }
    }

    private static String serializeList(List<?> list) {
        StringBuilder json = new StringBuilder();
        json.append("[");
        for (Object item : list) {
            json.append(serializeObject(item)).append(",");
        }
        // Remove trailing comma
        if (json.charAt(json.length() - 1) == ',') {
            json.deleteCharAt(json.length() - 1);
        }
        json.append("]");
        return json.toString();
    }

    private static String serializeDTO(DTO dto) {
        StringBuilder json = new StringBuilder();
        json.append("{");

        Class<?> dtoClass = dto.getClass();
        
        String typeName = dtoClass.getSimpleName();
        if (typeName.endsWith("DTO")) {
            typeName = typeName.substring(0, typeName.length() - 3); // Remove the "DTO" suffix
        }
        typeName = typeName.toLowerCase();

        json.append("\"type\": \"").append(typeName).append("\",");

        // Attempt to get 'id' field
        Object idValue = getFieldValue(dto, "id");
        if (idValue != null) {
            json.append("\"id\": ").append(formatValue(idValue)).append(",");
        }

        json.append("\"attributes\": {");
        Field[] fields = dtoClass.getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            if (fieldName.equals("id") || fieldName.startsWith("$")) {
                continue;
            }
            Object value = getFieldValue(dto, fieldName);
            json.append("\"").append(fieldName).append("\":").append(formatValue(value)).append(",");
        }
        // Remove trailing comma
        if (json.charAt(json.length() - 1) == ',') {
            json.deleteCharAt(json.length() - 1);
        }
        json.append("}");

        json.append("}");
        return json.toString();
    }

    private static Object getFieldValue(Object obj, String fieldName) {
        try {
            Method method = obj.getClass().getDeclaredMethod(fieldName);
            return method.invoke(obj);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            return null;
        }
    }

    private static String formatValue(Object value) {
        if (value == null) {
            return "null";
        } else if (value instanceof String) {
            return "\"" + escapeJsonString((String) value) + "\"";
        } else if (value instanceof Number || value instanceof Boolean) {
            return value.toString();
        } else if (value instanceof DTO) {
            return serializeDTO((DTO) value);
        } else if (value instanceof List<?>) {
            return serializeList((List<?>) value);
        } else {
            return "\"" + escapeJsonString(value.toString()) + "\"";
        }
    }

    private static String escapeJsonString(String str) {
        return str.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}