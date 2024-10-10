package main.java.com.hu.picit.api.model.location;

import java.util.function.Function;

public class LocationDTOMapper implements Function<Location, LocationDTO> {
    @Override
    public LocationDTO apply(Location location) {
        return new LocationDTO(location.getId(), location.getName());
    }
}
