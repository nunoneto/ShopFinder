package com.nn.shopfinder.logic.handlers.impl;

import com.nn.shopfinder.logic.handlers.OnShopsLoadedCallback;
import com.nn.shopfinder.logic.handlers.AbstractShopHandler;
import com.nn.shopfinder.model.DataModel;
import com.nn.shopfinder.model.shop.NosShop;
import com.nn.shopfinder.services.Rest;
import com.nn.shopfinder.services.request.NosBody;
import com.nn.shopfinder.services.response.NosResponse;

import java.util.ArrayList;
import java.util.List;

import java8.util.function.Consumer;
import java8.util.function.Predicate;
import java8.util.stream.StreamSupport;

/**
 * Created by Nuno on 14/04/2016.
 */
public class NosHandler extends AbstractShopHandler<NosShop,NosResponse> {

    public NosHandler(boolean requiresLocation, OnShopsLoadedCallback onShopsLoadedCallback) {
        super(requiresLocation, onShopsLoadedCallback);
    }

    @Override
    public void loadShops() {
        Rest.getInstance().getService().getNOSShops(new NosBody()).enqueue(this);
    }

    @Override
    public List<NosShop> parseShops(NosResponse serviceResponse) {

        final List<NosShop> outputShops = new ArrayList<NosShop>();
        StreamSupport.stream(serviceResponse.getShops())
                .filter(new Predicate<NosResponse.Shop>() {
                    @Override
                    public boolean test(NosResponse.Shop nosShop) {
                        return nosShop != null;
                    }
                })
                .forEach(new Consumer<NosResponse.Shop>() {
                    @Override
                    public void accept(NosResponse.Shop nosShop) {
                        outputShops.add(
                                new NosShop(
                                        "nos_"+nosShop.getId(),
                                        nosShop.getTitle(),
                                        "",
                                        nosShop.getPostalCodeMajor()+"-"+nosShop.getPostalCodeMinor()+", "+nosShop.getDistrict()+", "+nosShop.getLocality()+", "+nosShop.getTownHall(),
                                        nosShop.getSchedule(),
                                        nosShop.getStoreLatitude(),
                                        nosShop.getStoreLongitude(),
                                        nosShop.getAvailableServices()
                                ));
                    }
                });
        DataModel.getInstance().setNosShops(outputShops);
        getOnShopsLoadedCallback().storesLoaded(outputShops);
        return outputShops;
    }
}
