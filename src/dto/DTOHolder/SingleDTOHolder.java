package dto.DTOHolder;

import dto.BaseDTO;

// Class to hold a single BaseDTO
public final class SingleDTOHolder implements DTOHolder {
    private final BaseDTO dto;

    public SingleDTOHolder(BaseDTO dto) {
        this.dto = dto;
    }

    public BaseDTO getDto() {
        return dto;
    }
}
