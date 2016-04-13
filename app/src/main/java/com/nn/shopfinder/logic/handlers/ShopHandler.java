package com.nn.shopfinder.logic.handlers;

import com.nn.shopfinder.model.shop.GenericShop;

import retrofit2.Callback;

/**
 * Created by Nuno on 13/04/2016.
 */
public abstract class ShopHandler<V extends GenericShop,T> implements IShopHandler<V,T>, Callback<T>{

    private boolean requiresLocation, isLoaded;

    public ShopHandler(boolean requiresLocation) {
        this.requiresLocation = requiresLocation;
    }

    public void setIsLoaded(boolean isLoaded) {
        this.isLoaded = isLoaded;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public boolean isRequiresLocation() {
        return requiresLocation;
    }
}
