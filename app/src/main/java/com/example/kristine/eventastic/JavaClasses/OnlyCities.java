package com.example.kristine.eventastic.JavaClasses;

import java.util.ArrayList;

/**
 * Created by Kristine on 01.09.2016.
 */
public class OnlyCities {
    private static ArrayList<String> allCities=new ArrayList<>();



    public static void enterEvent(String city){
        if(!allCities.equals(city)) {
            allCities.add(city);
        }

    }

    public static void clearArray(){
        allCities.clear();
    }


    public static ArrayList<String> getAllCities(){
        return allCities;
    }
}
