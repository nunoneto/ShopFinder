package com.nn.shopfinder.model;

import com.nn.shopfinder.services.response.VodafoneResponse;

import java.util.List;

/**
 * Created by Nuno on 10/04/2016.
 */
public class DataModel {

    private static DataModel instance = null;

    public static DataModel getInstance(){
        return instance;
    }

    public DataModel(){
        instance = this;
    }

    //Vodafone
    private List<VodafoneResponse.VodafoneShop> vodafoneShops;

}
