package main.java.com.hu.core.model;

import main.java.com.hu.core.dto.BaseDTO;
import main.java.com.hu.core.dto.DTOHolder.DTOHolder;
import main.java.com.hu.core.dto.DTOHolder.MultipleDTOHolder;
import main.java.com.hu.core.dto.DTOHolder.SingleDTOHolder;
import main.java.com.hu.core.enums.HttpStatusCodeEnum;

import java.util.List;

public class Response {
    private final DTOHolder dtoHolder;
    private String message;
    private HttpStatusCodeEnum status;
    private Object metadata = null;

    public Response(BaseDTO dto, String message) {
        this(new SingleDTOHolder(dto), HttpStatusCodeEnum.OK, message);
    }

    public Response(List<? extends BaseDTO> dtoList, String message) {
        this(new MultipleDTOHolder(dtoList), HttpStatusCodeEnum.OK, message);
    }

    public Response(BaseDTO dto, HttpStatusCodeEnum status, String message) {
        this(new SingleDTOHolder(dto), status, message);
    }

    public Response(List<? extends BaseDTO> dtoList, HttpStatusCodeEnum status, String message) {
        this(new MultipleDTOHolder(dtoList), status, message);
    }

    private Response(DTOHolder dtoHolder, HttpStatusCodeEnum status, String message) {
        this.dtoHolder = dtoHolder;
        this.status = status;
        this.message = message;
    }

    public DTOHolder getDtoHolder() {
        return dtoHolder;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getMetadata() {
        return metadata;
    }

    public void setMetadata(Object metadata) {
        this.metadata = metadata;
    }

    public int getStatus() {
        return status.getCode();
    }

    // this is sketch, need to find a better way
    // the response object should'nt be responsible for converting to JSON
    public String toJson() {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");
    
        // Add message
        jsonBuilder.append("\"message\":\"").append(message).append("\",");
    
        // Add metadata if available
        if (metadata != null) {
            //jsonBuilder.append("\"metadata\":").append(metadata.toJson()).append(",");
        }
    
        // Convert DTOHolder to JSON (single or list)
        jsonBuilder.append("\"data\":");
        if (dtoHolder instanceof SingleDTOHolder singleHolder) {
            jsonBuilder.append(singleHolder.getDto().toJson());
        } else if (dtoHolder instanceof MultipleDTOHolder multipleHolder) {
            jsonBuilder.append("[");
            List<? extends BaseDTO> dtoList = multipleHolder.getDtoList();
            for (int i = 0; i < dtoList.size(); i++) {
                jsonBuilder.append(dtoList.get(i).toJson());
                if (i < dtoList.size() - 1) {
                    jsonBuilder.append(", ");
                }
            }
            jsonBuilder.append("]");
        }
    
        jsonBuilder.append("}");
        return jsonBuilder.toString();
    }
}