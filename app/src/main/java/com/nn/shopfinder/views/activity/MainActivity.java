package com.nn.shopfinder.views.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nn.shopfinder.R;
import com.nn.shopfinder.model.DataModel;
import com.nn.shopfinder.model.shop.GenericShop;
import com.nn.shopfinder.model.shop.VodafoneShop;
import com.nn.shopfinder.services.Rest;
import com.nn.shopfinder.services.response.VodafoneResponse;
import com.nn.shopfinder.views.dialog.GenericDialog;

import java.util.ArrayList;
import java.util.List;

import java8.util.function.Consumer;
import java8.util.stream.StreamSupport;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private final static String TAG = "MAIN_ACTIVITY";

    private Toolbar toolbar;
    private GoogleMap map;

    //dialog
    GenericDialog diag;

    //Callbacks
    private final static int CALLBACK_REQUEST_LOCATION_PERMISSION = 1;
    private final static int CALLBACK_REQUEST_INTERNET_PERMISSION = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        initNavDrawer();

        validateLocationStatus();
        validateInternetAccess();

        Rest.getInstance().getService().listRepos().enqueue(new Callback<VodafoneResponse>() {
            @Override
            public void onResponse(Call<VodafoneResponse> call, Response<VodafoneResponse> response) {

                VodafoneResponse resp = response.body();
                if(resp != null){

                    final List<VodafoneResponse.VodafoneShop> shops = new ArrayList<VodafoneResponse.VodafoneShop>(resp.getShops().values());
                    final List<VodafoneShop> outputShops = new ArrayList<VodafoneShop>();
                    StreamSupport.stream(shops)
                            .forEach(new Consumer<VodafoneResponse.VodafoneShop>() {
                                @Override
                                public void accept(VodafoneResponse.VodafoneShop vodafoneShop) {
                                    outputShops.add(
                                            new VodafoneShop(
                                                    vodafoneShop.getHash(),
                                                    vodafoneShop.getName(),
                                                    vodafoneShop.getDescription(),
                                                    vodafoneShop.getStoreProperties().getAddress(),
                                                    vodafoneShop.getStoreProperties().getHours(),
                                                    vodafoneShop.getStoreProperties().getLat(),
                                                    vodafoneShop.getStoreProperties().getLon()
                                            ));
                                }
                            });
                    DataModel.getInstance().setVodafoneShops(outputShops);
                    buildShopMarkers();
                }else{
                    //TODO: deal with it -.-'
                }

            }

            @Override
            public  void onFailure(Call<VodafoneResponse> call, Throwable t) {
                Log.d(TAG,t.getMessage());
            }
        });

    }

    private void buildShopMarkers() {

        StreamSupport.stream(DataModel.getInstance().getAllShops()).forEach(new Consumer<GenericShop>() {
            @Override
            public void accept(GenericShop genericShop) {
                map.addMarker(new MarkerOptions()
                        .position(new LatLng(genericShop.getLatitude(), genericShop.getLongitude()))
                        .draggable(false)
                );
            }
        });

    }


    private void initNavDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }

    private boolean validateLocationStatus(){

        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},CALLBACK_REQUEST_LOCATION_PERMISSION);
            return false;
        }


        LocationManager locMan = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        boolean status = locMan.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(!status){
            GenericDialog
                    .newInstance(getResources().getString(R.string.activate_gps))
                    .show(getFragmentManager(), "DIAG");
        }

        return false;
    }

    private boolean validateInternetAccess() {

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET},CALLBACK_REQUEST_INTERNET_PERMISSION);
            return false;
        }
        return true;
    }


    private void centerMap(){
        LocationManager locMan = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        //Location loc = locMan.getLastKnownLocation(locMan.getBestProvider(new Criteria(),false));

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch(requestCode){
            case CALLBACK_REQUEST_LOCATION_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    validateLocationStatus();
                else
                    //TODO: add dialog to ask user if sure about exiting app
                break;
            case CALLBACK_REQUEST_INTERNET_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    validateInternetAccess();
                else
                    //TODO: add dialog to ask user if sure about exiting app
                break;
            default:

                break;
        }

    }
}
