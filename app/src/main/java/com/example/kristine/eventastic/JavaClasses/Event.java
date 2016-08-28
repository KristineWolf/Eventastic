package com.example.kristine.eventastic.JavaClasses;

/**
 * Created by Kristine on 10.08.2016.
 */
public class Event {

    private String city;
    private int day;
    private int month;
    private int year;
    private int hour;
    private int minutes;
    private String titel;
    private String definition;
    private String type;

    public Event(String city, int day, int month, int year, int hour, int minutes, String titel, String definition, String type){
        this.city=city;
        this.day=day;
        this.month=month;
        this.year=year;
        this.hour=hour;
        this.minutes=minutes;
        this.titel=titel;
        this.definition=definition;
        this.type=type;
    }

    public String getCity(){return city;}

    public int getDay(){return day;}

    public int getMonth(){return month;}

    public int getYear(){return year;}

    public int getHour(){return hour;}

    public int getMinutes(){return minutes;}

    public String getTitel(){return titel;}

    public String getDefinition(){return definition;}

    public String getType(){return type;}
}
