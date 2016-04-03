package com.nn.shopfinder.services;

import com.nn.shopfinder.services.response.VodafoneResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Nuno on 03/04/2016.
 */
public interface Services {

    //Vodafone

    @GET("https://selfcareonline.vodafone.pt/lojasvdf/api")
    Call<VodafoneResponse> listRepos(Callback<VodafoneResponse> callback);

}
