package dto.DTOHolder;

import dto.BaseDTO;
import java.util.*;

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