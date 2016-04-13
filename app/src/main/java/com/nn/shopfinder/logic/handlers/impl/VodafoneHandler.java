package com.nn.shopfinder.logic.handlers.impl;

import android.util.Log;

import com.nn.shopfinder.logic.handlers.ShopHandler;
import com.nn.shopfinder.model.DataModel;
import com.nn.shopfinder.model.shop.VodafoneShop;
import com.nn.shopfinder.services.Rest;
import com.nn.shopfinder.services.response.VodafoneResponse;

import java.util.ArrayList;
import java.util.List;

import java8.util.function.Consumer;
import java8.util.function.Predicate;
import java8.util.stream.StreamSupport;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nuno on 13/04/2016.
 */
public class VodafoneHandler extends ShopHandler<VodafoneShop, VodafoneResponse> {

    private static final String TAG = "SHOPHANDLER_VODAFONE";

    public VodafoneHandler(boolean requiresLocation) {
        super(requiresLocation);
    }

    @Override
    public void loadShops() {
        Rest.getInstance().getService().getVodafoneShops().enqueue(this);
    }

    @Override
    public List<VodafoneShop> parseShops(VodafoneResponse serviceResponse) {

        final List<VodafoneResponse.VodafoneShop> shops = new ArrayList<VodafoneResponse.VodafoneShop>(serviceResponse.getShops().values());
        final List<VodafoneShop> outputShops = new ArrayList<VodafoneShop>();
        StreamSupport.stream(shops)
                .filter(new Predicate<VodafoneResponse.VodafoneShop>() {
                    @Override
                    public boolean test(VodafoneResponse.VodafoneShop vodafoneShop) {
                        return vodafoneShop != null && vodafoneShop.getStoreProperties() != null;
                    }
                })
                .forEach(new Consumer<VodafoneResponse.VodafoneShop>() {
                    @Override
                    public void accept(VodafoneResponse.VodafoneShop vodafoneShop) {
                        outputShops.add(
                                new VodafoneShop(
                                        "vodafone_"+vodafoneShop.getHash(),
                                        vodafoneShop.getName(),
                                        vodafoneShop.getDescription(),
                                        vodafoneShop.getStoreProperties().getAddress(),
                                        vodafoneShop.getStoreProperties().getHours(),
                                        vodafoneShop.getStoreProperties().getLat(),
                                        vodafoneShop.getStoreProperties().getLon()
                                ));
                    }
                });
        DataModel.getInstance().setVodafoneShops(outputShops);
        return outputShops;
    }


    @Override
    public void onResponse(Call<VodafoneResponse> call, Response<VodafoneResponse> response) {
        VodafoneResponse resp = response.body();
        if (resp != null) {
            this.parseShops(resp);
            this.setIsLoaded(true);
        }else{
            Log.d(TAG,"Could not load shops");
            this.setIsLoaded(false);
        }
    }

    @Override
    public void onFailure(Call<VodafoneResponse> call, Throwable t) {
        Log.d(TAG,"Could not load shops");
    }


}