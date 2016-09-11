package com.example.kristine.eventastic.AsyncTask;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;

import com.example.kristine.eventastic.JavaClasses.AllEventsPuffer;
import com.example.kristine.eventastic.JavaClasses.Event;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

//this will return all addresses of possible cities
public class GetAddressesOfCities extends AsyncTask<Context,Integer,ArrayList<Address>> {
    private Context context;
    private ArrayList<String>cities=new ArrayList<>();
    private ArrayList<Address> allLocations=new ArrayList<>();

    public GetAddressesOfCities(Context context){
        this.context=context;
    }



    private void allAdressesOfPossibleCities() {
        ArrayList<Event>allEvents=AllEventsPuffer.getAllEvents();
        searchingThroughData(allEvents);
        getCityLocation();
    }

    private void getCityLocation() {
        for(int i=0; i<cities.size(); i++) {
            Geocoder gc=new Geocoder(context, Locale.GERMAN);
            String locationName=cities.get(i);
            List<Address> locations=null;

            try {
                locations =gc.getFromLocationName(locationName,1);
                Address address=locations.get(0);
                allLocations.add(address);
            }catch (IOException e){
                Log.d("TAG","Unable to get address from location name"+e.toString());
            }
        }
    }

    private void searchingThroughData(ArrayList<Event> allEvents) {
        for(int i=0; i<allEvents.size();i++){
            if (!cities.contains(allEvents.get(i).getCity())){
                cities.add(allEvents.get(i).getCity());
            }
        }
        Collections.sort(cities);
    }

    @Override
    protected ArrayList<Address> doInBackground(Context...params) {
        allAdressesOfPossibleCities();
        return allLocations;
    }

    @Override
    protected void onPostExecute(ArrayList<Address> addresses) {
        super.onPostExecute(addresses);

    }
}
