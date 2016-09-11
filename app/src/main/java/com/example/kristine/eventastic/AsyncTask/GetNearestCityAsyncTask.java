package com.example.kristine.eventastic.AsyncTask;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.example.kristine.eventastic.Activities.EventNearLocation;
import com.example.kristine.eventastic.JavaClasses.AllEventsPuffer;
import com.example.kristine.eventastic.JavaClasses.Event;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

//this will return all Events
public class GetNearestCityAsyncTask extends AsyncTask<ArrayList<Address>,Integer,String> {

    private Location userLocation;
    private Context context;

    public GetNearestCityAsyncTask(Location userLocation,Context context){
        this.userLocation=userLocation;
        this.context=context;
    }

    @Override
    protected String doInBackground(ArrayList<Address>... params) {

        return getCityName(getNearestCity(params[0])) ;
    }

    private String getCityName(ArrayList<Double> nearestCity) {
        Geocoder gc=new Geocoder(context,Locale.GERMAN);
        String city=null;
        try {
            List<Address>addresses=gc.getFromLocation(nearestCity.get(0),nearestCity.get(1),1);
            if(addresses.size()!=0){
                Address a=addresses.get(0);
                city=a.getLocality();
            }
        }catch (IOException e){
            Log.d("GEO",e.toString());
        }
        return city;
    }

    private ArrayList<Double> getNearestCity(ArrayList<Address> param) {
        String city;
        double smallestDistance=proofDistance(param.get(0).getLatitude(),param.get(0).getLongitude());
        double smallestCityLat=0;
        double smallestCityLon=0;
        for(int i=1; i<param.size();i++){
            double cityLat=param.get(i).getLatitude();
            double cityLon=param.get(i).getLongitude();
            double distance=proofDistance(cityLat,cityLon);
            if(distance<smallestDistance){
                smallestDistance=distance;
                smallestCityLat=cityLat;
                smallestCityLon=cityLon;
            }
        }
        ArrayList<Double>locations=new ArrayList<>();
        locations.add(smallestCityLat);
        locations.add(smallestCityLon);
        return locations;
    }



    private double proofDistance(double cityLat, double cityLon) {
        double userLat=userLocation.getLatitude();
        double userLon=userLocation.getLongitude();
        return Math.sqrt(Math.pow(userLat-cityLat,2)+Math.pow(userLon-cityLon,2));
    }

    private ArrayList<Event> getEvents(String city){
        ArrayList<Event>allEvents=AllEventsPuffer.getAllEvents();
        ArrayList<Event>eventsInNeighborhood=new ArrayList<>();
        for(int i=0; i<allEvents.size();i++){
            if(city.equals(allEvents.get(i).getCity())){
                eventsInNeighborhood.add(allEvents.get(i));
            }
        }
        return eventsInNeighborhood;
    }




}
