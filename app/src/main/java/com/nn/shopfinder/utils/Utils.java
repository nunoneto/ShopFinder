package com.nn.shopfinder.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Nuno on 14/04/2016.
 */
public class Utils {

    private static Utils instance;
    private static Context context;

    public Utils(Context context) {
        this.context = context;
        instance = this;
    }

    public static Utils getInstance() {
        return instance;
    }

    public boolean isNetworkAvailable(){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
