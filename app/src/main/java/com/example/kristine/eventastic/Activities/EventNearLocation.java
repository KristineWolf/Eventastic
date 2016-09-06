package com.example.kristine.eventastic.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.audiofx.BassBoost;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kristine.eventastic.R;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class EventNearLocation extends AppCompatActivity {

    private TextView currentLocation, positionCity;
    private LocationManager locationManager;

    private Button toAllCities;
    private LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_near_location);
        initUI();
        initLocationManager();
        initLocationListener();
    }

    private void initLocationListener() {
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                currentLocation.setText(" lat: " + location.getLatitude() + " lon: " + location.getLongitude());
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
                }, 10);//10 ist requestCode und kann irgendeine Zahl gewählt werden
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
            case 10:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){

                    return;
                }
                break;

        }
    }

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
        positionCity=(TextView)findViewById(R.id.city_location);

        Geocoder gc=new Geocoder(this, Locale.GERMAN);
        String locationName="Regensburg";
        List<Address>locations=null;

        try {
            locations =gc.getFromLocationName(locationName,5);
            Address address=locations.get(0);
            positionCity.setText(""+address.getLatitude()+"+lon:"+address.getLongitude());
        }catch (IOException e){
            Log.d("TAG","Unable to get address from ölocation name"+e.toString());
        }
    }
}
