package com.example.kristine.eventastic.JavaClasses;

import java.util.ArrayList;

/**
 * Created by Kristine on 01.09.2016.
 */
public class AllEventsPuffer {

    private static ArrayList<Event> allEvents=new ArrayList<>();



    public static void enterEvent(Event event){
        allEvents.add(event);

    }

    public static void clearArrayList(){
        allEvents.clear();
    }

    public static ArrayList<Event> getAllEvents(){
        return allEvents;
    }


}
