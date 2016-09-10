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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kristine.eventastic.Adapter.CityAdapter;
import com.example.kristine.eventastic.AsyncTask.GetAddressesOfCities;
import com.example.kristine.eventastic.AsyncTask.GetNearestCityAsyncTask;
import com.example.kristine.eventastic.JavaClasses.AllEventsPuffer;
import com.example.kristine.eventastic.JavaClasses.ChangeDateFormat;
import com.example.kristine.eventastic.JavaClasses.Event;
import com.example.kristine.eventastic.R;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class EventNearLocation extends AppCompatActivity {

    private TextView currentLocation;
    private ListView positionCity;
    private LocationManager locationManager;
    private Location userLocation;
    private Button toAllCities;
    private LocationListener locationListener;
    private ArrayList<String> cityNames=new ArrayList<>();
    private ArrayList<Address> allLocations=new ArrayList<>();
    private int counter=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_near_location);
        initUI();
        initLocationManager();
        initLocationOfAllCities();
        initLocationListener();




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


    private void initLocationListener() {
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                setToast(location);
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
                }, 5);//10 ist requestCode und kann irgendeine Zahl gewählt werden
                return;
            }
        } else {
            locationManager.requestLocationUpdates("gps",100,0,locationListener);
        }

    }




    private void setToast(Location location) {
        Geocoder gc=new Geocoder(this,Locale.GERMAN);
        try {
            List<Address>addresses=gc.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            if(addresses.size()!=0){
                Address a=addresses.get(0);

                currentLocation.setText(a.getLocality());
            }
        }catch (IOException e){
            Log.d("GEO",e.toString());
        }

        if(counter<1) {
            GetNearestCityAsyncTask nearestCity = new GetNearestCityAsyncTask(location, EventNearLocation.this);
            nearestCity.execute(allLocations);
            try {

                final ArrayList<Event> events=nearestCity.get();
                CityAdapter cityAdapter=new CityAdapter(this,events);
                positionCity.setAdapter(cityAdapter);
                positionCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(EventNearLocation.this, AllInformationsOfAnEvent.class);
                        //intent.putExtra("event",arraylist.get(position));
                        intent.putExtra("city",events.get(position).getCity());
                        intent.putExtra("titel",events.get(position).getTitel());
                        intent.putExtra("time",events.get(position).getTime());
                        intent.putExtra("type",events.get(position).getType());
                        intent.putExtra("date", ChangeDateFormat.changeIntoString(events.get(position).getDate()));
                        intent.putExtra("definition",events.get(position).getDefintion());

                        startActivity(intent);
                    }
                });



            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        counter++;

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
        positionCity=(ListView)findViewById(R.id.city_location);


    }


}
