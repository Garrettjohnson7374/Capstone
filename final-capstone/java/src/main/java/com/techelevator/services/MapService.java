package com.techelevator.services;

import com.techelevator.model.distanceMatrix.Location;

import java.util.List;

public interface MapService {

    Location getLocation(String address, String city, String state);

    List<String> getDistances(String origin, String destinations);
}
