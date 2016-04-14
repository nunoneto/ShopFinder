package com.nn.shopfinder.global;

import com.facebook.stetho.Stetho;
import com.nn.shopfinder.model.DataModel;
import com.nn.shopfinder.services.Rest;
import com.nn.shopfinder.utils.Utils;

/**
 * Created by Nuno on 03/04/2016.
 */
public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //init dependencies
        new Utils(getApplicationContext());
        new Rest(getApplicationContext());
        new DataModel();

        //dev dependencies
        Stetho.initializeWithDefaults(this);

    }
}
