package com.techelevator.model.distanceMatrix;

import java.util.List;

public class DistanceMatrixResult {
    List<MatrixResponseRows> rows;
    private List<String> destination_addresses;
    private List<String> origin_addresses;

    public List<String> getDestination_addresses() {
        return destination_addresses;
    }

    public List<String> getOrigin_addresses() {
        return origin_addresses;
    }

    public List<MatrixResponseRows> getRows() {
        return rows;
    }


}
