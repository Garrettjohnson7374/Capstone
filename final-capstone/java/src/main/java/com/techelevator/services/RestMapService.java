package com.techelevator.services;

import com.techelevator.model.distanceMatrix.DistanceMatrixElement;
import com.techelevator.model.distanceMatrix.DistanceMatrixResult;
import com.techelevator.model.geocode.GeocodeResult;
import com.techelevator.model.distanceMatrix.Location;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class RestMapService implements MapService{

    private static final String GEOCODE_URL = "https://maps.googleapis.com/maps/api/geocode/json?";
    private static final String DISTANCE_MATRIX_URL = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial";
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public Location getLocation(String address, String city, String state) {
        String fullAddress = formatFullAddress(address, city, state);
        GeocodeResult geocodeResult = null;
        try {
            geocodeResult = restTemplate.getForObject(GEOCODE_URL + "address=" +  fullAddress + "&key=" + System.getenv("GOOGLE_MAPS_API_KEY"), GeocodeResult.class);
        } catch (RestClientResponseException ex) {
            System.out.println(ex.getRawStatusCode() + " : " + ex.getStatusText());
        } catch (ResourceAccessException ex) {
            System.out.println(ex.getMessage());
        }
        assert geocodeResult != null;

        return geocodeResult.getResults().get(0).getGeometry().getLocation();
    }

    @Override
    public List<String> getDistances(String origin, String destinations) {
        DistanceMatrixResult distanceMatrixResult = null;
        try {
            distanceMatrixResult = restTemplate.getForObject(DISTANCE_MATRIX_URL + "&origins=" + origin + "&destinations=" + destinations + "&key=" + System.getenv("GOOGLE_MAPS_API_KEY"), DistanceMatrixResult.class);
        }catch (RestClientResponseException ex) {
            System.out.println(ex.getRawStatusCode() + " : " + ex.getStatusText());
        } catch (ResourceAccessException ex) {
            System.out.println(ex.getMessage());
        }
        List<String> distances = new ArrayList<>();
        for (DistanceMatrixElement element :  distanceMatrixResult.getRows().get(0).getElements()){
            distances.add(element.getDistance().getText());
        }

        return distances;
    }

    private String formatFullAddress(String address, String city, String state){
        String fullAddress ="";
        String fixedAddress = String.join("+", address.split(" "));
        String fixedCity = String.join("+", city.split(" "));
        String fixedState= String.join("+", state.split(" "));
        fullAddress = fixedAddress + "+" + fixedCity + "+" + fixedState;

        return fullAddress;
    }
}
