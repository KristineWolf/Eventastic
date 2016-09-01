package com.example.kristine.eventastic.JavaClasses;

import java.util.ArrayList;

/**
 * Created by Kristine on 01.09.2016.
 */
public class AllEventsInACity {

    private static ArrayList<Event> specificCities=new ArrayList<>();

    public static void enterEvent(Event event){
        specificCities.add(event);
    }

    public static void clearEvent(){
        specificCities.clear();
    }
    public static ArrayList<Event> getSpecificCities(){
        return specificCities;
    }
}
