package main.java.com.hu.core.dto.DTOHolder;

import java.util.*;

import main.java.com.hu.core.dto.BaseDTO;

// Class to hold a list of BaseDTOs
public final class MultipleDTOHolder implements DTOHolder {
    private final List<? extends BaseDTO> dtoList;

    public MultipleDTOHolder(List<? extends BaseDTO> dtoList) {
        this.dtoList = dtoList;
    }

    public List<? extends BaseDTO> getDtoList() {
        return dtoList;
    }
}