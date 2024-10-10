package main.java.com.hu.picit.api.controller;

import java.util.List;

import main.java.com.hu.core.annotation.Autowired;
import main.java.com.hu.core.annotation.Controller;
import main.java.com.hu.core.annotation.HttpGet;
import main.java.com.hu.core.controller.BaseController;
import main.java.com.hu.picit.api.model.location.LocationDTO;
import main.java.com.hu.picit.service.LocationService;

@Controller("/api/[controller]")
public class LocationsController extends BaseController  {
    @Autowired
    private LocationService locationService = new LocationService();

    public LocationsController() {
    }

    @HttpGet()
    private List<LocationDTO> getLocations(){
        return locationService.getLocations();
    }
}
