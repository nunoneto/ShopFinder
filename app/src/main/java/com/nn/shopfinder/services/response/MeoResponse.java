package com.nn.shopfinder.services.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by NB20301 on 13/04/2016.
 */
public class MeoResponse implements Serializable {

    @SerializedName("GetPOIByBoundingBoxResponse")
    private GetPOIByBoundingBoxResponse response;

    public GetPOIByBoundingBoxResponse getResponse() {
        return response;
    }

    public class GetPOIByBoundingBoxResponse implements Serializable{

        @SerializedName("GetPOIByBoundingBoxResult")
        private GetPOIByBoundingBoxResult response;

        public GetPOIByBoundingBoxResult getResponse() {
            return response;
        }

        public class GetPOIByBoundingBoxResult implements Serializable{

            @SerializedName("POI")
            private POI response;

            public POI getResponse() {
                return response;
            }

            public class POI implements Serializable{

                private List<Shop> shops;

                public List<Shop> getShops() {
                    return shops;
                }

                public class Shop implements Serializable{

                    private String Address, FriendlyName, Name;
                    private double Latitude, Longitude;

                    private List<Service> services;

                    public String getAddress() {
                        return Address;
                    }

                    public String getFriendlyName() {
                        return FriendlyName;
                    }

                    public String getName() {
                        return Name;
                    }

                    public double getLatitude() {
                        return Latitude;
                    }

                    public double getLongitude() {
                        return Longitude;
                    }

                    public List<Service> getServices() {
                        return services;
                    }

                    public class Service implements Serializable{

                        private String Service;

                    }
                }

            }
        }
    }
}
