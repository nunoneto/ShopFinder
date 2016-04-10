package com.nn.shopfinder.model.shop;

/**
 * Created by Nuno on 10/04/2016.
 */
public class VodafoneShop extends GenericShop {

    public VodafoneShop(String id, String name, String description, String address, String hours, double latitude, double longitude) {
        super(id, name, description, address, hours, latitude, longitude);
    }

    //services available in vodafone stores
    public static enum services{ alargados, assistencia, mobile, negocios, portal, reparacao, tekkies};



}
