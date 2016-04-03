package com.nn.shopfinder.services.response;

import android.content.Context;

import com.nn.shopfinder.services.Services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Nuno on 03/04/2016.
 */
public class Rest {

    protected static Rest instance;
    private Context mContext;

    private Retrofit retrofit;
    Services service;

    public Rest(Context context){
        this.mContext = context;
        instance = this;
        initRetrofit();
    }

    private void initRetrofit() {

        retrofit = new Retrofit.Builder()
                .baseUrl("https://selfcareonline.vodafone.pt/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(Services.class);
    }

    public Services getService() {
        return service;
    }

    public static Rest getInstance(){
        return instance;
    }
}
