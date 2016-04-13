package com.nn.shopfinder.services.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by NB20301 on 13/04/2016.
 */
public class NosResponse implements Serializable {

    @SerializedName("d")
    private List<Shop> shops;

    public List<Shop> getShops() {
        return shops;
    }

    public class Shop implements Serializable{

        private int id;
        private String Title, Schedule, District, Locality, TownHall, PostalCodeMajor, PostalCodeMinor;
        private double StoreLatitude, StoreLongitude;

        private List<String> AvailableServices;

        public int getId() {
            return id;
        }

        public String getTitle() {
            return Title;
        }

        public String getSchedule() {
            return Schedule;
        }

        public String getDistrict() {
            return District;
        }

        public String getLocality() {
            return Locality;
        }

        public String getTownHall() {
            return TownHall;
        }

        public String getPostalCodeMajor() {
            return PostalCodeMajor;
        }

        public String getPostalCodeMinor() {
            return PostalCodeMinor;
        }

        public double getStoreLatitude() {
            return StoreLatitude;
        }

        public double getStoreLongitude() {
            return StoreLongitude;
        }

        public List<String> getAvailableServices() {
            return AvailableServices;
        }
    }

}
