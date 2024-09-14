package dto;

import java.lang.reflect.Field;

public abstract class BaseDTO {
    private String type;
    private String id;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String toJson() {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");

        // Add type and id to the JSON
        jsonBuilder.append("\"type\":\"").append(type).append("\",");
        jsonBuilder.append("\"id\":\"").append(id).append("\",");

        // Start attributes section
        jsonBuilder.append("\"attributes\":{");

        // Use reflection to get all fields of the subclass (DTO-specific attributes)
        Field[] fields = this.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true); // Allow access to private fields
            try {
                String key = fields[i].getName(); // Get field name
                Object value = fields[i].get(this); // Get field value

                if (!key.equals("type") && !key.equals("id")) { // Exclude type and id from attributes
                    jsonBuilder.append("\"").append(key).append("\":");

                    if (value instanceof String) {
                        jsonBuilder.append("\"").append(value).append("\"");
                    } else {
                        jsonBuilder.append(value);
                    }

                    if (i < fields.length - 1) {
                        jsonBuilder.append(", ");
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        // End attributes section
        jsonBuilder.append("}");

        jsonBuilder.append("}");
        return jsonBuilder.toString();
    }
}