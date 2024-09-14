package dto;

import java.lang.reflect.Field;

import models.BaseModel;
import java.util.*;

public class AutoMapper<M extends BaseModel, D extends BaseDTO> {

    public D mapToDTO(M model, D dto) {
        // Automatically set the type from the model class name
        dto.setType(model.getClass().getSimpleName().toLowerCase());

        // Automatically set the id from the model
        dto.setId(String.valueOf(model.getId()));

        // Map matching fields between the model and the DTO using reflection
        Field[] modelFields = model.getClass().getDeclaredFields();
        Field[] dtoFields = dto.getClass().getDeclaredFields();

        for (Field modelField : modelFields) {
            modelField.setAccessible(true);
            for (Field dtoField : dtoFields) {
                dtoField.setAccessible(true);
                if (modelField.getName().equals(dtoField.getName()) &&
                    modelField.getType().equals(dtoField.getType())) {
                    try {
                        dtoField.set(dto, modelField.get(model));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return dto;
    }

    // New method to map a list of models to a list of DTOs
    public List<D> mapToDTOList(List<M> models, Class<D> dtoClass) {
        List<D> dtoList = new ArrayList<>();
        for (M model : models) {
            try {
                D dto = dtoClass.getDeclaredConstructor().newInstance();
                dtoList.add(mapToDTO(model, dto));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dtoList;
    }
}