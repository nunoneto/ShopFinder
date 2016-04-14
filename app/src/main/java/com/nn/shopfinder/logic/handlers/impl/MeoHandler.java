package com.nn.shopfinder.logic.handlers.impl;

import com.nn.shopfinder.logic.handlers.OnShopsLoadedCallback;
import com.nn.shopfinder.logic.handlers.AbstractShopHandler;
import com.nn.shopfinder.model.DataModel;
import com.nn.shopfinder.model.shop.MeoShop;
import com.nn.shopfinder.services.Rest;
import com.nn.shopfinder.services.response.MeoResponse;

import java.util.ArrayList;
import java.util.List;

import java8.util.function.Consumer;
import java8.util.function.Function;
import java8.util.function.Predicate;
import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;

/**
 * Created by NB20301 on 14/04/2016.
 */
public class MeoHandler extends AbstractShopHandler<MeoShop, MeoResponse> {

    public MeoHandler(boolean requiresLocation, OnShopsLoadedCallback onShopsLoadedCallback) {
        super(requiresLocation, onShopsLoadedCallback);
    }

    @Override
    public void loadShops() {
        Rest.getInstance().getService().getMeoShops(
                35.068102021308d,
                -13.373642610162d,
                43.947245101186d,
                -3.1563574543194,
                1,1000, 421
        ).enqueue(this);
    }

    @Override
    public List<MeoShop> parseShops(final MeoResponse serviceResponse) {

        if(serviceResponse.getResponse() != null && serviceResponse.getResponse().getResponse() != null
                && serviceResponse.getResponse().getResponse() != null && serviceResponse.getResponse().getResponse().getResponse() != null ){
            final List<MeoResponse.GetPOIByBoundingBoxResponse.GetPOIByBoundingBoxResult.Shop> shops = serviceResponse.getResponse().getResponse().getResponse();
            final List<MeoShop> outputShops = new ArrayList<MeoShop>();
            StreamSupport.stream(shops)
                    .filter(new Predicate<MeoResponse.GetPOIByBoundingBoxResponse.GetPOIByBoundingBoxResult.Shop>() {
                        @Override
                        public boolean test(MeoResponse.GetPOIByBoundingBoxResponse.GetPOIByBoundingBoxResult.Shop meoShop) {
                            return meoShop != null;
                        }
                    })
                    .forEach(new Consumer<MeoResponse.GetPOIByBoundingBoxResponse.GetPOIByBoundingBoxResult.Shop>() {
                        @Override
                        public void accept(MeoResponse.GetPOIByBoundingBoxResponse.GetPOIByBoundingBoxResult.Shop meoShop) {
                            List services = StreamSupport.stream(meoShop.getServices())
                                    .map(new Function<MeoResponse.GetPOIByBoundingBoxResponse.GetPOIByBoundingBoxResult.Shop.Service, String>() {
                                        @Override
                                        public String apply(MeoResponse.GetPOIByBoundingBoxResponse.GetPOIByBoundingBoxResult.Shop.Service service) {
                                            return service.getService();
                                        }
                                    }).collect(Collectors.toList());
                            if (services != null) {
                                outputShops.add(
                                        new MeoShop(
                                                "meo_" + meoShop.getFriendlyName(),
                                                meoShop.getName(),
                                                "",
                                                meoShop.getAddress(),
                                                "",
                                                meoShop.getLatitude(),
                                                meoShop.getLongitude(),
                                                services
                                        ));
                            } else {
                                outputShops.add(
                                        new MeoShop(
                                                "meo_" + meoShop.getFriendlyName(),
                                                meoShop.getName(),
                                                "",
                                                meoShop.getAddress(),
                                                "",
                                                meoShop.getLatitude(),
                                                meoShop.getLongitude()
                                        ));
                            }


                        }
                    });
            DataModel.getInstance().setMeoShops(outputShops);
            getOnShopsLoadedCallback().storesLoaded(outputShops);
            return outputShops;

        }else{
            return null;
        }

    }
}
