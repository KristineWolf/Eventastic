package com.example.kristine.eventastic.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kristine.eventastic.Adapter.CityAdapter;
import com.example.kristine.eventastic.AsyncTask.GetAddressesOfCities;
import com.example.kristine.eventastic.AsyncTask.GetNearestCityAsyncTask;
import com.example.kristine.eventastic.Interface.LocationUpdateListener;
import com.example.kristine.eventastic.JavaClasses.Event;
import com.example.kristine.eventastic.JavaClasses.LocationController;
import com.example.kristine.eventastic.R;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

//this activity presents specific events that are the closest to the user´s location.
public class EventNearLocation extends AppCompatActivity implements LocationUpdateListener {

    private TextView currentLocation,city;

    private LocationManager locationManager;
    private Button toAllCities;
    private LocationListener locationListener;
    private ArrayList<Address> allLocations=new ArrayList<>();
    private LocationController locationController;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_near_location);
        initUI();
        initLocationOfAllCities();
        initLocationController();
    }

    private void initLocationController() {
        locationController=new LocationController(this);
        locationController.setLocationUpdateListener(this);
        locationController.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationController.stop();
    }

    private void initLocationOfAllCities() {
        GetAddressesOfCities cities=new GetAddressesOfCities(this);
        cities.execute(this);

        try {
            allLocations= cities.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

/**
    private void initLocationListener() {
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                setUserLocation(location);


            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);

            }
        };


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET
                }, 5);
                return;
            }
        } else {
            locationManager.requestLocationUpdates("gps",100,0,locationListener);
        }

    }








    private void initLocationManager() {
        String locService = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) getSystemService(locService);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 5:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){

                    return;
                }
                break;

        }
    }
    */

    private void initUI() {
        currentLocation=(TextView)findViewById(R.id.user_location);
        toAllCities=(Button)findViewById(R.id.from_events_in_neighbourhood_to_all_possible_cities);
        toAllCities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EventNearLocation.this,AllPossibleCities.class);
                startActivity(intent);
            }
        });
        city=(TextView)findViewById(R.id.city_location);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.event_in_city_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        int id= item.getItemId();
        switch (id){


            case R.id.event_in_city_add_event:
                Intent intent=new Intent(EventNearLocation.this,AddEvent.class);
                startActivity(intent);
                break;


            //Choosing this icon the user gets to the activity which contains all participating events.
            case R.id.event_in_city_to_my_events:
                Intent intent3 = new Intent(EventNearLocation.this, ParticipatingEvents.class);
                startActivity(intent3);
                break;

            case android.R.id.home:
                finish();
                break;

        }

        return true;
    }



    @Override
    public void onNewLocation(Location location) {
        Geocoder gc=new Geocoder(this, Locale.GERMAN);
        try {
            List<Address> addresses=gc.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            if(addresses.size()!=0){
                Address a=addresses.get(0);
                currentLocation.setText(a.getLocality());
            }
        }catch (IOException e){
            Log.d("GEO",e.toString());
        }
        GetNearestCityAsyncTask nearestCity=new GetNearestCityAsyncTask(location,this);
        nearestCity.execute(allLocations);
        try {
            city.setText(nearestCity.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
