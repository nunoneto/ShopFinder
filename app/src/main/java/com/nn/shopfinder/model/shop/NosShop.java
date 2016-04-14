package com.nn.shopfinder.model.shop;

import java.util.List;

/**
 * Created by NB20301 on 14/04/2016.
 */
public class NosShop extends GenericShop {

    public NosShop(String id, String name, String description, String address, String hours, double latitude, double longitude) {
        super(id, name, description, address, hours, latitude, longitude);
    }

    public NosShop(String id, String name, String description, String address, String hours, double latitude, double longitude, List<String> services) {
        super(id, name, description, address, hours, latitude, longitude, services);
    }

}
