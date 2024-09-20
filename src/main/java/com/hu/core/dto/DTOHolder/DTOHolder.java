package main.java.com.hu.core.dto.DTOHolder;

// Define a sealed interface that permits only SingleDTOHolder and MultipleDTOHolder
public sealed interface DTOHolder permits SingleDTOHolder, MultipleDTOHolder {
}
