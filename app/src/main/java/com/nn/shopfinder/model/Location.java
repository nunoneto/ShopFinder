package com.nn.shopfinder.model;

/**
 * Created by Nuno on 13/04/2016.
 */
public class Location {

    private double lattitude, longitude;

    public Location(double lattitude, double longitude) {
        this.lattitude = lattitude;
        this.longitude = longitude;
    }

    public double getLattitude() {
        return lattitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
