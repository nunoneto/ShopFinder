package com.nn.shopfinder.views.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nn.shopfinder.R;
import com.nn.shopfinder.logic.ShopFactory;
import com.nn.shopfinder.logic.handlers.OnShopsLoadedCallback;
import com.nn.shopfinder.model.DataModel;
import com.nn.shopfinder.model.shop.GenericShop;
import com.nn.shopfinder.views.dialog.GenericDialog;
import com.nn.shopfinder.views.fragment.ShopDetailFragment;
import com.nn.shopfinder.views.fragment.ShopListingFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java8.util.function.Consumer;
import java8.util.function.Predicate;
import java8.util.stream.StreamSupport;

public class MainActivity extends AppCompatActivity
        implements  NavigationView.OnNavigationItemSelectedListener,
                    OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
                    GoogleApiClient.OnConnectionFailedListener, OnShopsLoadedCallback {

    private final static String TAG = "MAIN_ACTIVITY";

    private Toolbar toolbar;
    private GoogleMap map;
    private GoogleApiClient mGoogleApiClient;
    private Location lastKnownLocation;
    private Map<Marker,String> mapMarkers;

    //Fragments
    private ShopDetailFragment shopDetail;

    //dialog
    GenericDialog diag;

    //Callbacks
    private final static int CALLBACK_REQUEST_LOCATION_PERMISSION = 1;
    private final static int CALLBACK_REQUEST_INTERNET_PERMISSION = 2;

    //Logic
    private ShopFactory shopFac;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initPlayServices();

        initToolbar();
        initNavDrawer();

        //init GoogleMaps
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);

        shopFac = new ShopFactory(this);

        validateLocationStatus();
        validateInternetAccess();
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


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        //TODO map options choosen

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                String shopId = mapMarkers.get(marker);
                Toast.makeText(getApplicationContext(), shopId, Toast.LENGTH_SHORT).show();
                //showShopDetail(shopId);
                showShopListing();
                return false;
            }
        });
    }

    private void showShopDetail(String shopId){

        getFragmentManager().findFragmentById(R.id.mapFragment)
                .getView()
                .setVisibility(View.GONE);

        getFragmentManager()
                .beginTransaction()
                .add(
                        R.id.fragment_container,
                        ShopDetailFragment.newInstance(shopId)
                ).commit();

    }

    private void showShopListing(){

        getFragmentManager().findFragmentById(R.id.mapFragment)
                .getView()
                .setVisibility(View.GONE);

        getFragmentManager()
                .beginTransaction()
                .add(
                        R.id.fragment_container,
                        ShopListingFragment.newInstance()
                ).commit();

    }

    private boolean validateLocationStatus(){

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},CALLBACK_REQUEST_LOCATION_PERMISSION);
            return false;
        }


        LocationManager locMan = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        boolean status = locMan.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(!status){
            GenericDialog
                    .newInstance(getResources().getString(R.string.activate_gps))
                    .show(getFragmentManager(), "DIAG");
            return false;
        }

        return true;
    }

    private boolean validateInternetAccess() {

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, CALLBACK_REQUEST_INTERNET_PERMISSION);
            return false;
        }
        return true;
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

    private GoogleApiClient initPlayServices(){
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        return mGoogleApiClient;
    }

    @Override
    public void onConnected(Bundle bundle) {

        if (validateLocationStatus()) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Location loc = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            DataModel.getInstance().setLastKnownLocation(loc);
            map.moveCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(
                    new LatLng(loc.getLatitude(), loc.getLongitude()),
                    10
            )));
            shopFac.loadShops();
        }
    }


    /**
     * Google Play Services Callbacks
     */

    @Override
    public void onConnectionSuspended(int i) {
        //TODO
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        //TODO
    }

    @Override
    protected void onStart() {
        super.onStart();
        initPlayServices().connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        initPlayServices().disconnect();
    }


    @Override
    public void storesLoaded(final List<? extends GenericShop> shops) {
        if(mapMarkers == null)
            mapMarkers = new HashMap<>();
        StreamSupport.stream(shops)
                .filter(new Predicate<GenericShop>() {
                    @Override
                    public boolean test(GenericShop genericShop) {
                        //check if id doesnt already exist, no duplicates here...
                        return !mapMarkers.containsValue(genericShop.getId());
                    }
                })
                .forEach(new Consumer<GenericShop>() {
                    @Override
                    public void accept(GenericShop genericShop) {
                        mapMarkers.put(
                                map.addMarker(new MarkerOptions()
                                                .position(new LatLng(genericShop.getLatitude(), genericShop.getLongitude()))
                                                .draggable(false)
//                                                .icon(BitmapDescriptorFactory.fromResource(genericShop.getIconResourceId()))
                                ),
                                genericShop.getId()
                        );
                    }
                });
    }
}
