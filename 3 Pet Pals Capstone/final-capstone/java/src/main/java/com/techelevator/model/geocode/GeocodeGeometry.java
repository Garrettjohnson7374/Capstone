package com.techelevator.model.geocode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.techelevator.model.distanceMatrix.Location;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeocodeGeometry {
    @JsonProperty("location")
    Location geocodeLocation;
    public GeocodeGeometry() {
    }
    public Location getLocation() {
        return geocodeLocation;
    }
    public void setGeocodeLocation(Location geocodeLocation) {
        this.geocodeLocation = geocodeLocation;
    }
}
