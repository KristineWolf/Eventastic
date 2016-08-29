package com.example.kristine.eventastic.JavaClasses;

import java.util.Calendar;

/**
 * Created by Kristine on 29.08.2016.
 */
public class ContemporaryDate {

    public static int getContemporaryDate(){
        Calendar cal=Calendar.getInstance();
        String month;
        String day;
        if(cal.get(Calendar.MONTH)+1<10){
            month=""+0+(cal.get(Calendar.MONTH)+1);
        }else {
            month=""+(cal.get(Calendar.MONTH)+1);
        }
        if(cal.get(Calendar.DAY_OF_MONTH)<10){
            day=""+0+cal.get(Calendar.DAY_OF_MONTH);
        }else {
            day=""+cal.get(Calendar.DAY_OF_MONTH);
        }
        return Integer.parseInt(""+cal.get(Calendar.YEAR)+month+day);
    }
}
