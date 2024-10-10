package main.java.com.hu.picit.service;

import java.util.ArrayList;
import java.util.List;

import main.java.com.hu.picit.api.model.location.Location;
import main.java.com.hu.picit.api.model.location.LocationDTO;
import main.java.com.hu.picit.api.model.location.LocationDTOMapper;

public class LocationService {
    private static List<Location> locations = new ArrayList<Location>();
    private LocationDTOMapper locationDTOMapper = new LocationDTOMapper();

    static {
        // Initialize the fruits array
        locations.add(new Location("Amsterdam"));
        locations.add(new Location("Haarlem"));
        locations.add(new Location("Leiden"));
        locations.add(new Location("Groningen"));
    }

    public List<LocationDTO> getLocations() {
        return locations.stream().map(l -> locationDTOMapper.apply(l)).toList();
    }

    public LocationDTO getLocation(int id) {
        return locations.stream()
                .filter(l -> l.getId() == id)
                .map(l -> locationDTOMapper.apply(l))
                .findFirst()
                .orElse(null);
    }
}
