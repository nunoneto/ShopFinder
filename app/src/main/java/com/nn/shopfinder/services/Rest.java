package com.nn.shopfinder.services;

import android.content.Context;
import android.util.Log;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nn.shopfinder.utils.Utils;

import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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

    public Rest(Context context) {
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
                //cache for offline usage
                .cache(new Cache(mContext.getCacheDir(), 10 * 1024 * 1024)) // 10 MB
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        if (Utils.getInstance().isNetworkAvailable()) {
                            request = request.newBuilder().header("Cache-Control", "public, max-age=" + 60*60).build(); // re-use data for an minute
                        } else {
                            request = request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build(); // re-use data for 7 days
                        }
                        return chain.proceed(request);
                    }
                })
                //interceptor for logging network requests in chrome dev tools
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

    public static Rest getInstance() {
        return instance;
    }

    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {

            Log.e("RETROFIT", "intercepted response");
            Request request = chain.request();
            Response originalResponse = chain.proceed(request);
            return originalResponse.newBuilder()
                    .header("Cache-Control", String.format("max-age=%d, public, only-if-cached, max-stale=%d", 120, 0))
                    .build();
        }
    };

}
