package com.nn.shopfinder.services;

import android.content.Context;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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

        //parse json to POJOs
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://selfcareonline.vodafone.pt/")
                .addConverterFactory(GsonConverterFactory.create(gson))
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
