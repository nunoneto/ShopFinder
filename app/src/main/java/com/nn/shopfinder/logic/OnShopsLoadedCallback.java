package com.nn.shopfinder.logic;

import java.util.List;

/**
 * Created by Nuno on 13/04/2016.
 */
public interface OnShopsLoadedCallback<T> {

    void storesLoaded(List<T> shops);
}
