package com.example.kristine.eventastic.JavaClasses;

/**
 * Created by Kristine on 29.08.2016.
 */
public class ChangeDateFormat {

    public static int changeIntoInteger(String sDate){
        String day= ""+sDate.charAt(6)+sDate.charAt(7);
        String month=""+sDate.charAt(4)+sDate.charAt(5);
        String year =""+sDate.charAt(0)+sDate.charAt(1)+sDate.charAt(2)+sDate.charAt(3);
        return Integer.parseInt(""+year+month+day);
    }

    public static String changeIntoString(int iDate){
        String date = ""+iDate;
        String day= ""+date.charAt(6)+date.charAt(7);
        String month=""+date.charAt(4)+date.charAt(5);
        String year =""+date.charAt(0)+date.charAt(1)+date.charAt(2)+date.charAt(3);
        return ""+day+"."+month+"."+year;
    }
}
