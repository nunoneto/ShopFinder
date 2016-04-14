package com.nn.shopfinder.model.shop;

import java.util.List;

/**
 * Created by Nuno on 10/04/2016.
 */
public class VodafoneShop extends GenericShop {

    public VodafoneShop(String id, String name, String description, String address, String hours, double latitude, double longitude) {
        super(id, name, description, address, hours, latitude, longitude);
    }

    public VodafoneShop(String id, String name, String description, String address, String hours, double latitude, double longitude, List<String> services) {
        super(id, name, description, address, hours, latitude, longitude, services);
    }

}
