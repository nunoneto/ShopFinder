package com.nn.shopfinder.global;

import com.nn.shopfinder.services.response.Rest;

/**
 * Created by Nuno on 03/04/2016.
 */
public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //init dependencies
        new Rest(getApplicationContext());

    }
}
