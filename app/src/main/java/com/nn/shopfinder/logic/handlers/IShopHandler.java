package com.nn.shopfinder.logic.handlers;

import com.nn.shopfinder.model.shop.GenericShop;

import java.util.List;

/**
 * Created by Nuno on 13/04/2016.
 */
public interface IShopHandler<V extends GenericShop, T>{


    void loadShops();

    List<V> parseShops(T serviceResponse);




}
