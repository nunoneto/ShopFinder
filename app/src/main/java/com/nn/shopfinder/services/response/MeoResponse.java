package com.nn.shopfinder.services.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Nuno on 13/04/2016.
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
            private List<Shop> response;

            public List<Shop> getResponse() {
                return response;
            }

            public class Shop implements Serializable{

                private String Address, FriendlyName, Name;
                private double Latitude, Longitude;

                @SerializedName("Services")
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

                    public String getService() {
                        return Service;
                    }
                }
            }
        }
    }
}
