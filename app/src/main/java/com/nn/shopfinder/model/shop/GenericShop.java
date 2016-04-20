package com.nn.shopfinder.model.shop;

import java.util.List;

/**
 * Created by Nuno on 10/04/2016.
 */
public class GenericShop {

    private String id, name, description, address, hours;
    private double latitude, longitude;
    private List<String> services;
    private int iconResourceId;

    public GenericShop(String id, String name, String description, String address, String hours, double latitude, double longitude, int iconResourceId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.hours = hours;
        this.latitude = latitude;
        this.longitude = longitude;
        this.iconResourceId = iconResourceId;
    }

    public GenericShop(String id, String name, String description, String address, String hours, double latitude, double longitude, List<String> services, int iconResourceId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.hours = hours;
        this.latitude = latitude;
        this.longitude = longitude;
        this.services = services;
        this.iconResourceId = iconResourceId;
    }

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

    public int getIconResourceId() {
        return iconResourceId;
    }

    public List<String> getServices() {
        return services;
    }
}
