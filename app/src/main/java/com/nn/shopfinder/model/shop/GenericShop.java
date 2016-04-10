package com.nn.shopfinder.model.shop;

/**
 * Created by Nuno on 10/04/2016.
 */
public class GenericShop {

    public GenericShop(String id, String name, String description, String address, String hours, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.hours = hours;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    private String id, name, description, address, hours;
    private double latitude, longitude;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public String getHours() {
        return hours;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
