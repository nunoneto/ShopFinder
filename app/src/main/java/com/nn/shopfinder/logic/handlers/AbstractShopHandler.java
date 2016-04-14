package com.nn.shopfinder.logic.handlers;

import android.util.Log;

import com.nn.shopfinder.model.shop.GenericShop;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nuno on 13/04/2016.
 */
public abstract class AbstractShopHandler<V extends GenericShop,T> implements IShopHandler<V,T>, Callback<T>{

    public static final String TAG = "SHOPHANDLER";

    private boolean requiresLocation, isLoaded;
    private OnShopsLoadedCallback onShopsLoadedCallback;

    public AbstractShopHandler(boolean requiresLocation, OnShopsLoadedCallback onShopsLoadedCallback) {
        this.requiresLocation = requiresLocation;
        this.onShopsLoadedCallback = onShopsLoadedCallback;
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

    public OnShopsLoadedCallback getOnShopsLoadedCallback() {
        return onShopsLoadedCallback;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        T resp = response.body();
        if (resp != null) {
            this.parseShops(resp);
            this.setIsLoaded(true);
        }else{
            Log.d(TAG, "Could not load shops");
            this.setIsLoaded(false);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Log.d(TAG,"Could not load shops");
        this.setIsLoaded(false);
    }
}
