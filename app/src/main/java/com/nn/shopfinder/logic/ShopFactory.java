package com.nn.shopfinder.logic;

import android.location.Location;

import com.nn.shopfinder.logic.handlers.AbstractShopHandler;
import com.nn.shopfinder.logic.handlers.OnShopsLoadedCallback;
import com.nn.shopfinder.logic.handlers.impl.MeoHandler;
import com.nn.shopfinder.logic.handlers.impl.NosHandler;
import com.nn.shopfinder.logic.handlers.impl.VodafoneHandler;
import com.nn.shopfinder.model.DataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nuno on 13/04/2016.
 */
public class ShopFactory{

    private static ShopFactory instance;
    private List<AbstractShopHandler> handlers;
    private OnShopsLoadedCallback storesLoadedCallback;


    public ShopFactory(OnShopsLoadedCallback storesLoadedCallback) {
        instance = this;
        this.storesLoadedCallback = storesLoadedCallback;
        initHandlers();
    }

    private void initHandlers() {
        //add handlers here
        handlers = new ArrayList<>();
        handlers.add(new VodafoneHandler(false, storesLoadedCallback));
        handlers.add(new NosHandler(false, storesLoadedCallback));
        handlers.add(new MeoHandler(false, storesLoadedCallback));
    }

    public static ShopFactory getInstance() {
        return instance;
    }

    public void addHandler(AbstractShopHandler handler) {
        this.handlers.add(handler);
    }

    public void loadShops(){
        Location lastLoc = null;
        for(AbstractShopHandler handler : handlers){
            lastLoc = DataModel.getInstance().getLastKnownLocation();
            if((lastLoc != null && handler.isRequiresLocation() && !handler.isLoaded()) || (!handler.isRequiresLocation() && !handler.isLoaded())){
                handler.loadShops();
            }
        }
    }

}
