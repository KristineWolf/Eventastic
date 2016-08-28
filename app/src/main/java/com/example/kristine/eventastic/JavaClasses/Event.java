package com.example.kristine.eventastic.JavaClasses;

/**
 * Created by Kristine on 10.08.2016.
 */
public class Event {

    private String city;
    private int date;
    private String time;
    private String titel;
    private String defintion;
    private String type;

    public Event(){}

    public Event(String city, int date, String time, String titel, String defintion, String type){
        this.city=city;
        this.date=date;
        this.time=time;
        this.titel=titel;
        this.defintion=defintion;
        this.type=type;
    }

    public String getCity(){return city;}

    public void setCity(String city){this.city=city;}

    public int getDate(){return date;}

    public void setDate(int date){this.date=date;}

    public String getTime(){return time;}

    public void setTime(String time){this.time=time;}

    public String getTitel(){return titel;}

    public void setTitel(String titel){this.titel=titel;}

    public String getDefintion(){return defintion;}

    public void setDefintion(String defintion){this.defintion=defintion;}

    public String getType(){return type;}

    public void setType(String type){this.type=type;}

    public String changeIntIntoStringDate(){
        String iDate=""+date;
        String day= ""+iDate.charAt(6)+iDate.charAt(7);
        String month=""+iDate.charAt(4)+iDate.charAt(5);
        String year =""+iDate.charAt(0)+iDate.charAt(1)+iDate.charAt(2)+iDate.charAt(3);
        return ""+day+"."+month+"."+year;
    }
}
