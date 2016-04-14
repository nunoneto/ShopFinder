package com.nn.shopfinder.logic;

import com.nn.shopfinder.model.shop.GenericShop;

import java.util.List;

/**
 * Created by Nuno on 13/04/2016.
 */
public interface OnShopsLoadedCallback {

    void storesLoaded(List<? extends GenericShop> shops);
}
