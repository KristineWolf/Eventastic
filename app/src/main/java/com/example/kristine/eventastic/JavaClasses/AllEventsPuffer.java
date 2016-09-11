package com.example.kristine.eventastic.JavaClasses;

import java.util.ArrayList;

//this static class saves all events from the firebase database
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
