package com.example.kristine.eventastic.JavaClasses;

/**
 * Created by Kristine on 10.08.2016.
 */
public class Event implements Comparable<Event> {

    private String city;
    private int date;
    private String time;
    private String title;
    private String defintion;
    private String type;

    public Event(){}

    public Event(String city, int date, String time, String title, String defintion, String type){
        this.city=city;
        this.date=date;
        this.time=time;
        this.title=title;
        this.defintion=defintion;
        this.type=type;
    }

    public String getCity(){return city;}

    public void setCity(String city){this.city=city;}

    public int getDate(){return date;}

    public void setDate(int date){this.date=date;}

    public String getTime(){return time;}

    public void setTime(String time){this.time=time;}

    public String getTitle(){return title;}

    public void setTitle(String title){this.title=title;}

    public String getDefintion(){return defintion;}

    public void setDefintion(String defintion){this.defintion=defintion;}

    public String getType(){return type;}

    public void setType(String type){this.type=type;}

    @Override
    public int compareTo(Event another) {
        int comparisonResult=(""+date).compareTo(""+another.getDate());
        if(comparisonResult==0){
            comparisonResult=time.compareTo(another.getTime());
        }
        return comparisonResult;
    }
}
