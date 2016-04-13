package com.nn.shopfinder.services;

import com.nn.shopfinder.services.request.NosBody;
import com.nn.shopfinder.services.response.MeoResponse;
import com.nn.shopfinder.services.response.NosResponse;
import com.nn.shopfinder.services.response.VodafoneResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Nuno on 03/04/2016.
 */
public interface Services {

    //Vodafone
    @GET("https://selfcareonline.vodafone.pt/lojasvdf/api")
    Call<VodafoneResponse> getVodafoneShops();

    //MEO
    @GET("http://lojas.meo.pt/GIS/GetPOIS?z=49695&categoryId=421")
    Call<MeoResponse> getMeoShops(
            @Query("latitude1") Double latitude1,
            @Query("longitude1") Double longitude1,
            @Query("latitude2") Double latitude2,
            @Query("longitude2") Double longitude2,
            @Query("pageNumber") Integer pageNumber,
            @Query("recordsPerPage") Integer recordsPerPage
    );

    //NOS
    @POST("http://www.nos.pt/particulares/_layouts/15/Armstrong/ApplicationPages/OurStores.aspx/GetStores")
    Call<NosResponse> getNOSShops(@Body NosBody body);

}
