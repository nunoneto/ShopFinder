package com.nn.shopfinder.model.shop;

import java.util.List;

/**
 * Created by Nuno on 10/04/2016.
 */
public class VodafoneShop extends GenericShop {


    public VodafoneShop(String id, String name, String description, String address, String hours, double latitude, double longitude, int iconResourceId) {
        super(id, name, description, address, hours, latitude, longitude, iconResourceId);
    }

    public VodafoneShop(String id, String name, String description, String address, String hours, double latitude, double longitude, List<String> services, int iconResourceId) {
        super(id, name, description, address, hours, latitude, longitude, services, iconResourceId);
    }
}
