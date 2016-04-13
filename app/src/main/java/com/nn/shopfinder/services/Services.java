package com.nn.shopfinder.services;

import com.nn.shopfinder.services.response.MeoResponse;
import com.nn.shopfinder.services.response.VodafoneResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Nuno on 03/04/2016.
 */
public interface Services {

    //Vodafone
    @GET("https://selfcareonline.vodafone.pt/lojasvdf/api")
    Call<VodafoneResponse> getVodafoneShops();

    //Meo
    @GET("http://lojas.meo.pt/GIS/GetPOIS?z=49695&categoryId=421")
    Call<MeoResponse> getMeoShops(
            @Query("latitude") Double latitude1,
            @Query("longitude1") Double longitude1,
            @Query("latitude") Double latitude2,
            @Query("longitude1") Double longitude2,
            @Query("pageNumber") Integer pageNumber,
            @Query("recordsPerPage") Integer recordsPerPage
    );

}
