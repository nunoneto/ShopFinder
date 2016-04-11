package com.nn.shopfinder.services.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nuno on 03/04/2016.
 */
public class VodafoneResponse implements Serializable{

    @SerializedName("result")
    public Map<String,VodafoneShop> shops;

    public Map<String,VodafoneShop> getShops() {
        return shops;
    }

    public class VodafoneShop implements Serializable{

        private int id;
        private long changedAt;
        private String hash;
        private String title;
        private String name;
        private String description;
        @SerializedName("store")
        private StoreProperties storeProperties;

        public int getId() {
            return id;
        }

        public long getChangedAt() {
            return changedAt;
        }

        public String getHash() {
            return hash;
        }

        public String getTitle() {
            return title;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public StoreProperties getStoreProperties() {
            return storeProperties;
        }


        public class StoreProperties implements Serializable{

            private String address, county, district, parish;
            private String description, hours, sap;
            private String alargados, assistencia, mobile, negocios, portal, reparacao, tekkies;
            private double lat, lon;

            public String getAddress() {
                return address;
            }

            public String getCounty() {
                return county;
            }

            public String getDistrict() {
                return district;
            }

            public String getParish() {
                return parish;
            }

            public String getDescription() {
                return description;
            }

            public String getHours() {
                return hours;
            }

            public String getSap() {
                return sap;
            }

            public String getAlargados() {
                return alargados;
            }

            public String getAssistencia() {
                return assistencia;
            }

            public String getMobile() {
                return mobile;
            }

            public String getNegocios() {
                return negocios;
            }

            public String getPortal() {
                return portal;
            }

            public String getReparacao() {
                return reparacao;
            }

            public String getTekkies() {
                return tekkies;
            }

            public double getLat() {
                return lat;
            }

            public double getLon() {
                return lon;
            }
        }
    }
}
