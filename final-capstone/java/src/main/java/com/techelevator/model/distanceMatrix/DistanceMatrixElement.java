package com.techelevator.model.distanceMatrix;

import com.techelevator.model.distanceMatrix.DistanceMatrixDistance;
import com.techelevator.model.distanceMatrix.DistanceMatrixDuration;

public class DistanceMatrixElement {
    DistanceMatrixDistance distance;
    DistanceMatrixDuration duration;

    public DistanceMatrixDistance getDistance() {
        return distance;
    }

    public DistanceMatrixDuration getDuration() {
        return duration;
    }
}
