package com.nn.shopfinder.logic;

import com.google.android.gms.maps.model.Marker;
import com.nn.shopfinder.logic.handlers.IShopHandler;
import com.nn.shopfinder.logic.handlers.ShopHandler;
import com.nn.shopfinder.model.DataModel;
import com.nn.shopfinder.model.Location;

import java.util.List;
import java.util.Map;

/**
 * Created by Nuno on 13/04/2016.
 */
public class ShopFactory {

    private static ShopFactory instance;
    private Map<Marker,String> mapMarkers;
    private List<ShopHandler> handlers;


    public ShopFactory() {
        instance = this;
    }

    public static ShopFactory getInstance() {
        return instance;
    }

    public void addHandler(ShopHandler handler) {
        this.handlers.add(handler);
    }

    public void loadShops(){
        Location lastLoc = null;

        for(ShopHandler handler : handlers){

            lastLoc = DataModel.getInstance().getLastKnownLocation();
            if(lastLoc != null && handler.isRequiresLocation()){

            }else if(!handler.isRequiresLocation()){
                handler.loadShops();
            }
        }


    }





}
