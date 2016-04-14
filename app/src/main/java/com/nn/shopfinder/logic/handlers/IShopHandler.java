package com.nn.shopfinder.logic.handlers;

import com.nn.shopfinder.model.shop.GenericShop;

import java.util.List;

/**
 * Created by Nuno on 13/04/2016.
 */
public interface IShopHandler<V extends GenericShop, T>{


    /**
     * called by the factory to load the shops from service or cache
     */
    void loadShops();

    /**
     * parse the service response into domain objects
     * @param serviceResponse response from proprietary services
     * @return list of internal domain objects
     */
    List<V> parseShops(T serviceResponse);




}
