package com.nn.shopfinder.model;

import android.location.Location;

import com.nn.shopfinder.model.shop.GenericShop;
import com.nn.shopfinder.model.shop.MeoShop;
import com.nn.shopfinder.model.shop.NosShop;
import com.nn.shopfinder.model.shop.VodafoneShop;

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

    private List<VodafoneShop> vodafoneShops;
    private List<NosShop> nosShops;
    private List<MeoShop> meoShops;

    private Location lastKnownLocation;

    public List<? extends GenericShop> getAllShops() {
        return vodafoneShops;
    }

    public List<VodafoneShop> getVodafoneShops() {
        return vodafoneShops;
    }

    public void setVodafoneShops(List<VodafoneShop> vodafoneShops) {
        this.vodafoneShops = vodafoneShops;
    }

    public GenericShop getShopByID(String shopId){

        for(VodafoneShop vdfShop : vodafoneShops)
            if(vdfShop.getId().equals(shopId))
                return vdfShop;

        return null;
    }

    public Location getLastKnownLocation() {
        return lastKnownLocation;
    }

    public void setLastKnownLocation(Location lastKnownLocation) {
        this.lastKnownLocation = lastKnownLocation;
    }

    public List<NosShop> getNosShops() {
        return nosShops;
    }

    public void setNosShops(List<NosShop> nosShops) {
        this.nosShops = nosShops;
    }

    public List<MeoShop> getMeoShops() {
        return meoShops;
    }

    public void setMeoShops(List<MeoShop> meoShops) {
        this.meoShops = meoShops;
    }
}
