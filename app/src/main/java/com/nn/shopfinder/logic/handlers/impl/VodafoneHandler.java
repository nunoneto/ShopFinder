package com.nn.shopfinder.logic.handlers.impl;

import com.nn.shopfinder.logic.handlers.OnShopsLoadedCallback;
import com.nn.shopfinder.logic.handlers.AbstractShopHandler;
import com.nn.shopfinder.model.DataModel;
import com.nn.shopfinder.model.shop.VodafoneShop;
import com.nn.shopfinder.services.Rest;
import com.nn.shopfinder.services.response.VodafoneResponse;

import java.util.ArrayList;
import java.util.List;

import java8.util.function.Consumer;
import java8.util.function.Predicate;
import java8.util.stream.StreamSupport;

/**
 * Created by Nuno on 13/04/2016.
 */
public class VodafoneHandler extends AbstractShopHandler<VodafoneShop, VodafoneResponse> {

    public VodafoneHandler(boolean requiresLocation, OnShopsLoadedCallback onShopsLoadedCallback) {
        super(requiresLocation, onShopsLoadedCallback);
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
        getOnShopsLoadedCallback().storesLoaded(outputShops);
        return outputShops;
    }
}