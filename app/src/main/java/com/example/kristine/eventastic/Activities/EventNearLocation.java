package com.example.kristine.eventastic.Activities;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    private TextView userLocation;
    private Button toAllCities;
    private ListView nearestEvents;
    private CityAdapter adapter;
    private ArrayList<Address> allLocations=new ArrayList<>();
    private ArrayList<Event>events=new ArrayList<>();
    private LocationController locationController;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_near_location);
        initUI();
        initLocationOfAllCities();
        initLocationController();
    }
    //getting user´s location while activity is visible
    private void initLocationController() {
        locationController=new LocationController(this);
        locationController.setLocationUpdateListener(this);
        locationController.start();
    }

    //locationController stops when activity not visible
    @Override
    protected void onPause() {
        super.onPause();
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




    private void initUI() {
        initTextView();
        initButton();
        initListView();
    }

    private void initAdapter() {
        adapter=new CityAdapter(this,events);
    }

    private void initListView() {
        nearestEvents=(ListView) findViewById(R.id.city_location);
        nearestEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(EventNearLocation.this, AllInformationsOfAnEvent.class);
                intent.putExtra(getResources().getString(R.string.event_in_intent),events.get(position));
                startActivity(intent);
            }
        });

    }

    //button gets the user to all cities that are in the database
    private void initButton() {
        toAllCities=(Button)findViewById(R.id.from_events_in_neighbourhood_to_all_possible_cities);
        toAllCities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EventNearLocation.this,AllPossibleCities.class);
                startActivity(intent);
            }
        });
    }

    private void initTextView() {
        userLocation=(TextView)findViewById(R.id.user_location);

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

            //Choosing this icon the user gets to the activity to add an event.
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


    //setting user´s location and presenting the nearest events in a listView
    @Override
    public void onNewLocation(Location location) {
        Geocoder gc=new Geocoder(this, Locale.GERMAN);
        try {
            List<Address> addresses=gc.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            if(addresses.size()!=0){
                Address a=addresses.get(0);
                userLocation.setText(getResources().getString(R.string.your_location)+" "+a.getLocality());
            }
        }catch (IOException e){
            Log.d("GEO",e.toString());
        }

        GetNearestCityAsyncTask nearestCity=new GetNearestCityAsyncTask(location,this);
        nearestCity.execute(allLocations);

        try {
            events=nearestCity.get();
            initAdapter();
            nearestEvents.setAdapter(adapter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
